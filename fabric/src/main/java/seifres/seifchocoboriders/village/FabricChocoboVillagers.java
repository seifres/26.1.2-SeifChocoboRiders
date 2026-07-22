package seifres.seifchocoboriders.village;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import seifres.seifchocoboriders.ChocoboVillagerData;

public final class FabricChocoboVillagers {
    public static PoiType CHOCOBO_CRATE_POI;
    public static VillagerProfession CHOCOBO_RANCHER;

    public static void register() {

        CHOCOBO_CRATE_POI = PoiHelper.register(
                ChocoboVillagerData.POI_ID,
                1,  // ticketCount
                1,  // searchDistance
                ChocoboVillagerData.jobSiteStates()
        );

        CHOCOBO_RANCHER = Registry.register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                ChocoboVillagerData.PROFESSION_ID,
                new VillagerProfession(
                        Component.translatable("entity.minecraft.villager.chocobo_rancher"),
                        holder -> holder.is(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getResourceKey(CHOCOBO_CRATE_POI).orElseThrow()),
                        holder -> holder.is(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getResourceKey(CHOCOBO_CRATE_POI).orElseThrow()),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.VILLAGER_WORK_FARMER,
                        ChocoboVillagerData.tradeSets()
                )
        );
    }
}
