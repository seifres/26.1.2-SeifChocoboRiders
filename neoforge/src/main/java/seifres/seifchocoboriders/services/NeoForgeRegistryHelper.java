package seifres.seifchocoboriders.services;

import net.minecraft.core.registries.Registries;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.services.types.IRegistryHelper;
import seifres.seifchocoboriders.services.util.RegistryHandle;
import net.minecraft.world.entity.ai.attributes.Attribute;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Constants.MOD_ID);
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Constants.MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU,
            Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        ENTITIES.register(eventBus);
        ATTRIBUTES.register(eventBus);
        MENU_TYPES.register(eventBus);
    }

    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        Identifier id = Constants.id(name);
        DeferredBlock<T> deferredItem = BLOCKS.registerBlock(name, block);
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return deferredItem.get();
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name,properties -> item.apply(block.get(), properties));
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        Identifier id = Constants.id(name);
        DeferredItem<T> deferredItem = ITEMS.registerItem(name, item);
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return deferredItem.get();
            }
        };
    }

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name,
                                                                               EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.entityTypeKey(name);
        Identifier id = key.identifier();
        DeferredHolder<EntityType<?>, EntityType<T>> deferredEntityType = ENTITIES.register(name,
                () -> builder.build(key));
        return new RegistryHandle<EntityType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public EntityType get() { return deferredEntityType.get(); }
        };
    }

    @Override
    public <T extends Attribute> RegistryHandle<T> registerAttribute(String name, Supplier<T> attribute) {
        Identifier id = Constants.id(name);
        DeferredHolder<Attribute, T> deferred = ATTRIBUTES.register(name, attribute);

        return new RegistryHandle<T>() {
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            public T get() {
                return deferred.get();
            }
        };
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerEntityMenuType(String name, Class<T> menuClass) {
        Identifier id = Constants.id(name);
        DeferredHolder<MenuType<?>, MenuType<T>> deferredMenuType = MENU_TYPES.register(name, () -> (MenuType<T>) switch (name) {
            case "chocobo_training" -> IMenuTypeExtension.create(ChocoboTrainingMenu::new);

            default -> throw new IllegalStateException("Unexpected value: " + name);
        });
        return new RegistryHandle<MenuType<T>>() {
            @Override
            public Identifier id() { return id;}
            @Override
            public MenuType<T> get() { return deferredMenuType.get();}
        };
    }

    /*
    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerMenuType(String name, BiFunction<Integer, Inventory, T> factory) {
        Identifier id = Constants.id(name);
        DeferredHolder<MenuType<?>, MenuType<T>> deferred = MENUS.register(name,
                () -> new MenuType<>((windowId, inv) -> factory.apply(windowId, inv), FeatureFlags.VANILLA_SET));

        return new RegistryHandle<MenuType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }
            @Override
            @SuppressWarnings("unchecked")
            public MenuType<T> get() {
                return (MenuType<T>) deferred.get();
            }
        };
    }
    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerEntityMenuType(String name, Class<ChocoboTrainingMenu> factory) {
        Identifier id = Constants.id(name);
        DeferredHolder<MenuType<?>, MenuType<T>> deferred = MENUS.register(name,
                () -> IMenuTypeExtension.create((windowId, inv, buf) -> factory.create(windowId, inv, buf.readVarInt())));

        return new RegistryHandle<MenuType<T>>() {
            @Override public Identifier id() { return id; }
            @Override @SuppressWarnings("unchecked")
            public MenuType<T> get() { return (MenuType<T>) deferred.get(); }
        };
    }
*/

}
