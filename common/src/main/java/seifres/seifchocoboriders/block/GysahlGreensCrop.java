package seifres.seifchocoboriders.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.init.ModItems;

public class GysahlGreensCrop extends CropBlock {
    public static final MapCodec<GysahlGreensCrop> CODEC = simpleCodec(GysahlGreensCrop::new);
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE;
    private static final VoxelShape[] SHAPES;

    public GysahlGreensCrop(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull MapCodec<GysahlGreensCrop> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected @NonNull ItemLike getBaseSeedId() {return ModItems.GYSAHL_GREENS_SEED.get(); }


    @Override
    protected void randomTick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (random.nextInt(MAX_AGE) != 0) {
            super.randomTick(state, level, pos, random);
        }

    }

    @Override
    protected int getBonemealAgeIncrease(@NonNull Level level) {
        return super.getBonemealAgeIncrease(level) / 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES[this.getAge(state)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.SUPPORTS_VEGETATION);
    }

    static {
        AGE = BlockStateProperties.AGE_5;
        SHAPES = Block.boxes(5, (age) -> Block.column((double)16.0F, (double)0.0F, (double)(2 + age * 2)));
    }
}