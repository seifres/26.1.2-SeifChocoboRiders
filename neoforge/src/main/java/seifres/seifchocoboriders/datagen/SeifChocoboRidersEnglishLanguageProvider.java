package seifres.seifchocoboriders.datagen;

import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModBlocks;
import seifres.seifchocoboriders.init.ModEntityTypes;
import seifres.seifchocoboriders.init.ModItems;

public class SeifChocoboRidersEnglishLanguageProvider extends LanguageProvider {
    public SeifChocoboRidersEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CHOCOBO_WHISTLE_BLACK.get(), "Chocobo Whistle Black");
        add(ModItems.CHOCOBO_WHISTLE_BLUE.get(), "Chocobo Whistle Blue");
        add(ModItems.CHOCOBO_WHISTLE_BROWN.get(), "Chocobo Whistle Brown");
        add(ModItems.CHOCOBO_WHISTLE_CYAN.get(), "Chocobo Whistle Cyan");
        add(ModItems.CHOCOBO_WHISTLE_GRAY.get(), "Chocobo Whistle Gray");
        add(ModItems.CHOCOBO_WHISTLE_GREEN.get(), "Chocobo Whistle Green");
        add(ModItems.CHOCOBO_WHISTLE_LIGHT_BLUE.get(), "Chocobo Whistle Light Blue");
        add(ModItems.CHOCOBO_WHISTLE_LIGHT_GRAY.get(), "Chocobo Whistle Light Gray");
        add(ModItems.CHOCOBO_WHISTLE_LIME.get(), "Chocobo Whistle Lime");
        add(ModItems.CHOCOBO_WHISTLE_MAGENTA.get(), "Chocobo Whistle Magenta");
        add(ModItems.CHOCOBO_WHISTLE_ORANGE.get(), "Chocobo Whistle Orange");
        add(ModItems.CHOCOBO_WHISTLE_PINK.get(), "Chocobo Whistle Pink");
        add(ModItems.CHOCOBO_WHISTLE_PURPLE.get(), "Chocobo Whistle Purple");
        add(ModItems.CHOCOBO_WHISTLE_RED.get(), "Chocobo Whistle Red");
        add(ModItems.CHOCOBO_WHISTLE_WHITE.get(), "Chocobo Whistle White");
        add(ModItems.CHOCOBO_WHISTLE_YELLOW.get(), "Chocobo Whistle Yellow");
        add(ModItems.GYSAHL_GREENS_SEED.get(), "Gysahl Greens Seed");
        add(ModItems.GYSAHL_GREENS.get(), "Gysahl Greens");

        add(ModBlocks.GYSAHL_GREENS_CRATE.block().get(), "Gysahl Greens Crate");
        add(ModBlocks.GYSAHL_GREENS_CRATE.item().get(), "Gysahl Greens Crate");
        add(ModBlocks.GYSAHL_GREENS_CROP.item().get(),"Gysahl Greens Crop");


        add(ModEntityTypes.CHOCOBO_ENTITY.get(), "Chocobo Entity");
    }



    private void add(Component component, String value) {
        if(component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }

}
