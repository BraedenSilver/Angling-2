package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.block.entity.AnemoneBlockEntity;
import net.braeden.waterlogged.entity.client.state.AnemoneBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class AnemoneBlockEntityRenderer implements BlockEntityRenderer<AnemoneBlockEntity, AnemoneBlockEntityRenderState> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/anemone/anemone.png");

    private final AnemoneModel model;

    public AnemoneBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new AnemoneModel(ctx.bakeLayer(WaterloggedEntityModelLayers.ANEMONE));
    }

    @Override
    public AnemoneBlockEntityRenderState createRenderState() {
        return new AnemoneBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(AnemoneBlockEntity entity, AnemoneBlockEntityRenderState state, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderState.extractBase(entity, state, overlay);
        state.vibingAnimationState.copyFrom(entity.vibingAnimationState);
        state.ageInTicks = entity.animTickCount + partialTick;
    }

    @Override
    public void submit(AnemoneBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(1.0F, -1.0F, 1.0F);
        model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutoutNoCull(TEXTURE);
        collector.submitModel(model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, 0, state.breakProgress);
        poseStack.popPose();
    }
}
