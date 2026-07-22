package seifres.seifchocoboriders.services.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.network.ChocoboJumpPacket;

public class FabricClientSetup {
    private static boolean wasJumping = false;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (!(client.player.getVehicle() instanceof ChocoboEntity)) return;

            boolean isJumping = client.options.keyJump.isDown();
            boolean holdGlide = client.options.keyShift.isDown();

            if (isJumping && !wasJumping) {
                ClientPlayNetworking.send(new ChocoboJumpPacket(true, holdGlide));
            } else if (!isJumping && holdGlide) {
                ClientPlayNetworking.send(new ChocoboJumpPacket(false, true));
            }

            wasJumping = isJumping;
        });
    }
}

