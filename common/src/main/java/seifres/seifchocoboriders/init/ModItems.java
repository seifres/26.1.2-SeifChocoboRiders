package seifres.seifchocoboriders.init;

import net.minecraft.world.item.Item;
import seifres.seifchocoboriders.entities.ChocoboVariant;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;
import seifres.seifchocoboriders.items.ChocoboWhistleItem;
import seifres.seifchocoboriders.items.GysahlGreensSeed;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

public final class ModItems {
    private ModItems() {
        }

        public static void load(){
        }

        public static final RegistryHandle<ChocoboScrollTrainingItem> CHOCOBO_SCROLL_TRAINING_LEVEL_1 =
            Services.REGISTRY.registerItem("chocobo_training_scroll_level_1",
                    properties -> new ChocoboScrollTrainingItem(properties,1));

        public static final RegistryHandle<ChocoboScrollTrainingItem> CHOCOBO_SCROLL_TRAINING_LEVEL_2 =
            Services.REGISTRY.registerItem("chocobo_training_scroll_level_2",
                    properties -> new ChocoboScrollTrainingItem(properties, 2));

        public static final RegistryHandle<ChocoboScrollTrainingItem> CHOCOBO_SCROLL_TRAINING_LEVEL_3 =
            Services.REGISTRY.registerItem("chocobo_training_scroll_level_3",
                    properties -> new ChocoboScrollTrainingItem(properties, 3));

        public static final RegistryHandle<ChocoboScrollTrainingItem> CHOCOBO_SCROLL_TRAINING_LEVEL_4 =
            Services.REGISTRY.registerItem("chocobo_training_scroll_level_4",
                    properties -> new ChocoboScrollTrainingItem(properties, 4));

        public static final RegistryHandle<ChocoboScrollTrainingItem> CHOCOBO_SCROLL_TRAINING_LEVEL_5 =
            Services.REGISTRY.registerItem("chocobo_training_scroll_level_5",
                    properties -> new ChocoboScrollTrainingItem(properties, 5));

        public static final RegistryHandle<Item> CHOCOBO_TRAINING_WHIP = Services.REGISTRY.registerItem(
                "chocobo_training_whip", properties -> new Item(properties.stacksTo(1)));

        public static final RegistryHandle<ChocoboWhistleItem> CHOCOBO_WHISTLE = Services.REGISTRY.registerItem(
                "chocobo_whistle", properties -> new ChocoboWhistleItem(
                        properties.stacksTo(1)
                                .component(ModDataComponents.WHISTLE_COLOR.get(), ChocoboVariant.YELLOW)));

        public static final RegistryHandle<Item> GYSAHL_GREENS = Services.REGISTRY.registerItem(
                "gysahl_greens", properties -> new Item(properties.stacksTo(64)));

        public static final RegistryHandle<Item> GYSAHL_GREENS_SEED = Services.REGISTRY.registerItem(
                "gysahl_greens_seed",properties -> new GysahlGreensSeed(properties.stacksTo(64)));


}

