package seifres.seifchocoboriders.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.RegistryHandle;

import java.util.List;

public final class ModCreativeTabs {
    private ModCreativeTabs() {
    }

    public static void load() {
    }

    public static final RegistryHandle<CreativeModeTab> CHOCOBO_RIDERS = Services.REGISTRY.registerCreativeTab(
            "chocobo_riders",
            // Only title/icon here - no .displayItems(...). See the note on
            // IRegistryHelper#registerCreativeTab for why: CreativeModeTab.Output is protected
            // in NeoForge's own patched Minecraft artifact, so calling output.accept(...) from
            // common code fails to compile there even though it works fine on Fabric's jar.
            builder -> builder
                    .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".chocobo_riders"))
                    .icon(() -> new ItemStack(ModItems.CHOCOBO_WHISTLE.get())),
            List.<RegistryHandle<? extends ItemLike>>of(
                    ModItems.CHOCOBO_WHISTLE,
                    ModItems.CHOCOBO_TRAINING_WHIP,
                    ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_1,
                    ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_2,
                    ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_3,
                    ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_4,
                    ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_5,
                    ModItems.GYSAHL_GREENS,
                    ModItems.GYSAHL_GREENS_SEED,
                    ModBlocks.GYSAHL_GREENS_CRATE.item(),
                    ModBlocks.GYSAHL_GREENS_BUNDLE.item()
                    // Deliberately not including GYSAHL_GREENS_CROP's BlockItem - that's the
                    // planted-crop block itself, not meant to be handed out directly (same as
                    // vanilla never lists wheat/carrots as blocks in creative, only their seeds).
            ));
}
