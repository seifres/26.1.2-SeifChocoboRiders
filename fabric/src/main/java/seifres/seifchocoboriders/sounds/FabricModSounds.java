package seifres.seifchocoboriders.sounds;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModSounds;


public class FabricModSounds {
    public static void init() {
        ModSounds.CHOCOBO_FLUTE = register("chocobo_flute");
        ModSounds.CHOCOBO_KWEH = register("chocobo_kweh");
    }

    private static java.util.function.Supplier<SoundEvent> register(String name) {
        var loc = Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
        SoundEvent event = SoundEvent.createVariableRangeEvent(loc);
        Registry.register(BuiltInRegistries.SOUND_EVENT, loc, event);
        return () -> event;  // wrap in supplier to match ModSounds field type
    }
}




