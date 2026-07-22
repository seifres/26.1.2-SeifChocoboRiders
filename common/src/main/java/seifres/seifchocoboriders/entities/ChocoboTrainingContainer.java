package seifres.seifchocoboriders.entities;

import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;

public class ChocoboTrainingContainer extends SimpleContainer {
    public static final int SLOTS_PER_STAT = 5;
    public static final int TOTAL_SLOTS = ChocoboStat.values().length * SLOTS_PER_STAT;
    public static final int MAX_FILLED_SLOTS = 15;

    private final ChocoboEntity chocobo;
    private int filledSlotCount = 0;

    public ChocoboTrainingContainer(ChocoboEntity chocobo) {
        super(TOTAL_SLOTS);
        this.chocobo = chocobo;
    }

    public static ChocoboStat statForSlot(int slotIndex) {
        return ChocoboStat.values()[slotIndex / SLOTS_PER_STAT];
    }

    public boolean hasBudgetRemaining() {
        return filledSlotCount < MAX_FILLED_SLOTS;
    }

    public void spendBudget() {
        filledSlotCount++;
    }

    public int getFilledSlotCount() {
        return filledSlotCount;
    }

    public void setFilledSlotCount(int count) {
        this.filledSlotCount = count;
    }

    @Override
    public boolean canPlaceItem(int slot, @NonNull ItemStack stack) {
        return stack.getItem() instanceof ChocoboScrollTrainingItem;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (chocobo != null) {
            chocobo.recalculateTrainingBonuses();
        }
    }

    public void save(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.getItems());
        output.putInt("FilledSlots", filledSlotCount);
    }

    public void load(ValueInput input) {
        ContainerHelper.loadAllItems(input, this.getItems());
        this.filledSlotCount = input.getIntOr("FilledSlots", 0);
    }

    public ChocoboEntity getChocobo() {
        return chocobo;
    }

    public boolean isLeftmostEmptyInRow(int slotIndex) {
        int rowStart = (slotIndex / SLOTS_PER_STAT) * SLOTS_PER_STAT;
        for (int i = rowStart; i < slotIndex; i++) {
            if (getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

}