package seifres.seifchocoboriders.entities;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;


public class ChocoboTrainingScreen extends AbstractContainerScreen<ChocoboTrainingMenu> {
    private static final int PANEL_COLOR = 0xFFC6C6C6;
    private static final int SLOT_COLOR = 0xFF8B8B8B;
    private static final int LOCKED_SLOT_COLOR = 0xFF4A4A4A;
    private static final Identifier TEXTURE_LOCATION = Constants.id("textures/gui/container/training_screen.png");

    public ChocoboTrainingScreen(ChocoboTrainingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title,176,222);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.fill(x, y, x + imageWidth, y + imageHeight, PANEL_COLOR);

        for (int stat = 0; stat < ChocoboStat.values().length; stat++) {
            for (int col = 0; col < ChocoboTrainingContainer.SLOTS_PER_STAT; col++) {
                int slotIndex = stat * ChocoboTrainingContainer.SLOTS_PER_STAT + col;
                int slotX = x + ChocoboTrainingMenu.SLOT_START_X + col * ChocoboTrainingMenu.SLOT_SIZE;
                int slotY = y + ChocoboTrainingMenu.SLOT_START_Y + stat * ChocoboTrainingMenu.ROW_HEIGHT;

                boolean empty = this.menu.getTrainingContainer().getItem(slotIndex).isEmpty();
                boolean budgetLocked = empty && !this.menu.getTrainingContainer().hasBudgetRemaining();
                boolean orderLocked = empty && !this.menu.getTrainingContainer().isLeftmostEmptyInRow(slotIndex);
                boolean locked = budgetLocked || orderLocked;

                graphics.fill(slotX, slotY, slotX + 16, slotY + 16, locked ? LOCKED_SLOT_COLOR : SLOT_COLOR);
            }
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
        super.extractLabels(graphics, mouseX, mouseY); // draws title + player inventory label, same as before

        ChocoboEntity chocobo = this.menu.getTrainingContainer().getChocobo();
        ChocoboStat[] stats = ChocoboStat.values();

        for (int i = 0; i < stats.length; i++) {
            int labelY = ChocoboTrainingMenu.SLOT_START_Y + i * ChocoboTrainingMenu.ROW_HEIGHT + 1;
            graphics.text(this.font, stats[i].displayName(), 6, labelY, -12566464, false);

            if (chocobo != null) {
                AttributeInstance instance = chocobo.getAttribute(stats[i].attribute());
                if (instance != null) {
                    double displayValue = instance.getValue() * stats[i].displayMultiplier();
                    String valueText = String.format("%." + stats[i].decimalPlaces() + "f", displayValue);
                    graphics.text(this.font, valueText, 6, labelY + 10, 0xFF808080, false);
                }
            }
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a){
        super.extractBackground(graphics, mouseX, mouseY, a);
    }

}
