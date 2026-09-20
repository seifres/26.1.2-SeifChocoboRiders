package seifres.seifchocoboriders.network;


import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import seifres.seifchocoboriders.SeifChocoboRiders;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;

public class NeoForgeNetwork {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                ChocoboJumpPacket.TYPE,
                ChocoboJumpPacket.STREAM_CODEC,
                (payload, context)->handleJump(payload, context)
            );

        registrar.playToServer(
                ChocoboTrainingDataRequestPayload.TYPE,
                ChocoboTrainingDataRequestPayload.STREAM_CODEC,
                (payload, context) -> handleTrainingDataRequest(payload, context)
        );

        registrar.playToClient(
                ChocoboTrainingDataPayload.TYPE,
                ChocoboTrainingDataPayload.STREAM_CODEC,
                (payload, context) -> handleTrainingDataResponse(payload, context)
        );
    }

    private static void handleJump(final ChocoboJumpPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && player.getVehicle() instanceof ChocoboEntity chocobo) {
                SeifChocoboRiders.flightInput(player, payload);
            }
        });

    }

    private static void handleTrainingDataRequest(final ChocoboTrainingDataRequestPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player
                    && player.containerMenu instanceof ChocoboTrainingMenu menu
                    && menu.containerId == payload.containerId()) {
                PacketDistributor.sendToPlayer(player, menu.buildTrainingDataResponse());
            }
        });
    }

    private static void handleTrainingDataResponse(final ChocoboTrainingDataPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ChocoboTrainingMenu menu
                    && menu.containerId == payload.containerId()) {
                menu.applySyncedTrainingData(payload.levels(), payload.values());
            }
        });
    }
}
