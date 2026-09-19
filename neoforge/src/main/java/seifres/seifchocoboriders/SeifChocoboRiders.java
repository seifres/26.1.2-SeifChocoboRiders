package seifres.seifchocoboriders;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.init.ModEntityTypes;
import seifres.seifchocoboriders.neoforge.loot.ModLootModifiers;
import seifres.seifchocoboriders.network.ChocoboJumpPacket;
import seifres.seifchocoboriders.network.NeoForgeNetwork;
import seifres.seifchocoboriders.services.NeoForgeRegistryHelper;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.types.IAttributeRegistryHelper;
import seifres.seifchocoboriders.village.NeoForgeChocoboVillagers;

import java.util.Objects;

@Mod(Constants.MOD_ID)
public class SeifChocoboRiders {

    public SeifChocoboRiders(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
        eventBus.addListener(SeifChocoboRiders::onEntityAttributeCreation);
        eventBus.addListener(SeifChocoboRidersDatagen::onGatherClientData);
        eventBus.addListener(NeoForgeNetwork::register);
        NeoForgeRegistryHelper.register(eventBus);
        NeoForgeChocoboVillagers.register(eventBus);
        ModLootModifiers.register(eventBus);


        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            SeifChocoboRidersClient.init(eventBus);
        }
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        Services.ATTRIBUTES.applyEntityAttributeRegistrations(new IAttributeRegistryHelper.EntityAttributeRegistrar() {
            @Override
            public <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier.Builder builder) {
                event.put(entityType, builder.build());
            }
        });

        ModEntityTypes.registerSpawnPlacements();
    }

    public static void flightInput(ServerPlayer player, ChocoboJumpPacket payload) {
        Entity var3 = player.getVehicle();
        if (var3 instanceof ChocoboEntity chocobo) {
            if (player.equals(Objects.requireNonNull(chocobo.getControllingPassenger()))) {
                chocobo.applyFlightInput(payload.flapPressed(), payload.holdGlide());
            }
        }

    }

}