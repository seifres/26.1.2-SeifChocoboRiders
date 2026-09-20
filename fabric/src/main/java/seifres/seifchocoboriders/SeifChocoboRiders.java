package seifres.seifchocoboriders;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import seifres.seifchocoboriders.init.ModEntityTypes;
import seifres.seifchocoboriders.loot.FabricGysahlGrassLoot;
import seifres.seifchocoboriders.network.FabricNetwork;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.sounds.FabricModSounds;
import seifres.seifchocoboriders.spawn.FabricChocoboSpawns;
import seifres.seifchocoboriders.village.FabricChocoboVillagers;

public class SeifChocoboRiders implements ModInitializer {

    @Override
    public void onInitialize() {

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        FabricModSounds.init();
        CommonClass.init();
        Services.ATTRIBUTES.applyEntityAttributeRegistrations(FabricDefaultAttributeRegistry::register);
        FabricNetwork.register();
        FabricChocoboVillagers.register();
        ModEntityTypes.registerSpawnPlacements();
        FabricChocoboSpawns.register();
        FabricGysahlGrassLoot.register();

    }
}
