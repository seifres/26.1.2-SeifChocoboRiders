package seifres.seifchocoboriders.entities;

import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;
import net.minecraft.world.entity.player.Player;

public class ChocoboTrainingContainer extends SimpleContainer {
    public static final int TOTAL_SLOTS = ChocoboStat.values().length;
    public static final int MAX_LEVEL = 100;
    public static final long TOTAL_XP_TO_MAX = 200_000;

    /** XP granted by each scroll tier (index 0 = Tier 1 ... index 4 = Tier 5), shared across every stat. */
    private static final long[] XP_PER_TIER = {50, 200, 500, 1000, 2000};

    /** cumulative[n] = total XP needed to REACH level n. Quadratic curve: level 100 costs far more than level 1. */
    private static final long[] CUMULATIVE_XP_FOR_LEVEL = buildXpCurve();

    private static long[] buildXpCurve() {
        long[] cumulative = new long[MAX_LEVEL + 1];
        long sumOfSquares = 0;
        for (int lvl = 1; lvl <= MAX_LEVEL; lvl++) {
            sumOfSquares += (long) lvl * lvl;
        }
        double scale = TOTAL_XP_TO_MAX / (double) sumOfSquares;
        long running = 0;
        for (int lvl = 1; lvl <= MAX_LEVEL; lvl++) {
            long cost = Math.round(lvl * lvl * scale);
            running += Math.max(cost, 1);
            cumulative[lvl] = running;
        }
        cumulative[MAX_LEVEL] = TOTAL_XP_TO_MAX; // guarantee level 100 is reachable at exactly the intended XP total
        return cumulative;
    }

    //** Binary search: highest level whose cumulative cost is <= exp, capped at MAX_LEVEL. */
    public static int levelForExp(long exp) {
        long clamped = Math.min(exp, TOTAL_XP_TO_MAX);
        int lo = 0, hi = MAX_LEVEL;
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (CUMULATIVE_XP_FOR_LEVEL[mid] <= clamped) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    private final ChocoboEntity chocobo;
    private final long[] expTotals = new long[TOTAL_SLOTS];

    public ChocoboTrainingContainer(ChocoboEntity chocobo) {
        super(TOTAL_SLOTS);
        this.chocobo = chocobo;
    }

    public static ChocoboStat statForSlot(int slotIndex) {
        return ChocoboStat.values()[slotIndex];
    }

    public int getActiveSlot() {
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            if (!getItem(i).isEmpty()) return i;
        }
        return -1;
    }

    @Override
    public boolean canPlaceItem(int slot, @NonNull ItemStack stack) {
        if (!(stack.getItem() instanceof ChocoboScrollTrainingItem)) return false;
        int active = getActiveSlot();
        return active == -1 || active == slot;
    }

    public boolean consumeActiveTraining() {
        int slot = getActiveSlot();
        if (slot == -1) return false;

        ItemStack stack = getItem(slot);
        if (!(stack.getItem() instanceof ChocoboScrollTrainingItem scroll)) return false;

        if (expTotals[slot] >= TOTAL_XP_TO_MAX) {
            return false; // already at max level — refuse the click, don't consume the scroll
        }

        int tierIndex = scroll.getLevel() - 1;
        expTotals[slot] = Math.min(expTotals[slot] + XP_PER_TIER[tierIndex], TOTAL_XP_TO_MAX);

        removeItem(slot, 1);
        setChanged();
        return true;
    }

    /**
     * Called when the training menu closes. Any scroll still sitting in a training slot
     * (started but not maxed out, or never trained at all) is handed back to the player
     * rather than left parked in this container - if their inventory has no room, it drops
     * at their feet instead. Server-side only; the caller is expected to check that.
     */
    public void ejectRemainingItems(Player player) {
        for (int i = 0; i < TOTAL_SLOTS; i++) {
            ItemStack stack = getItem(i);
            if (stack.isEmpty()) continue;

            setItem(i, ItemStack.EMPTY);
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }
        }
        setChanged();
    }

    public void returnRemainingIfMaxed(Player player) {
        int slot = getActiveSlot();
        if (slot == -1) return;
        if (expTotals[slot] < TOTAL_XP_TO_MAX) return;

        ItemStack remaining = getItem(slot);
        if (remaining.isEmpty()) return;

        setItem(slot, ItemStack.EMPTY);
        if (!player.getInventory().add(remaining)) {
            player.drop(remaining, false); // inventory full — drop at their feet instead of deleting it
        }
        setChanged();
    }

    public double getTrainingValue(ChocoboStat stat) {
        return levelForExp(expTotals[stat.ordinal()]) * stat.perLevelIncrease();
    }

    public double getPreviewValue(ChocoboStat stat) {
        int i = stat.ordinal();
        ItemStack stack = getItem(i);
        if (!(stack.getItem() instanceof ChocoboScrollTrainingItem scroll)) {
            return getTrainingValue(stat);
        }

        int tierIndex = scroll.getLevel() - 1;
        long previewExp = Math.min(expTotals[i] + XP_PER_TIER[tierIndex], TOTAL_XP_TO_MAX);
        return levelForExp(previewExp) * stat.perLevelIncrease();
    }

    public int getLevel(ChocoboStat stat) {
        return levelForExp(expTotals[stat.ordinal()]);
    }

    public long getExp(ChocoboStat stat) {
        return expTotals[stat.ordinal()];
    }

    public void setExp(ChocoboStat stat, long exp) {
        expTotals[stat.ordinal()] = exp;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (chocobo != null) {
            chocobo.recalculateTrainingBonuses();
        }
    }

    public void save(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.getItems());
        for (ChocoboStat stat : ChocoboStat.values()) {
            output.putLong("Exp_" + stat.name(), expTotals[stat.ordinal()]);
        }
    }

    public void load(ValueInput input) {
        ContainerHelper.loadAllItems(input, this.getItems());
        for (ChocoboStat stat : ChocoboStat.values()) {
            expTotals[stat.ordinal()] = input.getLongOr("Exp_" + stat.name(), 0L);
        }
    }

    public ChocoboEntity getChocobo() {
        return chocobo;
    }
}