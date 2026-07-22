package seifres.seifchocoboriders.entities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.Container;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;

public class ChocoboTrainingSlot extends Slot {
    public ChocoboTrainingSlot(Container container, int slotIndex, int x, int y) {
        super(container, slotIndex, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPickup(@NonNull Player player) {
        return false; // items can never be removed by the player, only overwritten via our custom click logic
    }

    @Override
    public boolean mayPlace(@NonNull ItemStack stack) {
        return stack.getItem() instanceof ChocoboScrollTrainingItem;
    }
}