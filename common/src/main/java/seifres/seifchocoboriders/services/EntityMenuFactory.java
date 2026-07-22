package seifres.seifchocoboriders.services;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface EntityMenuFactory<T extends AbstractContainerMenu> {
    T create(int windowId, Inventory inventory, int entityId);
}