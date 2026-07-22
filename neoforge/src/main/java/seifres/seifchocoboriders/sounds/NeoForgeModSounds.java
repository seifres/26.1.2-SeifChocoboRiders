package seifres.seifchocoboriders.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModSounds;
import java.util.function.Supplier;

public class NeoForgeModSounds {
    public static final DeferredRegister<net.minecraft.sounds.SoundEvent> SOUND_EVENTS =
                       DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CHOCOBO_FLUTE =
            SOUND_EVENTS.register("chocobo_flute",
                    () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                            "chocobo_flute")));

    public static final DeferredHolder<SoundEvent, SoundEvent> CHOCOBO_KWEH =
            SOUND_EVENTS.register("chocobo_kweh",
                    () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                            "chocobo_kweh")));

    public static void init() {
        ModSounds.CHOCOBO_FLUTE = () -> ((Supplier<SoundEvent>) CHOCOBO_FLUTE).get();
        ModSounds.CHOCOBO_KWEH = () -> ((Supplier<SoundEvent>) CHOCOBO_KWEH).get();
    }

}
