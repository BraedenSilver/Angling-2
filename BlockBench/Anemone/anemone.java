// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class anemone extends EntityModel<Entity> {
	private final ModelPart body;
	private final ModelPart tentacle;
	private final ModelPart tentercools;
	private final ModelPart tentercools2;
	private final ModelPart tentercools3;
	private final ModelPart tentercools4;
	public anemone(ModelPart root) {
		this.body = root.getChild("body");
		this.tentacle = root.getChild("tentacle");
		this.tentercools = root.getChild("tentercools");
		this.tentercools2 = root.getChild("tentercools2");
		this.tentercools3 = root.getChild("tentercools3");
		this.tentercools4 = root.getChild("tentercools4");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, 0.0F));

		ModelPartData tentacle = body.addChild("tentacle", ModelPartBuilder.create().uv(-12, 35).cuboid(-11.0F, 1.0F, -1.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -1.0F, -5.0F));

		ModelPartData tentercools = tentacle.addChild("tentercools", ModelPartBuilder.create().uv(1, 17).cuboid(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.829F));

		ModelPartData tentercools2 = tentacle.addChild("tentercools2", ModelPartBuilder.create().uv(1, 17).cuboid(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 1.0F, 5.0F, 0.0F, 0.0F, -0.829F));

		ModelPartData tentercools3 = tentacle.addChild("tentercools3", ModelPartBuilder.create().uv(1, 17).cuboid(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 1.0F, 2.0F, 1.5708F, -0.829F, -1.5708F));

		ModelPartData tentercools4 = tentacle.addChild("tentercools4", ModelPartBuilder.create().uv(1, 17).cuboid(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 1.0F, 8.0F, -1.5708F, 0.829F, -1.5708F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}