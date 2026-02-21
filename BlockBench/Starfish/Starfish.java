// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class Starfish extends EntityModel<Entity> {
	private final ModelPart bb_main;
	public Starfish(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(2, 0).cuboid(-8.0F, -4.1F, -7.0F, 16.0F, 0.0F, 14.0F, new Dilation(0.0F))
		.uv(0, 20).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData bottom_r1 = bb_main.addChild("bottom_r1", ModelPartBuilder.create().uv(2, 0).cuboid(-8.0F, -0.1F, -7.0F, 16.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		ModelPartData rightarm_r1 = bb_main.addChild("rightarm_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

		ModelPartData leftarm_r1 = bb_main.addChild("leftarm_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.3963F, 0.0F));

		ModelPartData rightleg_r1 = bb_main.addChild("rightleg_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

		ModelPartData leftleg_r1 = bb_main.addChild("leftleg_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, -1.0F, 0.0F, -2.3562F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}