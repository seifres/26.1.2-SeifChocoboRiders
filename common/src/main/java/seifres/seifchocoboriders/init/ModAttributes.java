package seifres.seifchocoboriders.init;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

public final class ModAttributes {
    private ModAttributes() {}

    public static final RegistryHandle<RangedAttribute> CHOCOBO_FLIGHT_SPEED = Services.REGISTRY.registerAttribute(
            "flight_speed",
            () -> {
                RangedAttribute attribute = new RangedAttribute("attribute.name.chocobo.flight_speed", 0.02D, 0.0D, 1.0D);
                attribute.setSyncable(true);
                return attribute;
            }
    );

    public static final RegistryHandle<RangedAttribute> CHOCOBO_FLAP_CAPACITY = Services.REGISTRY.registerAttribute(
            "flap_capacity",
            () -> {
                RangedAttribute attribute = new RangedAttribute("attribute.name.chocobo.flap_capacity", 2.0D, 0.0D, 20.0D);
                attribute.setSyncable(true);
                return attribute;
            }
    );

    public static void load() {
    }
}
