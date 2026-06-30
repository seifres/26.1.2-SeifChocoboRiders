package seifres.seifchocoboriders;


import net.neoforged.neoforge.data.event.GatherDataEvent;
import seifres.seifchocoboriders.datagen.*;

public final class SeifChocoboRidersDatagen {
    private SeifChocoboRidersDatagen() {
    }

     public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(SeifChocoboRidersModelProvider::new);
        event.createProvider(SeifChocoboRidersEnglishLanguageProvider::new);
         event.createProvider(SeifChocoboRidersBlockTagProvider::new);
         event.createProvider(SeifChocoboRidersLootTableProvider::new);
    }

}

