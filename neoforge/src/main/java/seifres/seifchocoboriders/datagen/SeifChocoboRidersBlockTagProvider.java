package seifres.seifchocoboriders.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class SeifChocoboRidersBlockTagProvider extends BlockTagsProvider {
    public SeifChocoboRidersBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.GYSAHL_GREENS_CRATE.block().get());

        // Add Aditional Tags... tag(BlockTags.NEEDS_IRON_TOOL);

    }
}
