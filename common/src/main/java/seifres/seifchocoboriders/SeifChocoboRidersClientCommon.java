package seifres.seifchocoboriders;

import seifres.seifchocoboriders.init.client.ModEntityRenderers;
import seifres.seifchocoboriders.init.client.ModModelLayers;

public final class SeifChocoboRidersClientCommon {
    private static boolean initialized;

    private SeifChocoboRidersClientCommon(){

    }

    public static void init() {
        if (initialized)
            return;

        initialized = true;

        ModModelLayers.load();
        ModEntityRenderers.load();
         }

}
