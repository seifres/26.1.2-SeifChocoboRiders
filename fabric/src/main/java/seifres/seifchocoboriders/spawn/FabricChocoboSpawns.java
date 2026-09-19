package seifres.seifchocoboriders.spawn;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import seifres.seifchocoboriders.init.ModEntityTypes;

import java.util.Set;

public final class FabricChocoboSpawns {
    private static final Set<ResourceKey<Biome>> SPAWN_BIOMES = Set.of(
            Biomes.PLAINS,
            Biomes.SUNFLOWER_PLAINS,
            Biomes.SAVANNA,
            Biomes.SAVANNA_PLATEAU,
            Biomes.WINDSWEPT_SAVANNA
    );

    public static void register() {
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(SPAWN_BIOMES),
                MobCategory.CREATURE,
                ModEntityTypes.CHOCOBO_ENTITY.get(),
                3,
                2,
                6
        );
    }
}
