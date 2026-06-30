package seifres.seifchocoboriders.datagen;

import io.netty.util.Constant;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.block.GysahlGreensCrop;
import seifres.seifchocoboriders.init.ModBlocks;
import seifres.seifchocoboriders.init.ModItems;
import seifres.seifchocoboriders.services.NeoForgeRegistryHelper;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SeifChocoboRidersLootTableProvider extends LootTableProvider {
    public SeifChocoboRidersLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Set.of(),
                List.of(
                        new SubProviderEntry(SeifChocoboRidersLootSubProvider::new , LootContextParamSets.BLOCK)
                ),
                registries
        );
    }

    private static final class SeifChocoboRidersLootSubProvider extends BlockLootSubProvider {

        SeifChocoboRidersLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = registries.lookupOrThrow(Registries.ENCHANTMENT);

            LootItemCondition.Builder isFullyGrown = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(ModBlocks.GYSAHL_GREENS_CROP.block().get())
                    .setProperties(StatePropertiesPredicate.Builder.properties()
                            .hasProperty(GysahlGreensCrop.AGE, GysahlGreensCrop.MAX_AGE));

            add(ModBlocks.GYSAHL_GREENS_CROP.block().get(), createCropDrops(
                            ModBlocks.GYSAHL_GREENS_CROP.block().get(),
                            ModItems.GYSAHL_GREENS.get(),       //if fully grown
                            ModItems.GYSAHL_GREENS_SEED.get(),  //if not fully grown
                            isFullyGrown
                    ));

                    LootTable.Builder builder = LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                            .when(isFullyGrown.invert())
                                .add(LootItem.lootTableItem(ModItems.GYSAHL_GREENS_SEED.get()))
                            .when(isFullyGrown)
                                .setRolls(ConstantValue.exactly(3))
                                .add(LootItem.lootTableItem(ModItems.GYSAHL_GREENS_SEED.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,2))))
                                .add(LootItem.lootTableItem(ModItems.GYSAHL_GREENS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1,2)))));




            dropSelf(ModBlocks.GYSAHL_GREENS_CRATE.block().get());


        }



        @Override
        protected @NonNull Iterable<Block> getKnownBlocks() {
            return NeoForgeRegistryHelper.BLOCKS.getEntries()
                    .stream()
                    .map(entry -> (Block) entry.value())
                    .toList();
        }
    }

}
