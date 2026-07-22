package seifres.seifchocoboriders.init;

import net.minecraft.world.inventory.MenuType;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

public final class ModMenus {
    private ModMenus() {}

    public static final RegistryHandle<MenuType<ChocoboTrainingMenu>> CHOCOBO_TRAINING =
            Services.REGISTRY.registerEntityMenuType("chocobo_training", ChocoboTrainingMenu.class);

    public static void load() {
        // triggers static init
    }



}