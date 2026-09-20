package seifres.seifchocoboriders.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import seifres.seifchocoboriders.SeifChocoboRiders;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;

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

        PayloadTypeRegistry.serverboundPlay().register(
                ChocoboTrainingDataRequestPayload.TYPE,
                ChocoboTrainingDataRequestPayload.STREAM_CODEC
        );
        PayloadTypeRegistry.clientboundPlay().register(
                ChocoboTrainingDataPayload.TYPE,
                ChocoboTrainingDataPayload.STREAM_CODEC
        );

        // Client -> server: a training screen asking for a fresh snapshot. Look up the
        // sender's currently open menu (must actually be a ChocoboTrainingMenu with a
        // matching containerId) and send the server-built response straight back.
        ServerPlayNetworking.registerGlobalReceiver(ChocoboTrainingDataRequestPayload.TYPE, (packet, context) -> {
            ServerPlayer player = context.player();
            if (player.containerMenu instanceof ChocoboTrainingMenu menu && menu.containerId == packet.containerId()) {
                ServerPlayNetworking.send(player, menu.buildTrainingDataResponse());
            }
        });
    }

    /**
     * Registered separately from register() (which runs from the common ModInitializer
     * entrypoint on both sides) because ClientPlayNetworking is a client-only Fabric API
     * class - only ever called from SeifChocoboModClient.onInitializeClient().
     */
    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(ChocoboTrainingDataPayload.TYPE, (packet, context) -> {
            if (context.player().containerMenu instanceof ChocoboTrainingMenu menu
                    && menu.containerId == packet.containerId()) {
                menu.applySyncedTrainingData(packet.levels(), packet.values());
            }
        });
    }
}
