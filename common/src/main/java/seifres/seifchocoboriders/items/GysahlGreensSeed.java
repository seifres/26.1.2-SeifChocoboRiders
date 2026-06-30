package seifres.seifchocoboriders.items;

import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import seifres.seifchocoboriders.init.ModBlocks;

public class GysahlGreensSeed extends Item {
    public GysahlGreensSeed( Properties properties){ super(properties);}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        Player player = context.getPlayer();
        ItemStack itemStack = player.getItemInHand(hand);

        if (clickedFace != Direction.Up) {
            return InteractionResult.PASS;
        }

        BlockState clickedState = level.getBlockState(clickedPos);
        BlockPos plantPos = clickedPos.above();

        if (clickedState.is(Blocks.FARMLAND) && level.getBlockState(plantPos).canBeReplaced()) {

            if (!level.isClientSide()) {
                BlockState cropState = ModBlocks.GYSAHL_GREENS_CROP.block().get().defaultBlockState();

                // Set the crop block in the world
                level.setBlockAndUpdate(plantPos, cropState);

                // 4. Shrink the seed item stack by 1 if not in creative mode
                if (player != null && !player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
