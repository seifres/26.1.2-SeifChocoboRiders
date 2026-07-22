package seifres.seifchocoboriders.services.util;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;


public record SoundRegistryHandle<T extends Sound>(
        RegistryHandle<T> sound) {
}