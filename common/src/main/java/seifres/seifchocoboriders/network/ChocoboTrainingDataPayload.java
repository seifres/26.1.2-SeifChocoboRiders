package seifres.seifchocoboriders.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import seifres.seifchocoboriders.Constants;

/**
 * Server -> client: a fresh, server-authoritative snapshot of every ChocoboStat's level and
 * its current effective (already sanitizeValue()-clamped) attribute value, both arrays
 * indexed by ChocoboStat.ordinal(). Sent in reply to a ChocoboTrainingDataRequestPayload.
 *
 * Hand-rolled StreamCodec because there's no built-in fixed-size primitive array codec -
 * just length-prefixed int/double loops over a plain ByteBuf (no registry-aware types
 * involved, so ByteBuf is enough - same as ChocoboJumpPacket).
 */
public record ChocoboTrainingDataPayload(int containerId, int[] levels, double[] values) implements CustomPacketPayload {
    public static final Type<ChocoboTrainingDataPayload> TYPE =
            new Type<>(Constants.id("chocobo_training_data"));

    public static final StreamCodec<ByteBuf, ChocoboTrainingDataPayload> STREAM_CODEC =
            StreamCodec.of(ChocoboTrainingDataPayload::write, ChocoboTrainingDataPayload::read);

    private static void write(ByteBuf buf, ChocoboTrainingDataPayload payload) {
        buf.writeInt(payload.containerId);
        buf.writeInt(payload.levels.length);
        for (int level : payload.levels) {
            buf.writeInt(level);
        }
        buf.writeInt(payload.values.length);
        for (double value : payload.values) {
            buf.writeDouble(value);
        }
    }

    private static ChocoboTrainingDataPayload read(ByteBuf buf) {
        int containerId = buf.readInt();
        int levelCount = buf.readInt();
        int[] levels = new int[levelCount];
        for (int i = 0; i < levelCount; i++) {
            levels[i] = buf.readInt();
        }
        int valueCount = buf.readInt();
        double[] values = new double[valueCount];
        for (int i = 0; i < valueCount; i++) {
            values[i] = buf.readDouble();
        }
        return new ChocoboTrainingDataPayload(containerId, levels, values);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
