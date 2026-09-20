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
