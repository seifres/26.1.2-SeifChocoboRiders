package seifres.seifchocoboriders.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;


public record ChocoboJumpPacket(boolean flapPressed, boolean holdGlide) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ChocoboJumpPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("seifchocoboriders", "chocobo_jump"));
    public static final StreamCodec<ByteBuf, ChocoboJumpPacket> STREAM_CODEC;

    @Override
    public CustomPacketPayload.@NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE; }

    static {
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, ChocoboJumpPacket::flapPressed, ByteBufCodecs.BOOL,
                ChocoboJumpPacket::holdGlide, ChocoboJumpPacket::new);
    }

}