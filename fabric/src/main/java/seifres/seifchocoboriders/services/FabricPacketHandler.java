package seifres.seifchocoboriders.services;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import seifres.seifchocoboriders.network.ChocoboTrainingDataRequestPayload;
import seifres.seifchocoboriders.network.IPacketHandler;

/**
 * Client-only - only ever constructed from SeifChocoboModClient.onInitializeClient(), Fabric's
 * dedicated client entrypoint, so it's safe for this class to reference client-only Fabric
 * networking types even though IPacketHandler is a common interface.
 */
public class FabricPacketHandler implements IPacketHandler {
    @Override
    public void sendJumpToServer() {
        // Not wired up for this loader either - see NeoForgePacketHandler's identical note.
    }

    @Override
    public void sendTrainingDataRequest(int containerId) {
        ClientPlayNetworking.send(new ChocoboTrainingDataRequestPayload(containerId));
    }
}
