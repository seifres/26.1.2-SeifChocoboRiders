package seifres.seifchocoboriders.items;

import net.minecraft.world.item.Item;

public class ChocoboScrollTrainingItem extends Item {
    private final int level;

    public ChocoboScrollTrainingItem(Properties properties, int level) {
        super(properties);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}