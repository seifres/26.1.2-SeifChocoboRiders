package seifres.seifchocoboriders.datagen;


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
        //Items
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

        add(ModItems.CHOCOBO_TRAINING_WHIP.get(), "Chocobo Training Whip");
        add(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_1.get(), "Chocobo Training Scroll Level 1");
        add(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_2.get(), "Chocobo Training Scroll Level 2");
        add(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_3.get(), "Chocobo Training Scroll Level 3");
        add(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_4.get(), "Chocobo Training Scroll Level 4");
        add(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_5.get(), "Chocobo Training Scroll Level 5");


        //Blocks
        add(ModBlocks.GYSAHL_GREENS_CRATE.block().get(), "Gysahl Greens Crate");
        add(ModBlocks.GYSAHL_GREENS_CRATE.item().get(), "Gysahl Greens Crate");
        add(ModBlocks.GYSAHL_GREENS_CROP.item().get(),"Gysahl Greens Crop");

        //Entities
        add(ModEntityTypes.CHOCOBO_ENTITY.get(), "Chocobo Entity");

        //Villagers
        add("entity.minecraft.villager.chocobo_rancher", "Chocobo Rancher");

        //Menu Labels
        add("entity.seifchocoboriders.chocobo_training", "Chocobo Training");
        add("entity.seifchocoboriders.chocobo_training_named", "Chocobo Training - %s");
        add("stat.seifchocoboriders.land_speed", "Land Speed");
        add("stat.seifchocoboriders.flight_speed", "Flight Speed");
        add("stat.seifchocoboriders.jump_strength", "Jump Strength");
        add("stat.seifchocoboriders.max_health", "Max Health");
        add("stat.seifchocoboriders.flap_capacity", "Flap Stamina");

    }


    private void add(Component component, String value) {
        if(component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }

}
