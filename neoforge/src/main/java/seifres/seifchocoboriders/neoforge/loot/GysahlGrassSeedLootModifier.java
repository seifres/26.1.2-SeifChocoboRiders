package seifres.seifchocoboriders.neoforge.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.init.ModItems;

// Appends a small, independent chance of a Gysahl Greens seed to whatever short_grass loot table
// ends up loaded, instead of replacing data/minecraft/loot_table/blocks/short_grass.json outright.
// Global loot modifiers run *after* the base table resolves and hand back the already-generated
// loot list, so this composes cleanly with any other mod/datapack that also edits short_grass loot
// - nobody's file gets silently clobbered by the other.
//
// Which table this applies to is decided in this modifier's own data file
// (data/seifchocoboriders/loot_modifiers/gysahl_grass_seed.json) via the "neoforge:loot_table_id"
// condition, not in this class - this class only decides *what* to add once it's already gated to
// the right table.
//
// NOTE: this is new, unverified-against-the-real-classpath NeoForge API surface for this build (the
// same caveat that applied to the SpawnPlacements reflection fix) - LootModifier's exact method name
// (doApply) and the codecStart() helper have been stable across many Forge/NeoForge versions, but if
// this doesn't compile as-is, paste the error and we'll adjust.
//
// 26.1.2-specific gotcha already hit once: LootContextParams.TOOL is typed as
// ContextKey<ItemInstance>, not ContextKey<ItemStack> - ItemInstance is a new interface ItemStack
// implements (alongside DataComponentHolder), and the accessor is getOptionalParameter(...), not
// getParamOrNull(...). ItemInstance still exposes .is(Item) (from TypedInstance<Item>), so no cast
// back to ItemStack is needed here.
public class GysahlGrassSeedLootModifier extends LootModifier {
    public static final MapCodec<GysahlGrassSeedLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, GysahlGrassSeedLootModifier::new));

    private static final float CHANCE = 0.04f;

    public GysahlGrassSeedLootModifier(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Override
    protected @NonNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        ItemInstance tool = context.getOptionalParameter(LootContextParams.TOOL);
        boolean sheared = tool != null && tool.is(Items.SHEARS);

        // Shearing collects the grass plant itself - incidental seeds only make sense when the
        // block is actually broken, matching how vanilla's own wheat-seed drop on grass works.
        if (!sheared && random.nextFloat() < CHANCE) {
            generatedLoot.add(new ItemStack(ModItems.GYSAHL_GREENS_SEED.get()));
        }
        return generatedLoot;
    }

    @Override
    public @NonNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
