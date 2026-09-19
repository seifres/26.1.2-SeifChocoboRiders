package seifres.seifchocoboriders.loot;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import seifres.seifchocoboriders.init.ModItems;

// Fabric counterpart to the NeoForge side's GysahlGrassSeedLootModifier (a Global Loot Modifier).
// Fabric API's LootTableEvents.MODIFY fires once per loot table as it's *being built*, after
// vanilla's own JSON (and any other datapack's override of it) has already been merged in, and
// lets us append an extra pool rather than replace the table outright - so this composes with
// whatever else is touching short_grass instead of clobbering it.
//
// NOTE: unverified against this project's actual Fabric/Fabric-API classpath - loot builder API
// shapes (LootPool/LootItem/ApplyBonusCount, and the enchantment-as-registry-Holder plumbing in
// particular) have moved around release to release. If this doesn't compile as-is, paste the
// error and we'll adjust, same as we did for the SpawnPlacements fix.
public final class FabricGysahlGrassLoot {
    private static final ResourceKey<LootTable> SHORT_GRASS =
            ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("blocks/short_grass"));

    private static final float CHANCE = 0.04f;

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!SHORT_GRASS.equals(key)) {
                return;
            }

            // LootTable.Builder#withPool() takes the LootPool.Builder itself, not a built
            // LootPool - don't call .build() here, the table assembles it internally.
            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(ModItems.GYSAHL_GREENS_SEED.get())
                            // Shearing collects the grass plant itself - incidental seeds only make
                            // sense when the block is actually broken, matching short_grass's own
                            // wheat-seed drop. MatchTool.toolMatches() takes an ItemPredicate.Builder
                            // (not an Ingredient), built against the item registry lookup handed to
                            // this callback.
                            .when(InvertedLootItemCondition.invert(MatchTool.toolMatches(
                                    ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), Items.SHEARS))))
                            .when(LootItemRandomChanceCondition.randomChance(CHANCE))
                            .apply(ApplyBonusCount.addUniformBonusCount(
                                    registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 1)));

            tableBuilder.withPool(pool);
        });
    }

    private FabricGysahlGrassLoot() {
    }
}
