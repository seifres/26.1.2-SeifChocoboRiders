package seifres.seifchocoboriders.init;

import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.DyeColor;
import seifres.seifchocoboriders.entities.ChocoboVariant;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

import java.util.UUID;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static void load() {}

    public static final RegistryHandle<DataComponentType<ChocoboVariant>> WHISTLE_COLOR =
            Services.REGISTRY.registerDataComponent("whistle_color", builder -> builder
                    .persistent(ChocoboVariant.CODEC)
                    .networkSynchronized(ChocoboVariant.STREAM_CODEC));

    public static final RegistryHandle<DataComponentType<UUID>> BOUND_CHOCOBO =
            Services.REGISTRY.registerDataComponent("bound_chocobo", builder -> builder
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC));
}