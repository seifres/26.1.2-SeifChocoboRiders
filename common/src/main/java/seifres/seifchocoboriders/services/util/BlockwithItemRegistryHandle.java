package seifres.seifchocoboriders.services.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public record BlockwithItemRegistryHandle<T extends Block>(
    RegistryHandle<T> block,
    RegistryHandle<? extends BlockItem> item
) {
}
