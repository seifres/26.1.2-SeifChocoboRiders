package seifres.seifchocoboriders.services;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import seifres.seifchocoboriders.network.ChocoboTrainingDataRequestPayload;
import seifres.seifchocoboriders.network.IPacketHandler;

public class NeoForgePacketHandler implements IPacketHandler {
    @Override
    public void sendJumpToServer() {
        // Not wired up for this loader - SeifChocoboRidersClient's flight-control tick sends
        // ChocoboJumpPacket directly via ClientPacketDistributor instead.
    }

    @Override
    public void sendTrainingDataRequest(int containerId) {
        ClientPacketDistributor.sendToServer(new ChocoboTrainingDataRequestPayload(containerId));
    }
}
