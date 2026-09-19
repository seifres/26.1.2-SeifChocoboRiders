package seifres.seifchocoboriders.entities;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModAttributes;

import java.util.function.Supplier;

public enum ChocoboStat {
    LAND_SPEED       (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.MOVEMENT_SPEED.value()),         0.00167, 43.17, 1, 0.10, 0.20),
    FLIGHT_SPEED     (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLIGHT_SPEED.get()),  0.000271, 340.0, 1, 0.0127, 0.03),
    JUMP_STRENGTH    (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.JUMP_STRENGTH.value()),          0.298, 1, 2, 1.25, 2.0) {
        @Override
        public double toDisplayValue(double rawAttributeValue) {
            double x = rawAttributeValue;
            double blocks = 3.4593 * x * x + 2.1413 * x - 0.3007;
            return Math.max(0.0, blocks);
        }
    },
    MAX_HEALTH       (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.MAX_HEALTH.value()),             0.45, 1, 0, 5.0, 10.0) {
        @Override
        public boolean roundsDown() { return true; }
    },
    FLAP_CAPACITY    (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLAP_CAPACITY.get()), 0.08, 1, 0, 1.0, 3.0) {
        @Override
        public boolean roundsDown() { return true; }
    },
    ATTACK_DAMAGE    (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.ATTACK_DAMAGE.value()),          0.07, 1, 1, 1.0, 3.0),
    ATTACK_SPEED     (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.ATTACK_SPEED.value()),           0.02, 1, 2, 0.8, 1.2),
    ATTACK_KNOCKBACK (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.ATTACK_KNOCKBACK.value()),       0.04, 1, 1, 0.5, 1.0),
    ARMOR            (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.ARMOR.value()),                  0.19, 1, 1, 0.5, 1.0),
    ARMOR_TOUGHNESS  (() -> BuiltInRegistries.ATTRIBUTE.wrapAsHolder(Attributes.ARMOR_TOUGHNESS.value()),        0.19, 1, 1, 0.5, 1.0);

    private final Supplier<Holder<Attribute>> attribute;
    private final double perLevelIncrease;
    private final Identifier modifierId;
    private final double displayMultiplier;
    private final int decimalPlaces;
    private final double spawnMin;
    private final double spawnMax;

    ChocoboStat(Supplier<Holder<Attribute>> attribute, double perLevelIncrease, double displayMultiplier,
                int decimalPlaces, double spawnMin, double spawnMax) {
        this.attribute = attribute;
        this.perLevelIncrease = perLevelIncrease;
        this.displayMultiplier = displayMultiplier;
        this.decimalPlaces = decimalPlaces;
        this.spawnMin = spawnMin;
        this.spawnMax = spawnMax;
        this.modifierId = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "training_bonus_" + this.name().toLowerCase());
    }

    public double spawnMin() { return spawnMin; }
    public double spawnMax() { return spawnMax; }
    public int decimalPlaces() { return decimalPlaces; }
    public Holder<Attribute> attribute() { return attribute.get(); }
    public double perLevelIncrease() { return perLevelIncrease; }
    public Identifier modifierId() { return modifierId; }
    public double displayMultiplier() { return displayMultiplier; }

    public Component displayName() {
        return Component.translatable("stat.seifchocoboriders." + this.name().toLowerCase());
    }

        public double toDisplayValue(double rawAttributeValue) {
            return rawAttributeValue * displayMultiplier;
        }

        public boolean roundsDown() {
            return false;
        }
}