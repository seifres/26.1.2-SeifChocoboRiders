package seifres.seifchocoboriders;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import seifres.seifchocoboriders.services.ServicesClient;

import java.util.function.Supplier;

public class SeifChocoboModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SeifChocoboRidersClientCommon.init();
        ServicesClient.CLIENT_REGISTRY.applyModelLayerRegistrations((ModelLayerLocation location,
                Supplier<LayerDefinition> supplier) -> ModelLayerRegistry.registerModelLayer(location, supplier::get));
        ServicesClient.CLIENT_REGISTRY.applyEntityRendererRegistrations(EntityRenderers::register);
    }
}
