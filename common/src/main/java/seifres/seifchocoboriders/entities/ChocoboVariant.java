package seifres.seifchocoboriders.entities;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.core.jackson.ListOfMapEntryDeserializer;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import javax.swing.*;

public enum ChocoboVariant {
    BLACK(0, "black"),
    BLUE(1, "blue"),
    BROWN(2, "brown"),
    CYAN(3, "cyan"),
    GRAY(4, "gray"),
    GREEN(5, "green"),
    LIGHTBLUE(6, "lightblue"),
    LIGHTGRAY(7, "lightgray"),
    LIME(8, "lime"),
    MAGENTA(9, "magenta"),
    ORANGE(10, "orange"),
    PINK(11, "pink"),
    PURPLE(12, "purple"),
    RED(13, "red"),
    WHITE(14, "white"),
    YELLOW(15, "yellow");

    public static final Codec<ChocoboVariant> CODEC =
            Codec.STRING.xmap(ChocoboVariant::byName, ChocoboVariant::getSerializedName);
    public static final StreamCodec<ByteBuf, ChocoboVariant> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(ChocoboVariant::byName, ChocoboVariant::getSerializedName);

    private final int id;
    private final String serializedName;
    private final Identifier texture;

    ChocoboVariant(int id, String name) {
        this.id = id;
        this.serializedName = name;
        this.texture = Identifier.fromNamespaceAndPath("seifchocoboriders",
                "textures/entity/chocobo_texture_" + name + ".png");
    }

    public int getID() { return id; }
    public String getSerializedName() { return serializedName; }
    public Identifier getTexture() { return texture; }

    public static ChocoboVariant byID(int id) {
        for (ChocoboVariant v : values()) {
            if (v.id == id) return v;
        }
        return YELLOW;
    }

    public static ChocoboVariant byName(String name) {
        for (ChocoboVariant v : values()) {
            if (v.serializedName.equals(name)) return v;
        }
        return YELLOW;
    }
}