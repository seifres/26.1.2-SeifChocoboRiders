package seifres.seifchocoboriders.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FarmlandBlock;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

public final class ModItems {
    private ModItems() {
        }

        public static void load(){
        }

        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_BLACK = Services.REGISTRY.registerItem(
                "chocobo_whistle_black", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_BLUE = Services.REGISTRY.registerItem(
                "chocobo_whistle_blue", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_BROWN = Services.REGISTRY.registerItem(
                "chocobo_whistle_brown", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_CYAN = Services.REGISTRY.registerItem(
                "chocobo_whistle_cyan", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_GRAY = Services.REGISTRY.registerItem(
                "chocobo_whistle_gray", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_GREEN = Services.REGISTRY.registerItem(
                "chocobo_whistle_green", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_LIGHT_BLUE = Services.REGISTRY.registerItem(
                "chocobo_whistle_light_blue", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_LIGHT_GRAY = Services.REGISTRY.registerItem(
                "chocobo_whistle_light_gray", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_LIME = Services.REGISTRY.registerItem(
                "chocobo_whistle_lime", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_MAGENTA = Services.REGISTRY.registerItem(
                "chocobo_whistle_magenta", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_ORANGE = Services.REGISTRY.registerItem(
                "chocobo_whistle_orange", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_PINK = Services.REGISTRY.registerItem(
                "chocobo_whistle_pink", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_PURPLE = Services.REGISTRY.registerItem(
                "chocobo_whistle_purple", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_RED = Services.REGISTRY.registerItem(
                "chocobo_whistle_red", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_WHITE = Services.REGISTRY.registerItem(
                "chocobo_whistle_white", properties -> new Item(properties.stacksTo(1).useCooldown(30)));
        public static final RegistryHandle<Item> CHOCOBO_WHISTLE_YELLOW = Services.REGISTRY.registerItem(
                "chocobo_whistle_yellow", properties -> new Item(properties.stacksTo(1).useCooldown(30)));

        public static final RegistryHandle<Item> GYSAHL_GREENS = Services.REGISTRY.registerItem(
                "gysahl_greens", properties -> new Item(properties.stacksTo(32)));

        public static final RegistryHandle<Item> GYSAHL_GREENS_SEED = Services.REGISTRY.registerItem(
                "gysahl_greens_seed",properties -> new Item(properties.stacksTo(32)));
}

