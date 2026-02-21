// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class Orca<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "orca"), "main");
	private final ModelPart head;
	private final ModelPart lowerjaw;
	private final ModelPart upperjaw;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail_fin;
	private final ModelPart left_fin;
	private final ModelPart right_fin;
	private final ModelPart back_fin;

	public Orca(ModelPart root) {
		this.head = root.getChild("head");
		this.lowerjaw = this.head.getChild("lowerjaw");
		this.upperjaw = this.head.getChild("upperjaw");
		this.body = root.getChild("body");
		this.tail = root.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail_fin = this.tail2.getChild("tail_fin");
		this.left_fin = root.getChild("left_fin");
		this.right_fin = root.getChild("right_fin");
		this.back_fin = root.getChild("back_fin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, -24.0F));

		PartDefinition lowerjaw = head.addOrReplaceChild("lowerjaw", CubeListBuilder.create().texOffs(120, 167).addBox(-14.0F, 0.0F, -32.0F, 28.0F, 10.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition upperjaw = head.addOrReplaceChild("upperjaw", CubeListBuilder.create().texOffs(120, 113).addBox(-14.0F, -20.0F, -34.0F, 28.0F, 20.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -35.0F, 0.0F, 36.0F, 35.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -24.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 113).addBox(-14.0F, -12.5F, 0.0F, 28.0F, 28.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.5F, 24.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(168, 0).addBox(-8.0F, -8.5F, 0.0F, 16.0F, 18.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 32.0F));

		PartDefinition tail_fin = tail2.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(0, 83).addBox(-27.0F, -3.0F, 0.0F, 54.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 25.0F));

		PartDefinition left_fin = partdefinition.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offsetAndRotation(18.0F, 21.0F, -23.0F, 0.0F, 0.9599F, 0.3927F));

		PartDefinition left_fin_r1 = left_fin.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(168, 50).addBox(-1.0F, 0.0F, 0.0F, 4.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition right_fin = partdefinition.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offsetAndRotation(-18.0F, 21.0F, -23.0F, 0.0F, -0.9599F, -0.3927F));

		PartDefinition right_fin_r1 = right_fin.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(0, 173).addBox(-3.0F, 0.0F, 0.0F, 4.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition back_fin = partdefinition.addOrReplaceChild("back_fin", CubeListBuilder.create().texOffs(72, 209).addBox(-2.5F, -2.1809F, 2.0261F, 5.0F, 15.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, 1.2217F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_fin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_fin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		back_fin.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}