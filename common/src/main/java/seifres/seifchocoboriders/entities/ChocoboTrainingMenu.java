package seifres.seifchocoboriders.entities;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.init.ModMenus;
import seifres.seifchocoboriders.network.ChocoboEntityIdPayload;
import seifres.seifchocoboriders.network.ChocoboTrainingDataPayload;

public class ChocoboTrainingMenu extends AbstractContainerMenu {
    private final ChocoboTrainingContainer trainingContainer;
    private final ChocoboEntity entity;

    // Deliberately no DataSlots here for exp/level/base value. A DataSlot version of this
    // (synced via level, which safely fits the packet's signed-16-bit-short wire format -
    // see the earlier fix on the raw-exp/scaled-base-value version) still never delivered
    // updates to the client after the very first full-state push, even though item slot
    // changes (the scroll being consumed) synced correctly through the very same
    // broadcastChanges() call. Root cause wasn't pinned down (likely something specific to
    // how this loader's extra-data menu-opening wires the ContainerSynchronizer), but rather
    // than keep chasing it, this reuses a sync path that's already proven reliable in this
    // project: ChocoboEntity.recalculateTrainingBonuses() applies each stat's training bonus
    // as a real, permanent AttributeModifier (see ChocoboStat#modifierId) on the entity's own
    // AttributeInstance, and LivingEntity attributes - base value AND their modifiers - are
    // kept in sync to tracking clients automatically by vanilla's own
    // ClientboundUpdateAttributesPacket. So the Screen just reads the modifier straight off
    // the client-side ChocoboEntity and derives the level back out of it - no custom sync
    // needed at all.
    //
    // ...except that vanilla push turned out to be unreliable on at least one real dedicated
    // server (numbers/level would sit at their default value indefinitely - even after
    // waiting several minutes - and only ever refresh in response to a click, i.e. a genuine
    // request/response round trip). Rather than keep chasing vanilla's proactive-push timing
    // on that setup, the screen now also actively pulls a fresh snapshot via
    // ChocoboTrainingDataRequestPayload / ChocoboTrainingDataPayload (see PacketHandler /
    // ChocoboTrainingScreen#containerTick), using the exact same request-response pattern
    // that's always worked reliably here (e.g. the Train button). syncedLevels/syncedValues
    // below hold the latest snapshot received that way; the live attribute-modifier read is
    // kept only as a fallback for the brief window before the first response arrives.

    private int[] syncedLevels;
    private double[] syncedValues;

    // --- Shared layout constants — Screen reads these too, keep in sync ---
    public static final int LABEL_WIDTH = 60;
    public static final int SLOT_SIZE = 18;
    public static final int SLOT_START_Y = 14;
    public static final int ROW_HEIGHT = 14;
    public static final int COLUMN_GAP = 10;
    public static final int SLOT_START_X = LABEL_WIDTH + 4;
    public static final int COLUMN_WIDTH = SLOT_START_X + SLOT_SIZE + COLUMN_GAP;

    public static final int COLUMNS = 2;
    public static final int ROWS = (int) Math.ceil(ChocoboStat.values().length / (double) COLUMNS); // 5

    public static final int IMAGE_WIDTH = COLUMNS * COLUMN_WIDTH + 6;

    public static final int TRAIN_BUTTON_WIDTH = 80;
    public static final int TRAIN_BUTTON_HEIGHT = 20;
    public static final int STATS_BOTTOM_MARGIN = 6;
    public static final int TRAIN_BUTTON_Y = SLOT_START_Y + ROWS * ROW_HEIGHT + STATS_BOTTOM_MARGIN;
    public static final int TRAIN_BUTTON_X = (IMAGE_WIDTH - TRAIN_BUTTON_WIDTH) / 2;

    public static final int BUTTON_BOTTOM_MARGIN = 8;
    public static final int PLAYER_INVENTORY_HEIGHT = 90;
    public static final int INVENTORY_LABEL_Y = TRAIN_BUTTON_Y + TRAIN_BUTTON_HEIGHT + BUTTON_BOTTOM_MARGIN;
    public static final int PLAYER_INVENTORY_SLOTS_Y = INVENTORY_LABEL_Y + 10;
    public static final int IMAGE_HEIGHT = INVENTORY_LABEL_Y + PLAYER_INVENTORY_HEIGHT;

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, ChocoboEntity chocoboEntity) {
        super(ModMenus.CHOCOBO_TRAINING.get(), containerId);
        this.trainingContainer = chocoboEntity.getTrainingContainer();
        this.entity = chocoboEntity;

        this.trainingContainer.startOpen(playerInventory.player);

        ChocoboStat[] stats = ChocoboStat.values();
        for (int i = 0; i < stats.length; i++) {
            int col = i / ROWS;
            int row = i % ROWS;
            int x = SLOT_START_X + col * COLUMN_WIDTH;
            int y = SLOT_START_Y + row * ROW_HEIGHT;
            this.addSlot(new ChocoboTrainingSlot(trainingContainer, i, x, y));
        }

        addStandardInventorySlots(playerInventory, 8, PLAYER_INVENTORY_SLOTS_Y);
    }

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, ChocoboEntityIdPayload chocoboEntityIdPayload) {
        this(containerId, playerInventory,
                ((ChocoboEntity) playerInventory.player.level().getEntity(chocoboEntityIdPayload.integer())));
    }

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf byteBuf) {
        this(containerId, playerInventory, (ChocoboEntity) playerInventory.player.level().getEntity(byteBuf.readInt()));
    }

    @Override
    public boolean clickMenuButton(@NonNull Player player, int id) {
        boolean consumed = trainingContainer.consumeActiveTraining();
        if (consumed) {
            trainingContainer.returnRemainingIfMaxed(player);
        }
        return consumed;
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return this.trainingContainer.stillValid(player)
                && this.entity.isAlive()
                && player.isWithinEntityInteractionRange(this.entity, 4.0D);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide()) {
            this.trainingContainer.ejectRemainingItems(player);
        }
        this.trainingContainer.stopOpen(player);
    }

    public ChocoboTrainingContainer getTrainingContainer() {
        return trainingContainer;
    }

    /**
     * Server-side only: builds a fresh, server-authoritative snapshot of every stat's level
     * (from the training container's own XP/level math) and current effective attribute value
     * (AttributeInstance#getValue(), i.e. already run through Attribute#sanitizeValue()'s
     * clamp) to send back in response to a ChocoboTrainingDataRequestPayload.
     */
    public ChocoboTrainingDataPayload buildTrainingDataResponse() {
        ChocoboStat[] stats = ChocoboStat.values();
        int[] levels = new int[stats.length];
        double[] values = new double[stats.length];
        for (int i = 0; i < stats.length; i++) {
            levels[i] = trainingContainer.getLevel(stats[i]);
            AttributeInstance instance = entity.getAttribute(stats[i].attribute());
            values[i] = instance != null ? instance.getValue() : 0.0;
        }
        return new ChocoboTrainingDataPayload(this.containerId, levels, values);
    }

    /** Client-side only: stores the latest snapshot received from the server. */
    public void applySyncedTrainingData(int[] levels, double[] values) {
        this.syncedLevels = levels;
        this.syncedValues = values;
    }

    public boolean hasSyncedTrainingData() {
        return syncedLevels != null && syncedValues != null;
    }

    public int getSyncedLevel(ChocoboStat stat) {
        return syncedLevels[stat.ordinal()];
    }

    public double getSyncedValue(ChocoboStat stat) {
        return syncedValues[stat.ordinal()];
    }
}
