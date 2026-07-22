package seifres.seifchocoboriders;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.state.BlockState;
import seifres.seifchocoboriders.init.ModBlocks;

import java.util.Set;

public final class ChocoboVillagerData {
    public static final Identifier PROFESSION_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID,
            "chocobo_rancher");
    public static final  Identifier POI_ID = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gysahl_greens_crate");
    public static Set<BlockState> jobSiteStates() {
        return ImmutableSet.copyOf(ModBlocks.GYSAHL_GREENS_CRATE.block().get().getStateDefinition().getPossibleStates());
    }

    public static Int2ObjectMap<ResourceKey<TradeSet>> tradeSets() {
        Int2ObjectMap<ResourceKey<TradeSet>> map = new Int2ObjectOpenHashMap<>();

        for(int level = 1; level <= 5; ++level) {
            map.put(level, ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath("seifchocoboriders", "chocobo_rancher/level_" + level)));
        }

        return map;
    }
    private ChocoboVillagerData() {}
}
/*
{
        "id": "seifchocoboriders:village/villager_profession/chocobo_rancher",
        "workstation": "Modblocks:gysahl_greens_crate",
        "gathering_items": [
        "minecraft:wheat",
        "minecraft:carrot",
        "minecraft:potato"
        ],
        "secondary_job_site_blocks": [],
        "work_sound": "minecraft:entity.villager.work_farmer"
        }

 */