package seifres.seifchocoboriders.village;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.google.common.collect.ImmutableSet;
import seifres.seifchocoboriders.ChocoboVillagerData;

import java.util.function.Supplier;

public final class NeoForgeChocoboVillagers {

    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, "seifchocoboriders");

    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, "seifchocoboriders");

    public static final Supplier<PoiType> CHOCOBO_CRATE_POI = POI_TYPES.register(
            "gysahl_greens_crate",
            () -> new PoiType(ChocoboVillagerData.jobSiteStates(), 1, 1)
    );

    public static final Supplier<VillagerProfession> CHOCOBO_RANCHER = PROFESSIONS.register(
            "chocobo_rancher",
            () -> new VillagerProfession(
                    Component.translatable("entity.minecraft.villager.chocobo_rancher"),
                    holder -> holder.value() == CHOCOBO_CRATE_POI.get(),
                    holder -> holder.value() == CHOCOBO_CRATE_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FARMER,
                    ChocoboVillagerData.tradeSets()
            )
    );

    public static void register(IEventBus modEventBus) {
        POI_TYPES.register(modEventBus);
        PROFESSIONS.register(modEventBus);
    }
}