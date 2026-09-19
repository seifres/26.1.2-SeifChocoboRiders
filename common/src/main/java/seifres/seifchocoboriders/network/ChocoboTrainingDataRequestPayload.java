package seifres.seifchocoboriders.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import seifres.seifchocoboriders.Constants;

/**
 * Client -> server: "give me a fresh snapshot of this chocobo's training levels/values."
 * Sent periodically by ChocoboTrainingScreen while it's open, instead of relying solely on
 * vanilla's proactive attribute sync - see ChocoboTrainingMenu's class comment for why.
 */
public record ChocoboTrainingDataRequestPayload(int containerId) implements CustomPacketPayload {
    public static final Type<ChocoboTrainingDataRequestPayload> TYPE =
            new Type<>(Constants.id("chocobo_training_data_request"));

    public static final StreamCodec<ByteBuf, ChocoboTrainingDataRequestPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ChocoboTrainingDataRequestPayload::containerId,
                    ChocoboTrainingDataRequestPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
