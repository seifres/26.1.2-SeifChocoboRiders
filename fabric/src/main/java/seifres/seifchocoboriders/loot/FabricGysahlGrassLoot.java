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

public final class FabricGysahlGrassLoot {
    private static final ResourceKey<LootTable> SHORT_GRASS =
            ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("blocks/short_grass"));

    private static final float CHANCE = 0.04f;

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!SHORT_GRASS.equals(key)) {
                return;
            }

            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(ModItems.GYSAHL_GREENS_SEED.get())

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
