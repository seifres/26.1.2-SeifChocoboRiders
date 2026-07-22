package seifres.seifchocoboriders.services.types;


import net.minecraft.world.entity.player.Player;
import seifres.seifchocoboriders.entities.ChocoboEntity;

import java.util.OptionalInt;

public interface IMenuOpener {
    OptionalInt createMenuProviderForChocoboEntity(Player player, ChocoboEntity entity, int entityId);
}
