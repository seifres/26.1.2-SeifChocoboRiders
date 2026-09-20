package seifres.seifchocoboriders.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.init.ModDataComponents;

import java.util.UUID;

public class ChocoboWhistleItem extends Item {

    private static final int COOLDOWN_TICKS = 30 * 20; // 30 seconds

    public ChocoboWhistleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.isShiftKeyDown() || !(target instanceof ChocoboEntity chocobo)) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (stack.has(ModDataComponents.BOUND_CHOCOBO.get())) {
            player.sendOverlayMessage(Component.translatable("message.seifchocoboriders.whistle_already_bound"));
            return InteractionResult.FAIL;
        }

        if (!chocobo.isTame() || !chocobo.isOwnedBy(player)) {
            player.sendOverlayMessage(Component.translatable("message.seifchocoboriders.whistle_not_trained"));
            return InteractionResult.FAIL;
        }

        stack.set(ModDataComponents.BOUND_CHOCOBO.get(), chocobo.getUUID());
        stack.set(ModDataComponents.WHISTLE_COLOR.get(), chocobo.getVariant());
        applyBoundName(stack, chocobo.getName());
        // interactLivingEntity() is handed a copy of the held stack (most noticeably in
        // creative mode) - without writing it back, the component changes above never
        // reach the real item, so the success message shows but the whistle stays unbound.
        player.setItemInHand(hand, stack);
        player.sendOverlayMessage(Component.translatable("message.seifchocoboriders.whistle_bound"));
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.FAIL;
        }

        UUID boundId = stack.get(ModDataComponents.BOUND_CHOCOBO.get());
        if (boundId == null) {
            player.sendOverlayMessage(Component.translatable("message.seifchocoboriders.whistle_unbound"));
            return InteractionResult.FAIL;
        }

        Entity entity = serverLevel.getEntity(boundId);
        if (!(entity instanceof ChocoboEntity chocobo) || !chocobo.isAlive()) {
            player.sendOverlayMessage(Component.translatable("message.seifchocoboriders.whistle_no_chocobo"));
            return InteractionResult.FAIL;
        }

        Vec3 pos = player.position();
        chocobo.teleportTo(pos.x, pos.y, pos.z);
        player.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);
        return InteractionResult.SUCCESS;
    }

    public static void applyBoundName(ItemStack stack, Component chocoboName) {
        Component baseName = Component.translatable(stack.getItem().getDescriptionId());
        stack.set(DataComponents.CUSTOM_NAME, baseName.copy().append(Component.literal(" - ")).append(chocoboName));
    }
}
