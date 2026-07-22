package seifres.seifchocoboriders.entities;

import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.core.jackson.ListOfMapEntryDeserializer;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import javax.swing.*;

public enum ChocoboVariant {
    BLACK(0, "black"),
    BLUE(1,"blue"),
    BROWN (2,"brown"),
    CYAN(3,"cyan"),
    GRAY(4,"gray"),
    GREEN(5,"green"),
    LIGHTBLUE(6,"lightblue"),
    LIGHTGRAY(7,"lightgray"),
    LIME(8,"lime"),
    MAGENTA(9,"magenta"),
    ORANGE(10,"orange"),
    PINK(11,"pink"),
    PURPLE(12,"purple"),
    RED(13,"red"),
    WHITE(14,"white"),
    YELLOW(15,"yellow");

    private final int id;
    private final Identifier texture;

    ChocoboVariant(int id, String name) {
        this.id = id;
        this.texture = Identifier.fromNamespaceAndPath("seifchocoboriders",
                "textures/entity/chocobo_texture_" + name + ".png");
    }

    public int getID() { return id; }
    public Identifier getTexture() { return texture; }
    public static ChocoboVariant byID(int id) {
        for (ChocoboVariant v : values()) {
            if(v.id == id) return v;
        }
        return YELLOW;
    }
}
