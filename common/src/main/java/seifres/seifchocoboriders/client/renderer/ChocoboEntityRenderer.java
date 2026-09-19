package seifres.seifchocoboriders.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.client.model.ChocoboEntityModel;
import seifres.seifchocoboriders.entities.ChocoboEntity;

public class ChocoboEntityRenderer extends MobRenderer<ChocoboEntity, ChocoboRendererState, ChocoboEntityModel> {

    private static final Identifier BLACK_CHOCOBO = Constants.id("textures/entity/chocobo_texture_black.png");
    private static final Identifier BLUE_CHOCOBO = Constants.id("textures/entity/chocobo_texture_blue.png");
    private static final Identifier BROWN_CHOCOBO = Constants.id("textures/entity/chocobo_texture_brown_chocobo.png");
    private static final Identifier CYAN_CHOCOBO = Constants.id("textures/entity/chocobo_texture_cyan.png");
    private static final Identifier GRAY_CHOCOBO = Constants.id("textures/entity/chocobo_texture_gray.png");
    private static final Identifier GREEN_CHOCOBO = Constants.id("textures/entity/chocobo_texture_green.png");
    private static final Identifier LIGHTBLUE_CHOCOBO = Constants.id("textures/entity/chocobo_texture_lightblue.png");
    private static final Identifier LIGHTGRAY_CHOCOBO = Constants.id("textures/entity/chocobo_texture_lightgray.png");
    private static final Identifier LIME_CHOCOBO = Constants.id("textures/entity/chocobo_texture_lime.png");
    private static final Identifier MAGENTA_CHOCOBO = Constants.id("textures/entity/chocobo_texture_magenta.png");
    private static final Identifier ORANGE_CHOCOBO = Constants.id("textures/entity/chocobo_texture_orange.png");
    private static final Identifier PINK_CHOCOBO = Constants.id("textures/entity/chocobo_texture_pink.png");
    private static final Identifier PURPLE_CHOCOBO = Constants.id("textures/entity/chocobo_texture_purple.png");
    private static final Identifier RED_CHOCOBO = Constants.id("textures/entity/chocobo_texture_red.png");
    private static final Identifier WHITE_CHOCOBO = Constants.id("textures/entity/chocobo_texture_white.png");
    private static final Identifier YELLOW_CHOCOBO = Constants.id("textures/entity/chocobo_texture_yellow.png");
    private static final Identifier TEXTURE_LOCATION = Constants.id("textures/entity/chocobo_texture_yellow.png");

    private static final float BABY_MODEL_SCALE = 0.5f;

    public ChocoboEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ChocoboEntityModel(context.bakeLayer(ChocoboEntityModel.LAYER_LOCATION)), 0.6f);
    }


    public @NonNull Identifier getTextureLocation(@NonNull ChocoboRendererState state) {
       return state.texture;
    }

    @Override
    public @NonNull ChocoboRendererState createRenderState() {
        return new ChocoboRendererState();
    }

    @Override
    public void extractRenderState(@NonNull ChocoboEntity entity, @NonNull ChocoboRendererState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isSitting = entity.isInSittingPose();
        state.isGliding = entity.isGliding();
        state.wingAnimationSpeed = entity.getWingAnimationSpeed();
        state.wingAnimationStrength = entity.getWingAnimationStrength();
        state.yHeadRot = Mth.lerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
        state.yBodyRot = Mth.lerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        state.texture = entity.getVariant().getTexture();
    }

    @Override
    protected void scale(@NonNull ChocoboRendererState state, @NonNull PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(BABY_MODEL_SCALE, BABY_MODEL_SCALE, BABY_MODEL_SCALE);
        }
    }

}
