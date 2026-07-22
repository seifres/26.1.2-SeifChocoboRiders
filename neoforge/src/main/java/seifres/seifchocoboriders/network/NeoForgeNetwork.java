package seifres.seifchocoboriders.network;


import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import seifres.seifchocoboriders.SeifChocoboRiders;
import seifres.seifchocoboriders.entities.ChocoboEntity;

public class NeoForgeNetwork {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                ChocoboJumpPacket.TYPE,
                ChocoboJumpPacket.STREAM_CODEC,
                (payload, context)->handleJump(payload, context)
            );
    }

    private static void handleJump(final ChocoboJumpPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && player.getVehicle() instanceof ChocoboEntity chocobo) {
                SeifChocoboRiders.flightInput(player, payload);
            }
        });

    }
}