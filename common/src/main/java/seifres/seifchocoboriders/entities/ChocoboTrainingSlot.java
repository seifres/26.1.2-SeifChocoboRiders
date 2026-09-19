package seifres.seifchocoboriders.entities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.Container;

public class ChocoboTrainingSlot extends Slot {
    public ChocoboTrainingSlot(Container container, int slotIndex, int x, int y) {
        super(container, slotIndex, x, y);
    }


    @Override
    public boolean mayPickup(@NonNull Player player) {
        return true;
    }

    @Override
    public boolean mayPlace(@NonNull ItemStack stack) {
        return ((ChocoboTrainingContainer) this.container).canPlaceItem(this.getContainerSlot(), stack);
    }
}