package seifres.seifchocoboriders.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.client.model.ChocoboEntityModel;
import seifres.seifchocoboriders.entities.ChocoboEntity;

public class ChocoboEntityRenderer extends MobRenderer<ChocoboEntity, LivingEntityRenderState, ChocoboEntityModel> {

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

    public ChocoboEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ChocoboEntityModel(context.bakeLayer(ChocoboEntityModel.LAYER_LOCATION)), 0.6f);
    }


    @Override
    public @NonNull Identifier getTextureLocation(@NonNull LivingEntityRenderState livingEntityRenderState) {

        /*Identifier tmpColorVar;
        switch (state.variant) {
            case BLACK -> tmpColorVar = BLACK_CHOCOBO;
            case BLUE -> tmpColorVar = BLUE_CHOCOBO;
            case BROWN -> tmpColorVar = BROWN_CHOCOBO;
            case CYAN -> tmpColorVar = CYAN_CHOCOBO;
            case GRAY -> tmpColorVar = GRAY_CHOCOBO;
            case GREEN -> tmpColorVar = GREEN_CHOCOBO;
            case LIGHTBLUE -> tmpColorVar = LIGHTBLUE_CHOCOBO;
            case LIGHTGRAY -> tmpColorVar = LIGHTGRAY_CHOCOBO;
            case LIME -> tmpColorVar = LIME_CHOCOBO;
            case MAGENTA -> tmpColorVar = MAGENTA_CHOCOBO;
            case ORANGE -> tmpColorVar = ORANGE_CHOCOBO;
            case PINK -> tmpColorVar = PINK_CHOCOBO;
            case PURPLE -> tmpColorVar = PURPLE_CHOCOBO;
            case RED -> tmpColorVar = RED_CHOCOBO;
            case WHITE -> tmpColorVar = WHITE_CHOCOBO;
            case YELLOW -> tmpColorVar = YELLOW_CHOCOBO;
            default -> throw new MatchException((String) null, (Throwable) null);
        }*/

        return TEXTURE_LOCATION;
    }

    @Override
    public @NonNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
