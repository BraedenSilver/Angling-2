// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class RightWhale extends EntityModel<Entity> {
	private final ModelPart body;
	private final ModelPart barnacle_cluster_2;
	private final ModelPart head;
	private final ModelPart upperjaw;
	private final ModelPart barnacle_cluster_1;
	private final ModelPart lowerjaw;
	private final ModelPart tail;
	private final ModelPart middletail;
	private final ModelPart endtail;
	private final ModelPart right_fin;
	private final ModelPart left_fin;
	private final ModelPart saddle;
	public RightWhale(ModelPart root) {
		this.body = root.getChild("body");
		this.barnacle_cluster_2 = root.getChild("barnacle_cluster_2");
		this.head = root.getChild("head");
		this.upperjaw = root.getChild("upperjaw");
		this.barnacle_cluster_1 = root.getChild("barnacle_cluster_1");
		this.lowerjaw = root.getChild("lowerjaw");
		this.tail = root.getChild("tail");
		this.middletail = root.getChild("middletail");
		this.endtail = root.getChild("endtail");
		this.right_fin = root.getChild("right_fin");
		this.left_fin = root.getChild("left_fin");
		this.saddle = root.getChild("saddle");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-22.0F, -43.0F, -30.0F, 44.0F, 43.0F, 60.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData barnacle_cluster_2 = body.addChild("barnacle_cluster_2", ModelPartBuilder.create().uv(136, 222).cuboid(5.0F, 0.0F, 6.0F, 3.0F, 5.0F, 5.0F, new Dilation(0.0F))
		.uv(152, 235).cuboid(6.0F, 5.0F, -3.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(296, 94).cuboid(6.0F, 2.0F, -10.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(288, 88).cuboid(6.0F, -3.0F, -2.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(136, 232).cuboid(52.0F, 0.0F, 6.0F, 3.0F, 5.0F, 5.0F, new Dilation(0.0F))
		.uv(74, 298).cuboid(52.0F, 5.0F, -3.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(64, 298).cuboid(52.0F, 2.0F, -10.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(54, 298).cuboid(52.0F, -3.0F, -2.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(92, 179).cuboid(10.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(104, 281).cuboid(23.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(284, 94).cuboid(34.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(284, 94).cuboid(28.0F, -7.0F, -15.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(124, 183).cuboid(16.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(124, 183).cuboid(22.0F, -6.0F, -13.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(76, 179).cuboid(16.0F, -7.0F, 3.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(102, 264).cuboid(14.0F, -7.0F, -11.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(272, 94).cuboid(14.0F, -7.0F, 10.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(92, 281).cuboid(47.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(132, 183).cuboid(42.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(272, 88).cuboid(40.0F, -7.0F, 3.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(102, 270).cuboid(42.0F, -7.0F, -11.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(102, 276).cuboid(43.0F, -7.0F, 10.0F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-30.0F, -38.0F, -15.0F));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, -30.0F));

		ModelPartData upperjaw = head.addChild("upperjaw", ModelPartBuilder.create().uv(236, 245).cuboid(-20.0F, -19.0F, -14.0F, 40.0F, 33.0F, 14.0F, new Dilation(0.0F))
		.uv(208, 0).cuboid(-20.0F, -19.0F, -45.0F, 40.0F, 19.0F, 31.0F, new Dilation(0.0F))
		.uv(306, 88).cuboid(-16.0F, 0.0F, -41.0F, 32.0F, 17.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 179).cuboid(-18.0F, 0.0F, -43.0F, 36.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 298).cuboid(14.0F, 0.0F, -39.0F, 2.0F, 17.0F, 25.0F, new Dilation(0.0F))
		.uv(298, 186).cuboid(-16.0F, 0.0F, -39.0F, 2.0F, 17.0F, 25.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

		ModelPartData barnacle_cluster_1 = upperjaw.addChild("barnacle_cluster_1", ModelPartBuilder.create().uv(118, 234).cuboid(-10.0F, -2.0F, -3.0F, 5.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(118, 234).cuboid(-14.0F, -10.0F, -3.0F, 5.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(118, 234).cuboid(8.0F, -9.0F, -3.0F, 5.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(298, 228).cuboid(-3.0F, 3.0F, -2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(298, 233).cuboid(4.0F, -1.0F, -2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(298, 233).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(272, 88).cuboid(-5.0F, -15.0F, 32.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(102, 270).cuboid(3.0F, -15.0F, 40.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(102, 270).cuboid(3.0F, -15.0F, 26.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, -45.0F));

		ModelPartData lowerjaw = head.addChild("lowerjaw", ModelPartBuilder.create().uv(148, 103).cuboid(-18.0F, 0.0F, -43.0F, 36.0F, 9.0F, 43.0F, new Dilation(0.0F))
		.uv(236, 292).cuboid(16.0F, -14.0F, -41.0F, 2.0F, 14.0F, 27.0F, new Dilation(0.0F))
		.uv(294, 292).cuboid(-18.0F, -14.0F, -41.0F, 2.0F, 14.0F, 27.0F, new Dilation(0.0F))
		.uv(272, 76).cuboid(-18.0F, -10.0F, -43.0F, 36.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(0, 103).cuboid(-20.0F, -8.0F, 0.0F, 40.0F, 34.0F, 34.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -10.0F, 28.0F));

		ModelPartData middletail = tail.addChild("middletail", ModelPartBuilder.create().uv(162, 186).cuboid(-17.0F, -8.0F, 0.0F, 34.0F, 25.0F, 34.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 32.0F));

		ModelPartData endtail = middletail.addChild("endtail", ModelPartBuilder.create().uv(0, 264).cuboid(-11.0F, -4.0F, 0.0F, 22.0F, 10.0F, 24.0F, new Dilation(0.0F))
		.uv(148, 155).cuboid(-29.0F, -1.0F, 12.0F, 58.0F, 4.0F, 27.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 32.0F));

		ModelPartData right_fin = modelPartData.addChild("right_fin", ModelPartBuilder.create().uv(0, 222).cuboid(-22.0F, 0.0F, 0.0F, 22.0F, 5.0F, 37.0F, new Dilation(0.0F)), ModelTransform.of(-22.0F, 19.0F, -20.0F, -0.2317F, -0.3405F, -1.0122F));

		ModelPartData left_fin = modelPartData.addChild("left_fin", ModelPartBuilder.create().uv(0, 222).mirrored().cuboid(0.0F, 0.0F, 0.0F, 22.0F, 5.0F, 37.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(22.0F, 19.0F, -20.0F, -0.2317F, 0.3405F, 1.0122F));

		ModelPartData saddle = modelPartData.addChild("saddle", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -19.0F, 0.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_fin.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_fin.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		saddle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}