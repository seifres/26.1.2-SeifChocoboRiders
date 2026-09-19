package seifres.seifchocoboriders.services.types;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.services.util.BlockwithItemRegistryHandle;
import seifres.seifchocoboriders.services.util.RegistryHandle;
import net.minecraft.world.entity.ai.attributes.Attribute;
import java.util.List;
import java.util.function.Supplier;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface IRegistryHelper {

    //Register Blocks
    default <T extends Block> BlockwithItemRegistryHandle<T> registerBlockWithItem(String name,
                                                                                Function<BlockBehaviour.Properties, T> block){
        return registerBlockWithItem(name, block, BlockItem::new);
    }
    default <T extends Block>BlockwithItemRegistryHandle<T> registerBlockWithItem(String name,
                                                                       Function<BlockBehaviour.Properties, T> block,
                                                                               BiFunction<Block, Item.Properties,
                                                                                       BlockItem> item) {
        RegistryHandle<T> blockHandle = registerBlock(name, block);
        RegistryHandle<BlockItem> itemHandle = registerBlockItem(name, blockHandle, item);
        return new BlockwithItemRegistryHandle<>(blockHandle,itemHandle);
    }

    <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block);

    <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block,
                                                              BiFunction<Block, Item.Properties, T> item);

    static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Constants.id(name));
    }

    //Register Data for Items
    <T> RegistryHandle<DataComponentType<T>> registerDataComponent(String name,
                                                                   UnaryOperator<DataComponentType.Builder<T>> builder);

    static ResourceKey<DataComponentType<?>> dataComponentKey(String name) {
        return ResourceKey.create(Registries.DATA_COMPONENT_TYPE, Constants.id(name));
    }

    //Register Items.
    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }

    //Register Entity
    <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> builder);

    static ResourceKey<EntityType<?>> entityTypeKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Constants.id(name));
    }

    //Register Attributes
    <T extends Attribute> RegistryHandle<T> registerAttribute(String name, Supplier<T> attribute);

    static ResourceKey<Attribute> attributeKey(String name) {
        return ResourceKey.create(Registries.ATTRIBUTE, Constants.id(name));
    }

    //Register Entity Menu


    static ResourceKey<MenuType<?>> menuTypeKey(String name) {
        return ResourceKey.create(Registries.MENU, Constants.id(name));
    }

    <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerEntityMenuType(String name,
                                                                                         Class<T> menuClass);

    // Register Creative Mode Tabs.
    //
    // `builder` should only call things like .title(...)/.icon(...) - it must NOT call
    // .displayItems(...) itself. Common code compiles against NeoForge's own patched
    // Minecraft artifact (a separate, NeoForge-generated jar - not the plain/Fabric one),
    // and in that artifact CreativeModeTab.Output is declared *protected* with no access
    // transformer widening it, so no code outside net.minecraft.world.item can call
    // output.accept(...) - not even indirectly through the builder's .displayItems(...)
    // lambda, since the parameter type itself is inaccessible. `items` is handed to each
    // loader's own implementation instead, which populates the tab however actually works
    // there (NeoForge: BuildCreativeModeTabContentsEvent, which has its own fully-public
    // accept(); Fabric: the vanilla builder directly, since Output is public there).
    RegistryHandle<CreativeModeTab> registerCreativeTab(String name, UnaryOperator<CreativeModeTab.Builder> builder,
                                                         List<? extends RegistryHandle<? extends ItemLike>> items);

    static ResourceKey<CreativeModeTab> creativeTabKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.id(name));
    }
}
