package seifres.seifchocoboriders.entities;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModMenus;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;
import seifres.seifchocoboriders.network.ChocoboEntityIdPayload;


public class ChocoboTrainingMenu extends AbstractContainerMenu{
    private final ChocoboTrainingContainer trainingContainer;

    private final ChocoboEntity entity;

    public static final int SLOT_START_X = 78;
    public static final int SLOT_START_Y = 18;
    public static final int SLOT_SIZE = 18;
    public static final int ROW_HEIGHT = 22;

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, ChocoboEntity chocoboEntity) {
        super(ModMenus.CHOCOBO_TRAINING.get(),containerId);
        this.trainingContainer = chocoboEntity.getTrainingContainer();
        this.entity = chocoboEntity;

        this.trainingContainer.startOpen(playerInventory.player);

        for (int stat = 0; stat < ChocoboStat.values().length; stat++) {
            for (int col = 0; col < ChocoboTrainingContainer.SLOTS_PER_STAT; col++) {
                int slotIndex = stat * ChocoboTrainingContainer.SLOTS_PER_STAT + col;
                int x = SLOT_START_X + col * SLOT_SIZE;
                int y = SLOT_START_Y + stat * ROW_HEIGHT;
                this.addSlot(new ChocoboTrainingSlot(trainingContainer, slotIndex, x, y));
            }
        }

        addStandardInventorySlots(playerInventory, 8, 140);
    }

    @Override
    public void clicked(int slotId, int button, @NonNull ContainerInput containerInput, @NonNull Player player) {
        if (slotId >= 0 && slotId < ChocoboTrainingContainer.TOTAL_SLOTS) {
            handleTrainingSlotClick(slotId, player);
            return;
        }
        super.clicked(slotId, button, containerInput, player);
    }

    private void handleTrainingSlotClick(int slotId, Player player) {
        ItemStack cursor = this.getCarried();
        if (!(cursor.getItem() instanceof ChocoboScrollTrainingItem newScroll)) {
            return; // empty cursor or wrong item — do nothing
        }

        ItemStack current = trainingContainer.getItem(slotId);

        if (current.isEmpty()) {
            Constants.LOG.info("Attempting fill: filledSlotCount={}, hasBudgetRemaining={}",
                    trainingContainer.getFilledSlotCount(), trainingContainer.hasBudgetRemaining());
            if (!trainingContainer.hasBudgetRemaining()) return;
            if (!trainingContainer.isLeftmostEmptyInRow(slotId)) return;
            trainingContainer.setItem(slotId, cursor.copyWithCount(1));
            trainingContainer.spendBudget();
            cursor.shrink(1);
        } else if (current.getItem() instanceof ChocoboScrollTrainingItem currentScroll
                && newScroll.getLevel() > currentScroll.getLevel()) {
            trainingContainer.setItem(slotId, cursor.copyWithCount(1));
            cursor.shrink(1);
        }

    }

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, ChocoboEntityIdPayload chocoboEntityIdPayload) {
        this(containerId, playerInventory,
                ((ChocoboEntity) playerInventory.player.level().getEntity(chocoboEntityIdPayload.integer())));
    }

    public ChocoboTrainingMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf byteBuf) {
        this(containerId, playerInventory,(ChocoboEntity) playerInventory.player.level().getEntity(byteBuf.readInt()));

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
    public void removed(Player player){
        super.removed(player);
        this.trainingContainer.stopOpen(player);
    }

    public ChocoboTrainingContainer getTrainingContainer() {
        return trainingContainer;
    }

}