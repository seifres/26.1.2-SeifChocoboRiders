package seifres.seifchocoboriders.entities;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.network.PacketHandler;

public class ChocoboTrainingScreen extends AbstractContainerScreen<ChocoboTrainingMenu> {
    private static final int PANEL_COLOR = 0xFFC6C6C6;
    private static final int SLOT_COLOR = 0xFF8B8B8B;
    private static final int LOCKED_SLOT_COLOR = 0xFF4A4A4A;
    private static final Identifier TEXTURE_LOCATION = Constants.id("textures/gui/container/training_screen.png");
    private static final float TEXT_SCALE = 0.7f;

    /** How often (in client ticks) to re-request a fresh training-data snapshot from the
     *  server while this screen is open. 10 ticks = 0.5s - frequent enough to feel live
     *  without spamming the connection. See ChocoboTrainingMenu's class comment for why this
     *  request/response pull exists instead of relying solely on vanilla's proactive sync. */
    private static final int SYNC_REQUEST_INTERVAL = 10;

    private Button trainButton;
    private int syncRequestCooldown = 0;

    public ChocoboTrainingScreen(ChocoboTrainingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, ChocoboTrainingMenu.IMAGE_WIDTH, ChocoboTrainingMenu.IMAGE_HEIGHT);
        this.inventoryLabelY = ChocoboTrainingMenu.INVENTORY_LABEL_Y;
    }

    @Override
    protected void init() {
        super.init();

        int buttonX = this.leftPos + ChocoboTrainingMenu.TRAIN_BUTTON_X;
        int buttonY = this.topPos + ChocoboTrainingMenu.TRAIN_BUTTON_Y;

        trainButton = Button.builder(Component.translatable("gui.seifchocoboriders.train"), b -> {
                    if (this.minecraft != null && this.minecraft.gameMode != null) {
                        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
                    }
                })
                .bounds(buttonX, buttonY, ChocoboTrainingMenu.TRAIN_BUTTON_WIDTH, ChocoboTrainingMenu.TRAIN_BUTTON_HEIGHT)
                .build();

        this.addRenderableWidget(trainButton);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        trainButton.active = this.menu.getTrainingContainer().getActiveSlot() != -1;

        if (syncRequestCooldown <= 0) {
            PacketHandler.sendTrainingDataRequest(this.menu.containerId);
            syncRequestCooldown = SYNC_REQUEST_INTERVAL;
        } else {
            syncRequestCooldown--;
        }
    }

    @Override
    public void extractContents(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.fill(x, y, x + imageWidth, y + imageHeight, PANEL_COLOR);

        int activeSlot = this.menu.getTrainingContainer().getActiveSlot();
        ChocoboStat[] stats = ChocoboStat.values();
        for (int i = 0; i < stats.length; i++) {
            int col = i / ChocoboTrainingMenu.ROWS;
            int row = i % ChocoboTrainingMenu.ROWS;
            int rowY = y + ChocoboTrainingMenu.SLOT_START_Y + row * ChocoboTrainingMenu.ROW_HEIGHT;
            int slotX = x + ChocoboTrainingMenu.SLOT_START_X + col * ChocoboTrainingMenu.COLUMN_WIDTH;

            boolean locked = activeSlot != -1 && activeSlot != i;
            graphics.fill(slotX, rowY, slotX + ChocoboTrainingMenu.SLOT_SIZE, rowY + ChocoboTrainingMenu.SLOT_SIZE,
                    locked ? LOCKED_SLOT_COLOR : SLOT_COLOR);
        }

        for (Slot slot : this.menu.slots) {
            if (slot.index >= ChocoboTrainingContainer.TOTAL_SLOTS) {
                graphics.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, SLOT_COLOR);
            }
        }
        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void extractLabels(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);

        ChocoboEntity chocobo = this.menu.getTrainingContainer().getChocobo();
        ChocoboStat[] stats = ChocoboStat.values();

        for (int i = 0; i < stats.length; i++) {
            int col = i / ChocoboTrainingMenu.ROWS;
            int row = i % ChocoboTrainingMenu.ROWS;
            int rowTop = ChocoboTrainingMenu.SLOT_START_Y + row * ChocoboTrainingMenu.ROW_HEIGHT + 1;
            int labelX = 6 + col * ChocoboTrainingMenu.COLUMN_WIDTH;

            graphics.pose().pushMatrix();
            graphics.pose().scale(TEXT_SCALE, TEXT_SCALE);

            String nameText = stats[i].displayName().getString();
            int nameX = (int) (labelX / TEXT_SCALE);
            int nameY = (int) (rowTop / TEXT_SCALE);
            graphics.text(this.font, nameText, nameX, nameY, -12566464, false);

            // Base attribute value isn't pushed through a DataSlot - LivingEntity attributes are
            // already kept in sync to tracking clients automatically (vanilla's own
            // ClientboundUpdateAttributesPacket), so read it straight off the client-side entity.
            // This is only used for the spawn-percentile arrow below, which reflects the
            // chocobo's fixed innate stat and isn't affected by the training-sync issue.
            AttributeInstance attributeInstance = chocobo.getAttribute(stats[i].attribute());
            double baseValue = attributeInstance != null ? attributeInstance.getBaseValue() : 0.0;
            double range = stats[i].spawnMax() - stats[i].spawnMin();
            if (range > 0) {
                double percentile = (baseValue - stats[i].spawnMin()) / range;
                int nameWidth = this.font.width(nameText);
                if (percentile >= 0.8) {
                    graphics.text(this.font, " ↑", nameX + nameWidth, nameY, 0xFF55FF55, false);
                } else if (percentile <= 0.2) {
                    graphics.text(this.font, " ↓", nameX + nameWidth, nameY, 0xFFFF5555, false);
                }
            }

            int level;
            double totalRaw;
            if (this.menu.hasSyncedTrainingData()) {
                // Preferred path: the server-authoritative snapshot pulled via
                // ChocoboTrainingDataRequestPayload/ChocoboTrainingDataPayload (see
                // containerTick() above and ChocoboTrainingMenu's class comment). Already the
                // real, sanitizeValue()-clamped effective value - no further math needed.
                level = this.menu.getSyncedLevel(stats[i]);
                totalRaw = this.menu.getSyncedValue(stats[i]);
            } else {
                // Fallback for the brief window before the first response arrives: derive from
                // the training-bonus AttributeModifier that recalculateTrainingBonuses() applies
                // to this attribute (only added once the stat's been trained at all, so no
                // modifier = level 0), riding the same already-synced AttributeInstance as the
                // base value above.
                AttributeModifier trainingModifier = attributeInstance != null
                        ? attributeInstance.getModifier(stats[i].modifierId())
                        : null;
                double trainingBonus = trainingModifier != null ? trainingModifier.amount() : 0.0;
                level = stats[i].perLevelIncrease() != 0
                        ? (int) Math.round(trainingBonus / stats[i].perLevelIncrease())
                        : 0;
                // Read the attribute's real effective value instead of manually re-summing
                // base + bonus - AttributeInstance#getValue() runs it through
                // Attribute#sanitizeValue()'s clamp, exactly like the synced-snapshot path above.
                totalRaw = attributeInstance != null ? attributeInstance.getValue() : 0.0;
            }
            double rawDisplay = stats[i].toDisplayValue(totalRaw);
            double currentDisplay = stats[i].roundsDown() ? Math.floor(rawDisplay) : rawDisplay;
            String valueText = String.format("%." + stats[i].decimalPlaces() + "f", currentDisplay);

            int valueY = (int) ((rowTop + 8) / TEXT_SCALE);
            graphics.text(this.font, valueText, nameX, valueY, 0xFF808080, false);

            String levelText = " " + level + "/" + ChocoboTrainingContainer.MAX_LEVEL;
            int valueWidth = this.font.width(valueText);
            graphics.text(this.font, levelText, nameX + valueWidth, valueY, 0xFF808080, false);

            graphics.pose().popMatrix();
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
    }
}
