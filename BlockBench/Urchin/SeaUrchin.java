// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.mod;
   
public class SeaUrchin extends EntityModel<Entity> {
	private final ModelPart bone;
	private final ModelPart spinessmall;
	private final ModelPart spineslarge;
	private final ModelPart bb_main;
	public SeaUrchin(ModelPart root) {
		this.bone = root.getChild("bone");
		this.spinessmall = root.getChild("spinessmall");
		this.spineslarge = root.getChild("spineslarge");
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.of(0.0F, 25.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData spinessmall = bone.addChild("spinessmall", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		ModelPartData cube_r1 = spinessmall.addChild("cube_r1", ModelPartBuilder.create().uv(5, 10).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, 1.0F, -1.5708F, 0.7854F, 3.1416F));

		ModelPartData cube_r2 = spinessmall.addChild("cube_r2", ModelPartBuilder.create().uv(5, 10).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, -3.0F, -1.5708F, -0.7854F, -3.1416F));

		ModelPartData cube_r3 = spinessmall.addChild("cube_r3", ModelPartBuilder.create().uv(5, 10).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, -3.0F, 1.5708F, -0.7854F, 0.0F));

		ModelPartData cube_r4 = spinessmall.addChild("cube_r4", ModelPartBuilder.create().uv(5, 10).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, 1.0F, 1.5708F, 0.7854F, 0.0F));

		ModelPartData spineslarge = bone.addChild("spineslarge", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, -12.0F, -1.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		ModelPartData cube_r5 = spineslarge.addChild("cube_r5", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r6 = spineslarge.addChild("cube_r6", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r7 = spineslarge.addChild("cube_r7", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}