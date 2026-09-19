package seifres.seifchocoboriders.neoforge.loot;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import seifres.seifchocoboriders.Constants;

import java.util.function.Supplier;

public final class ModLootModifiers {
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public static final Supplier<MapCodec<GysahlGrassSeedLootModifier>> GYSAHL_GRASS_SEED =
            LOOT_MODIFIER_SERIALIZERS.register("gysahl_grass_seed", () -> GysahlGrassSeedLootModifier.CODEC);

    public static void register(IEventBus modBus) {
        LOOT_MODIFIER_SERIALIZERS.register(modBus);
    }

    private ModLootModifiers() {
    }
}
