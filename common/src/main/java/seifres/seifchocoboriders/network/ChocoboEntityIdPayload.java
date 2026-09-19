package seifres.seifchocoboriders.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.lwjgl.system.ffm.mapping.Mapping;
import seifres.seifchocoboriders.Constants;

public record ChocoboEntityIdPayload(int integer) implements CustomPacketPayload {
    public static final Type<ChocoboEntityIdPayload> TYPE = new Type<>(Constants.id("chocobo_entity_id_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChocoboEntityIdPayload> STREAM_CODEC;

    static {
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                ChocoboEntityIdPayload::integer,
                ChocoboEntityIdPayload::new
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
