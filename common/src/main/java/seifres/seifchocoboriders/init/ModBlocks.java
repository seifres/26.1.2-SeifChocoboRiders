package seifres.seifchocoboriders.init;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import seifres.seifchocoboriders.block.GysahlGreensCrop;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.util.BlockwithItemRegistryHandle;

public final class ModBlocks {
    private ModBlocks() {
    }
    public static void load() {
    }

    public static final BlockwithItemRegistryHandle<Block> GYSAHL_GREENS_CRATE =
            Services.REGISTRY.registerBlockWithItem(
                    "gysahl_greens_crate", properties -> new Block(properties.mapColor(MapColor.PLANT).destroyTime(1.0F)),
                    ((block, properties) -> new BlockItem(block, properties.stacksTo(1))));

    public static final BlockwithItemRegistryHandle<GysahlGreensCrop> GYSAHL_GREENS_CROP =
            Services.REGISTRY.registerBlockWithItem(
                    "gysahl_greens_crop",
                    properties -> new GysahlGreensCrop(properties.noCollision().randomTicks().instabreak().sound(SoundType.CROP)));

}
