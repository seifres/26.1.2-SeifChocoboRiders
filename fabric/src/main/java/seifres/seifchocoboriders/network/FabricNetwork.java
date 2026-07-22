package seifres.seifchocoboriders.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import seifres.seifchocoboriders.SeifChocoboRiders;
import seifres.seifchocoboriders.entities.ChocoboEntity;

public class FabricNetwork {

    public static void register() {
        // Use serverboundPlay() instead of playC2S()
        PayloadTypeRegistry.serverboundPlay().register(
                ChocoboJumpPacket.TYPE,
                ChocoboJumpPacket.STREAM_CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(ChocoboJumpPacket.TYPE, (packet, context) -> {
            ServerPlayer player = context.player();

            Entity vehicle = player.getVehicle();
            if (vehicle instanceof ChocoboEntity chocobo) {
                if (player.equals(chocobo.getControllingPassenger())) {
                    chocobo.applyFlightInput(packet.flapPressed(), packet.holdGlide());
                }
            }
        });
    }
}
