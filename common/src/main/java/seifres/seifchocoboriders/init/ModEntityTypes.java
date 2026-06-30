package seifres.seifchocoboriders.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

public class ModEntityTypes {
    public static void load() {

    }

    public static final RegistryHandle<EntityType<ChocoboEntity>> CHOCOBO_ENTITY =
            Services.REGISTRY.registerEntityType("chocobo_entity",
                    EntityType.Builder.of(ChocoboEntity::new, MobCategory.CREATURE)
                       .sized(1f, 2f)
                       .eyeHeight(1.9f));
}