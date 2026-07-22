package seifres.seifchocoboriders.services;


import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.network.ChocoboEntityIdPayload;
import seifres.seifchocoboriders.services.types.IRegistryHelper;
import seifres.seifchocoboriders.services.util.RegistryHandle;
import net.minecraft.world.entity.ai.attributes.Attribute;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        ResourceKey<Block> key = IRegistryHelper.blockKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(BuiltInRegistries.BLOCK, id,
                block.apply(BlockBehaviour.Properties.of().setId(key)));

        return new RegistryHandle<T>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name,properties -> item.apply(block.get(), properties));
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(BuiltInRegistries.ITEM, id, item.apply(new Item.Properties().setId(key)));

        return new RegistryHandle<>(){
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.entityTypeKey(name);
        Identifier id = key.identifier();
        EntityType<T> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(key));
        return new RegistryHandle<>(){
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            public EntityType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Attribute> RegistryHandle<T> registerAttribute(String name, Supplier<T> attribute) {
        ResourceKey<Attribute> key = IRegistryHelper.attributeKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(BuiltInRegistries.ATTRIBUTE, id, attribute.get());

        return new RegistryHandle<T>() {
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            public T get() {
                return registered;
            }
        };
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerEntityMenuType(String name, Class<T> menuClass) {
        Identifier id = Constants.id(name);
        MenuType<T> registered = Registry.register(BuiltInRegistries.MENU, id, (MenuType<T>) switch (name) {
            case "chocobo_training" -> new ExtendedMenuType<>(ChocoboTrainingMenu::new,
                    ChocoboEntityIdPayload.STREAM_CODEC);
            default -> throw new IllegalStateException("Unexpected value: " + name);
        });
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public MenuType<T> get() {
                return registered;
            }
        };
    }

/*
    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerMenuType(String name, BiFunction<Integer, Inventory, T> factory) {
        ResourceKey<MenuType<?>> key = IRegistryHelper.menuTypeKey(name);
        Identifier id = key.identifier();
        MenuType<T> registered = Registry.register(BuiltInRegistries.MENU, id,
                new MenuType<>((windowId, inv) -> factory.apply(windowId, inv), FeatureFlags.VANILLA_SET));

        return new RegistryHandle<MenuType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            public MenuType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerEntityMenuType(String name, Class<ChocoboTrainingMenu> factory) {
        ResourceKey<MenuType<?>> key = IRegistryHelper.menuTypeKey(name);
        Identifier id = key.identifier();
        MenuType<T> registered = Registry.register(BuiltInRegistries.MENU, id,
                new ExtendedMenuType<>((windowId, inv, entityId) -> factory.create(windowId, inv, entityId),
                        ByteBufCodecs.VAR_INT));

        return new RegistryHandle<MenuType<T>>() {
            @Override public Identifier id() { return id; }
            @Override public MenuType<T> get() { return registered; }
        };
    }
*/
}
