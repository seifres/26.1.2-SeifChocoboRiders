package seifres.seifchocoboriders.services;


import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.network.ChocoboEntityIdPayload;
import seifres.seifchocoboriders.services.types.IMenuOpener;

import java.util.OptionalInt;

public class FabricMenuOpener implements IMenuOpener {
    @Override
    public OptionalInt createMenuProviderForChocoboEntity(Player player, ChocoboEntity entity, int entityId) {
        return player.openMenu(new ExtendedMenuProvider<>() {
            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, @NonNull Inventory inventory,
                                                              @NonNull Player player) {
                return new ChocoboTrainingMenu(containerId, inventory, entity);
            }

            @Override
            public @NonNull Component getDisplayName() {
                return entity.getDisplayName();
            }

            @Override
            public @NonNull Object getScreenOpeningData(@NonNull ServerPlayer player) {
                return new ChocoboEntityIdPayload(entityId);
            }
        });
    }


}
