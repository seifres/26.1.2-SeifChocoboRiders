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
        add(ModItems.CHOCOBO_WHISTLE.get(), "Chocobo Whistle");
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
        add(ModBlocks.GYSAHL_GREENS_BUNDLE.block().get(), "Gysahl Greens Bundle");
        add(ModBlocks.GYSAHL_GREENS_BUNDLE.item().get(), "Gysahl Greens Bundle");

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
        add("stat.seifchocoboriders.attack_damage", "Attack Damage");
        add("stat.seifchocoboriders.attack_speed", "Attack Speed");
        add("stat.seifchocoboriders.attack_knockback", "Knockback");
        add("stat.seifchocoboriders.armor", "Armor");
        add("stat.seifchocoboriders.armor_toughness", "Toughness");
        add("gui.seifchocoboriders.train", "Train");

        //Creative Tabs
        add("itemGroup.seifchocoboriders.chocobo_riders", "Chocobo Riders");

        //Chocobo Whistle messages
        add("message.seifchocoboriders.whistle_bound", "Whistle bound to your Chocobo!");
        add("message.seifchocoboriders.whistle_already_bound", "This whistle is already bound to a Chocobo.");
        add("message.seifchocoboriders.whistle_not_trained", "You can only bind a whistle to a Chocobo you've trained.");
        add("message.seifchocoboriders.whistle_unbound", "This whistle isn't bound to a Chocobo yet.");
        add("message.seifchocoboriders.whistle_no_chocobo", "Your Chocobo couldn't be found.");
        add("message.seifchocoboriders.whistle_destroyed", "Your Chocobo Whistle shattered - its bound Chocobo has died.");
    }


    private void add(Component component, String value) {
        if(component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }

}
