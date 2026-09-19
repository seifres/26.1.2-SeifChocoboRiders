package seifres.seifchocoboriders.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.entity.animal.Animal;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

import java.lang.reflect.Method;

public class ModEntityTypes {
    public static void load() {

    }

    public static final RegistryHandle<EntityType<ChocoboEntity>> CHOCOBO_ENTITY =
            Services.REGISTRY.registerEntityType("chocobo_entity",
                    EntityType.Builder.of(ChocoboEntity::new, MobCategory.CREATURE)
                       .sized(1f, 2f)
                       .eyeHeight(1.9f));

    private static final Method SPAWN_PLACEMENTS_REGISTER;

    static {
        try {
            SPAWN_PLACEMENTS_REGISTER = SpawnPlacements.class.getDeclaredMethod("register",
                    EntityType.class, SpawnPlacementType.class, Heightmap.Types.class,
                    SpawnPlacements.SpawnPredicate.class);
            SPAWN_PLACEMENTS_REGISTER.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.SpawnPredicate<ChocoboEntity> predicate =
                (type, level, spawnReason, pos, random) ->
                        Animal.checkAnimalSpawnRules(type, level, spawnReason, pos, random);
        try {
            SPAWN_PLACEMENTS_REGISTER.invoke(null, CHOCOBO_ENTITY.get(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate);
        } catch (ReflectiveOperationException e) {
            Constants.LOG.error("Failed to register chocobo spawn placement", e);
        }
    }
}
