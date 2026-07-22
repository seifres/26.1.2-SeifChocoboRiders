package seifres.seifchocoboriders.client.renderer;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import seifres.seifchocoboriders.entities.ChocoboEntity;

public class ChocoboRendererState extends LivingEntityRenderState {
    public boolean isSitting;
    public boolean isGliding;
    public float yHeadRot;
    public float yBodyRot;
    public float wingAnimationSpeed;
    public float wingAnimationStrength;
    public Identifier texture;
}
