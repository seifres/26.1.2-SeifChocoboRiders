package seifres.seifchocoboriders.client.model;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.client.renderer.ChocoboRendererState;

public class ChocoboEntityModel extends EntityModel<ChocoboRendererState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Constants.id( "chocoboentity"), "main");
	private final ModelPart main;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart beak;
	private final ModelPart crest;
	private final ModelPart neck;
	private final ModelPart wings;
	private final ModelPart wing_left;
	private final ModelPart wing_right;
	private final ModelPart legs;
	private final ModelPart leg_right;
	private final ModelPart foot_right;
	private final ModelPart leg_left;
	private final ModelPart foot_left;
	private final ModelPart tail;
	private final ModelPart feather;
	private final ModelPart feather2;
	private final ModelPart feather3;
	private final ModelPart feather4;
	private final ModelPart feather5;
	private final ModelPart feather6;
	private final ModelPart feather7;
	private final ModelPart feather8;
	private final ModelPart feather9;
	private final ModelPart feather10;

	public ChocoboEntityModel(ModelPart root) {
		super(root);
		this.main = root.getChild("main");
		this.body = this.main.getChild("body");
		this.wings = this.body.getChild("wings");
		this.wing_left = this.wings.getChild("wing_left");
		this.wing_right = this.wings.getChild("wing_right");
		this.legs = this.body.getChild("legs");
		this.leg_right = this.legs.getChild("leg_right");
		this.foot_right = this.leg_right.getChild("foot_right");
		this.leg_left = this.legs.getChild("leg_left");
		this.foot_left = this.leg_left.getChild("foot_left");
		this.tail = this.body.getChild("tail");
		this.feather = this.tail.getChild("feather");
		this.feather2 = this.tail.getChild("feather2");
		this.feather3 = this.tail.getChild("feather3");
		this.feather4 = this.tail.getChild("feather4");
		this.feather5 = this.tail.getChild("feather5");
		this.feather6 = this.tail.getChild("feather6");
		this.feather7 = this.tail.getChild("feather7");
		this.feather8 = this.tail.getChild("feather8");
		this.feather9 = this.tail.getChild("feather9");
		this.feather10 = this.tail.getChild("feather10");
		this.neck = this.main.getChild("neck");
		this.head = this.neck.getChild("head");
		this.beak = this.head.getChild("beak");
		this.crest = this.head.getChild("crest");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-5.5F, -17.0F, -5.0F, 11.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 65).addBox(-8.0F, -14.0F, -6.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(56, 62).addBox(-8.0F, -14.0F, 2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -10.0F, -1.0F, 12.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -10.0F, -4.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 37).addBox(-11.0F, -10.0F, -1.0F, 12.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -10.0F, -2.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 37).addBox(-10.0F, -7.0F, -1.0F, 11.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -10.0F, -2.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(44, 10).addBox(-10.0F, -5.0F, -1.0F, 11.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(44, 0).addBox(-12.0F, -8.0F, -1.0F, 13.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition wing_left = wings.addOrReplaceChild("wing_left", CubeListBuilder.create(), PartPose.offset(1.8038F, -21.1119F, 6.0646F));

		PartDefinition cube_r6 = wing_left.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(58, 53).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1962F, 5.1119F, -0.0646F, 0.0F, 0.1745F, -0.1309F));

		PartDefinition cube_r7 = wing_left.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(34, 87).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, 5.1119F, -0.0646F, 0.0F, 0.0873F, -0.0436F));

		PartDefinition cube_r8 = wing_left.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(50, 86).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, 3.1119F, -0.0646F, 0.0F, 0.0873F, -0.3927F));

		PartDefinition cube_r9 = wing_left.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(44, 17).addBox(-13.0F, -2.0F, -1.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, 6.1119F, -0.0646F, 0.0F, 0.1745F, -0.3491F));

		PartDefinition cube_r10 = wing_left.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(42, 34).addBox(-13.0F, -3.0F, -1.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1962F, 8.1119F, -0.0646F, 0.0F, 0.0873F, -0.3491F));

		PartDefinition cube_r11 = wing_left.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(28, 56).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, 6.1119F, -0.0646F, 0.0F, 0.0873F, -0.1745F));

		PartDefinition cube_r12 = wing_left.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(20, 65).addBox(-11.0F, -2.0F, -1.0F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, 4.1119F, -0.0646F, 0.0F, 0.0873F, -0.1309F));

		PartDefinition cube_r13 = wing_left.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 56).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1962F, 3.1119F, -0.0646F, 0.0F, 0.0873F, -0.0873F));

		PartDefinition cube_r14 = wing_left.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(56, 56).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1962F, 1.1119F, -0.0646F, 0.0F, 0.1745F, -0.1309F));

		PartDefinition cube_r15 = wing_left.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(30, 53).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1962F, 1.1119F, -0.0646F, 0.0F, 0.0873F, -0.0436F));

		PartDefinition wing_right = wings.addOrReplaceChild("wing_right", CubeListBuilder.create(), PartPose.offset(2.9141F, -21.1328F, -6.0574F));

		PartDefinition cube_r16 = wing_right.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(28, 62).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9141F, 5.1328F, 1.0574F, 0.0F, -0.1745F, -0.1309F));

		PartDefinition cube_r17 = wing_right.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(88, 88).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0859F, 5.1328F, 1.0574F, 0.0F, -0.0873F, -0.0436F));

		PartDefinition cube_r18 = wing_right.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(88, 84).addBox(-4.0F, -3.0F, -1.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0859F, 3.1328F, 1.0574F, 0.0F, -0.0873F, -0.3927F));

		PartDefinition cube_r19 = wing_right.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 53).addBox(-13.0F, -2.0F, -1.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0859F, 6.1328F, 1.0574F, 0.0F, -0.1745F, -0.3491F));

		PartDefinition cube_r20 = wing_right.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(36, 50).addBox(-13.0F, -3.0F, -1.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0859F, 8.1328F, 1.0574F, 0.0F, -0.0873F, -0.3491F));

		PartDefinition cube_r21 = wing_right.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 62).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0859F, 6.1328F, 1.0574F, 0.0F, -0.0873F, -0.1745F));

		PartDefinition cube_r22 = wing_right.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(66, 50).addBox(-11.0F, -2.0F, -1.0F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0859F, 4.1328F, 1.0574F, 0.0F, -0.0873F, -0.1309F));

		PartDefinition cube_r23 = wing_right.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(56, 59).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9141F, 3.1328F, 1.0574F, 0.0F, -0.0873F, -0.0873F));

		PartDefinition cube_r24 = wing_right.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(28, 59).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9141F, 1.1328F, 1.0574F, 0.0F, -0.1745F, -0.1309F));

		PartDefinition cube_r25 = wing_right.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 59).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9141F, 1.1328F, 1.0574F, 0.0F, -0.0873F, -0.0436F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-3.8891F, -10.3515F, -0.0655F));

		PartDefinition leg_right = legs.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.6025F, 0.1889F, -4.2876F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r26 = leg_right.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(26, 84).addBox(-1.5F, -10.0F, -9.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4916F, 7.1626F, 10.3531F, 0.2618F, 0.0F, 0.0F));

		PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.7145F, 9.5376F, -0.1031F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r27 = foot_right.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(80, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0106F, 0.625F, -0.4954F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r28 = foot_right.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(86, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0106F, 0.625F, 7.5046F, 0.0F, -1.4399F, 0.0F));

		PartDefinition cube_r29 = foot_right.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(72, 35).addBox(-3.0F, -1.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9894F, 0.625F, 8.5046F, 0.0F, -1.7453F, 0.0F));

		PartDefinition cube_r30 = foot_right.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(76, 62).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0106F, 0.625F, 5.5046F, 0.0F, -1.5708F, 0.0F));

		PartDefinition leg_left = legs.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0407F, -0.0956F, 4.8729F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r31 = leg_left.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(18, 84).addBox(-1.5F, -10.0F, -0.75F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7032F, 9.4472F, 2.0461F, 0.2618F, -0.0873F, 0.0F));

		PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(74, 75).addBox(-2.4396F, -1.0F, -2.0681F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(82, 35).addBox(-4.4396F, 0.0F, -1.0681F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2968F, 9.4472F, 3.0461F, 0.0F, -1.6581F, 0.0F));

		PartDefinition cube_r32 = foot_left.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(46, 65).addBox(-3.0F, -1.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5604F, 1.0F, -2.0681F, 0.0F, 0.1745F, 0.0F));

		PartDefinition cube_r33 = foot_left.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(74, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5604F, 1.0F, 0.9319F, 0.0F, -0.1309F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-7.0F, -18.5F, -0.2F));

		PartDefinition feather = tail.addOrReplaceChild("feather", CubeListBuilder.create().texOffs(70, 26).addBox(-14.0F, -6.0F, -1.0F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.2F));

		PartDefinition cube_r34 = feather.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 78).addBox(-7.0F, -4.0F, -1.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather2 = tail.addOrReplaceChild("feather2", CubeListBuilder.create().texOffs(76, 67).addBox(-13.0F, -5.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.2F, 0.0F, -0.1745F, 0.0873F));

		PartDefinition cube_r35 = feather2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(54, 79).addBox(-7.0F, -3.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather3 = tail.addOrReplaceChild("feather3", CubeListBuilder.create().texOffs(70, 42).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 1.2F, -0.3054F, 0.3054F, 0.1309F));

		PartDefinition cube_r36 = feather3.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(70, 46).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather4 = tail.addOrReplaceChild("feather4", CubeListBuilder.create().texOffs(20, 71).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -2.8F, 0.2618F, -0.2618F, 0.0873F));

		PartDefinition cube_r37 = feather4.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(54, 71).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather5 = tail.addOrReplaceChild("feather5", CubeListBuilder.create().texOffs(36, 80).addBox(-13.0F, -5.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 1.2F, 0.0F, 0.2182F, 0.0873F));

		PartDefinition cube_r38 = feather5.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(72, 80).addBox(-7.0F, -3.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather6 = tail.addOrReplaceChild("feather6", CubeListBuilder.create().texOffs(70, 37).addBox(-14.0F, -6.0F, -1.0F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 1.2F, 0.0F, 0.0436F, 0.1309F));

		PartDefinition cube_r39 = feather6.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(18, 79).addBox(-7.0F, -4.0F, -1.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather7 = tail.addOrReplaceChild("feather7", CubeListBuilder.create().texOffs(72, 31).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.2F, -0.3054F, 0.3054F, -0.1745F));

		PartDefinition cube_r40 = feather7.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 74).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather8 = tail.addOrReplaceChild("feather8", CubeListBuilder.create().texOffs(74, 0).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -0.8F, 0.3927F, -0.3054F, -0.1745F));

		PartDefinition cube_r41 = feather8.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(74, 4).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather9 = tail.addOrReplaceChild("feather9", CubeListBuilder.create().texOffs(74, 16).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 1.2F, -0.8727F, 0.3927F, 0.1309F));

		PartDefinition cube_r42 = feather9.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(74, 71).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition feather10 = tail.addOrReplaceChild("feather10", CubeListBuilder.create().texOffs(20, 75).addBox(-12.0F, -3.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -1.8F, 0.8727F, -0.3927F, 0.1309F));

		PartDefinition cube_r43 = feather10.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(54, 75).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition neck = main.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(42, 68).addBox(-2.5F, -8.5F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 84).addBox(-2.0F, -8.0F, 1.0F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(14, 86).addBox(-1.0F, -8.0F, 2.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(62, 86).addBox(-1.0F, -8.0F, -3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(80, 84).addBox(-2.0F, -8.0F, -2.0F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -19.5F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 20).addBox(-3.7413F, -2.9235F, -3.9941F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.2413F, -10.5765F, -0.0059F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(66, 86).addBox(0.7894F, -1.4318F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(84, 56).addBox(-1.2106F, -1.4318F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.4694F, 1.5083F, 0.0059F));

		PartDefinition cube_r44 = beak.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(0, 86).addBox(-3.5F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2106F, 1.5682F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition crest = head.addOrReplaceChild("crest", CubeListBuilder.create(), PartPose.offset(-2.7281F, -1.5847F, -0.0118F));

		PartDefinition cube_r45 = crest.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 83).addBox(-7.0F, -2.0F, -1.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9868F, 1.6611F, 4.0177F, 0.0F, 0.1745F, -0.6109F));

		PartDefinition cube_r46 = crest.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(54, 83).addBox(-7.0F, -2.0F, -1.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9868F, 1.6611F, -2.9823F, -0.0436F, -0.2618F, -0.6109F));

		PartDefinition cube_r47 = crest.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(86, 53).addBox(-4.0F, -2.0F, -1.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9868F, 0.6611F, -2.9823F, -0.0436F, -0.2618F, -0.6109F));

		PartDefinition cube_r48 = crest.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(0, 89).addBox(-4.0F, -2.0F, -1.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9868F, 0.6611F, 4.0177F, 0.0436F, 0.2182F, -0.4363F));

		PartDefinition cube_r49 = crest.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(20, 68).addBox(-9.0F, -1.0F, -0.5F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9868F, -0.3389F, 2.0177F, 0.0436F, 0.1745F, 0.3054F));

		PartDefinition cube_r50 = crest.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(70, 10).addBox(-7.75F, -1.0F, -1.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9868F, -0.3389F, -2.9823F, -0.1309F, -0.2182F, 0.3054F));

		PartDefinition cube_r51 = crest.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(70, 13).addBox(-8.5F, -1.0F, -1.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9868F, -0.3389F, 0.0177F, 0.0F, -0.0436F, 0.6545F));

		PartDefinition cube_r52 = crest.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(34, 84).addBox(-5.0F, -1.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9868F, 2.6611F, 1.0177F, 0.0F, -0.0436F, 0.3054F));

		PartDefinition cube_r53 = crest.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(70, 23).addBox(-8.0F, -1.0F, -1.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9868F, 2.6611F, -0.9823F, 0.0F, -0.0436F, 0.4363F));

		PartDefinition cube_r54 = crest.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(70, 20).addBox(-8.0F, -1.0F, -1.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9868F, 0.6611F, 1.0177F, 0.0F, -0.0436F, 0.4363F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NonNull ChocoboRendererState state) {
		super.setupAnim(state);

		// --- Head Looking ------
		this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
		this.head.yRot = state.yRot * (float) (Math.PI / 180.0);

		// --- Is Sitting ------
		if (state.isSitting) {
			this.body.yRot = 0.0F;
			this.body.xRot = 0.0F;

			this.leg_left.xRot = 1.2F;   // bent forward
			this.leg_right.xRot = 1.2F;
			this.wing_left.zRot = -0.3F; // tuck wings slightly
			this.wing_right.zRot = 0.3F;

			this.main.y += 8.0F;  // Lower entire entity to ground
			return;
		}

		// Flight pose
		if (state.isGliding) {
			// Tuck legs up
			this.leg_left.xRot  = -0.8F;
			this.leg_right.xRot = -0.8F;

			// Spread and flap wings using animation speed/strength from entity
			float flapAngle = Mth.sin(state.wingAnimationSpeed) * state.wingAnimationStrength * 1.2F;
			this.wing_left.xRot  = 0.6F + flapAngle;
			this.wing_right.xRot = -0.6F - flapAngle;

			// Tilt tail up slightly
			//this.tail.xRot = 0.0F;
			this.tail.zRot = 0.3F;
			return;
		}


		// Walk animation — only when grounded
		float walk = state.walkAnimationPos;
		float speed = state.walkAnimationSpeed;
		this.leg_left.xRot   = Mth.cos(walk * 0.6662F) * 1.4F * speed;
		this.leg_right.xRot  = Mth.cos(walk * 0.6662F + (float) Math.PI) * 1.4F * speed;
		this.wing_right.zRot = Mth.cos(walk * 0.6662F) * .25F * speed;
		this.wing_left.zRot  = Mth.cos(walk * 0.6662F + (float) Math.PI) * .25F * speed;
		this.tail.yRot       = Mth.cos(walk * 0.6662F) * .60F * speed;
		this.tail.xRot       = 0.0F;


	}
}
