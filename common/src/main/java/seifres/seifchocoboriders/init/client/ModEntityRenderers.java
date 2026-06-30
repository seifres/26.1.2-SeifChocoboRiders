package seifres.seifchocoboriders.init.client;

import seifres.seifchocoboriders.client.renderer.ChocoboEntityRenderer;
import seifres.seifchocoboriders.init.ModEntityTypes;
import seifres.seifchocoboriders.services.ServicesClient;

public class ModEntityRenderers {
    private ModEntityRenderers(){}

    public static void load() {
        ServicesClient.CLIENT_REGISTRY.registerEntityRenderer(ModEntityTypes.CHOCOBO_ENTITY.get(), ChocoboEntityRenderer::new);
    }
}
