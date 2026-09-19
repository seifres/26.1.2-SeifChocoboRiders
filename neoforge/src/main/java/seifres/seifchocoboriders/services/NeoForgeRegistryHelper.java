package seifres.seifchocoboriders.services;

import net.minecraft.core.component.DataComponentType;
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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.entities.ChocoboTrainingMenu;
import seifres.seifchocoboriders.services.types.IRegistryHelper;
import seifres.seifchocoboriders.services.util.RegistryHandle;
import net.minecraft.world.entity.ai.attributes.Attribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Constants.MOD_ID);
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Constants.MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU,
            Constants.MOD_ID);

    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    // Populated by registerCreativeTab(...), which runs during CommonClass.init() - i.e.
    // before register(IEventBus) below is ever called, so the real per-mod event bus isn't
    // available yet at that point. We stash each tab's items here and add a single listener
    // once the mod bus actually is available, in register(IEventBus) below.
    private static final Map<ResourceKey<CreativeModeTab>, List<? extends RegistryHandle<? extends ItemLike>>> TAB_ITEMS =
            new HashMap<>();

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        DATA_COMPONENTS.register(eventBus);
        ITEMS.register(eventBus);
        ENTITIES.register(eventBus);
        ATTRIBUTES.register(eventBus);
        MENU_TYPES.register(eventBus);
        CREATIVE_TABS.register(eventBus);
        eventBus.addListener(NeoForgeRegistryHelper::onBuildCreativeModeTabContents);

    }

    // BuildCreativeModeTabContentsEvent is an IModBusEvent, so it must be registered on the
    // per-mod event bus (via eventBus.addListener above) rather than NeoForge.EVENT_BUS (the
    // common/game bus) - registering an IModBusEvent listener on the game bus throws at mod
    // construction time.
    private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        List<? extends RegistryHandle<? extends ItemLike>> items = TAB_ITEMS.get(event.getTabKey());
        if (items != null) {
            for (RegistryHandle<? extends ItemLike> item : items) {
                event.accept(item.get());
            }
        }
    }

    @Override
    public <T> RegistryHandle<DataComponentType<T>> registerDataComponent(String name,
                                                                          UnaryOperator<DataComponentType.Builder<T>> builder) {
        Identifier id = Constants.id(name);
        DeferredHolder<DataComponentType<?>, DataComponentType<T>> deferred =
                DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());
        return new RegistryHandle<DataComponentType<T>>() {
            @Override
            public Identifier id() { return id; }
            @Override
            public DataComponentType<T> get() { return deferred.get(); }
        };
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

    @Override
    public RegistryHandle<CreativeModeTab> registerCreativeTab(String name, UnaryOperator<CreativeModeTab.Builder> builder,
                                                                 List<? extends RegistryHandle<? extends ItemLike>> items) {
        ResourceKey<CreativeModeTab> key = IRegistryHelper.creativeTabKey(name);
        Identifier id = key.identifier();
        DeferredHolder<CreativeModeTab, CreativeModeTab> deferred = CREATIVE_TABS.register(name,
                () -> builder.apply(CreativeModeTab.builder()).build());

        // Populate contents via BuildCreativeModeTabContentsEvent rather than the builder's
        // own .displayItems(...) - see the note on IRegistryHelper#registerCreativeTab for why
        // (CreativeModeTab.Output is protected in NeoForge's patched Minecraft artifact). We
        // can't register the listener here directly - this method runs during CommonClass.init(),
        // before the real per-mod event bus is available (see register(IEventBus) above) - so we
        // just stash the items and let the single listener registered there handle it.
        TAB_ITEMS.put(key, items);

        return new RegistryHandle<CreativeModeTab>() {
            @Override
            public Identifier id() { return id; }
            @Override
            public CreativeModeTab get() { return deferred.get(); }
        };
    }



}
