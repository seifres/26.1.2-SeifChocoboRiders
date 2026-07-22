package seifres.seifchocoboriders.entities;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModAttributes;

import java.util.function.Supplier;

public enum ChocoboStat {
    LAND_SPEED      (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.MOVEMENT_SPEED.value()),        new double[]{0.01, 0.02, 0.03, 0.04, 0.05},100
            , 0),
    FLIGHT_SPEED    (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLIGHT_SPEED.get()), new double[]{0.004, 0.008, 0.012, 0.016, 0.020}, 100, 0),
    JUMP_STRENGTH (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.JUMP_STRENGTH.value()),          new double[]{0.4, 0.8, 1.2, 1.6, 1.8}, 1, 1),
    MAX_HEALTH      (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.MAX_HEALTH.value()),             new double[]{1.0, 2.0, 3.0, 4.0, 5.0}, 1, 0 ),
    FLAP_CAPACITY   (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLAP_CAPACITY.get()), new double[]{0.5, 1.0, 1.5, 2.0, 2.5}, 1, 1);

    private final Supplier<Holder<Attribute>> attribute;
    private final double[] amountPerLevel;
    private final Identifier modifierId;
    private final double displayMultiplier;
    private final int decimalPlaces;

    ChocoboStat(Supplier<Holder<Attribute>> attribute, double[] amountPerLevel, double displayMultiplier, int decimalPlaces) {
        this.attribute = attribute;
        this.amountPerLevel = amountPerLevel;
        this.displayMultiplier = displayMultiplier;
        this.decimalPlaces = decimalPlaces;
        this.modifierId = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "training_bonus_" + this.name().toLowerCase());
    }

    public int decimalPlaces() {
        return decimalPlaces;
    }

    public Holder<Attribute> attribute() {
        return attribute.get();
    }

    public double amountForLevel(int level) {
        return amountPerLevel[Mth.clamp(level, 1, 5) - 1];
    }

    public Identifier modifierId() {
        return modifierId;
    }

    public double displayMultiplier() {
        return displayMultiplier;
    }

    public Component displayName() {
        return Component.translatable("stat.seifchocoboriders." + this.name().toLowerCase());
    }
}