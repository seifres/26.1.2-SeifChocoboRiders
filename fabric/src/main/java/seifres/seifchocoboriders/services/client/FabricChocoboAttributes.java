package seifres.seifchocoboriders.services.client;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import seifres.seifchocoboriders.entities.ChocoboAttributeIds;

public final class FabricChocoboAttributes {
    public static Attribute FLIGHT_SPEED;
    public static Attribute FLAP_CAPACITY;

    public static void register() {
        FLIGHT_SPEED = Registry.register(BuiltInRegistries.ATTRIBUTE, ChocoboAttributeIds.FLIGHT_SPEED,
                new RangedAttribute("attribute.name.chocobo.flight_speed", 0.05D, 0.0D, 2.0D).setSyncable(true));

        FLAP_CAPACITY = Registry.register(BuiltInRegistries.ATTRIBUTE, ChocoboAttributeIds.FLAP_CAPACITY,
                new RangedAttribute("attribute.name.chocobo.flap_capacity", 2.0D, 0.0D, 20.0D).setSyncable(true));
    }
}