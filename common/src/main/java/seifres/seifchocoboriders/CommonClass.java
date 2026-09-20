package seifres.seifchocoboriders;

import seifres.seifchocoboriders.init.*;
import seifres.seifchocoboriders.services.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {

    public static void init() {

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        if (Services.PLATFORM.isModLoaded("seifchocoboriders")) {

            Constants.LOG.info("Chocobo Riders Loaded.");
        }

        ModEntityTypes.load();
        ModBlocks.load();
        ModItems.load();
        ModAttributes.load();
        ModMenus.load();
        ModDataComponents.load();
        ModCreativeTabs.load();


        ModEntityAttributes.load();


    }
}
