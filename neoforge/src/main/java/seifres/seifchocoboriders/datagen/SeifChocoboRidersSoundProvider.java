package seifres.seifchocoboriders.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.sounds.NeoForgeModSounds;


public class SeifChocoboRidersSoundProvider extends SoundDefinitionsProvider {
    public SeifChocoboRidersSoundProvider(PackOutput packOutput) {
        super(packOutput, Constants.MOD_ID);
    }

    @Override
    public void registerSounds() {

        this.add(NeoForgeModSounds.CHOCOBO_FLUTE,
                definition()
                        .subtitle("seifchocoboriders.chocobo_flute")
                        .with(sound(modLoc("chocobo_flute"))
                        .volume(1.5f)
                        .pitch(1.0f))
        );

        this.add(NeoForgeModSounds.CHOCOBO_KWEH,
                definition()
                        .subtitle("seifchocoboriders.chocobo_kweh")
                        .with(sound(modLoc("chocobo_kweh"))
                        .volume(1.5f)
                        .pitch(1.0f))
        );
    }

    private Identifier modLoc(String s) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, s);
    }


}

