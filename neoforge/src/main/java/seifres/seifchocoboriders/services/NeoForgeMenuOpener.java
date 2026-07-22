package seifres.seifchocoboriders.services;



import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.services.types.IMenuOpener;

import java.util.OptionalInt;

public class NeoForgeMenuOpener implements IMenuOpener {
    @Override
    public OptionalInt createMenuProviderForChocoboEntity(Player player, ChocoboEntity entity, int entityId) {
        return player.openMenu(new SimpleMenuProvider(
                (int containerId, Inventory inventory, Player _) -> new ChocoboTrainingMenu(entityId, inventory, entity),
                entity.getDisplayName()
                ), (RegistryFriendlyByteBuf buf) -> buf.writeInt(entityId));
    }
}
