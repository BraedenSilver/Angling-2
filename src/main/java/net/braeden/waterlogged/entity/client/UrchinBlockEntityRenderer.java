package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.block.entity.UrchinBlockEntity;
import net.braeden.waterlogged.entity.client.state.UrchinBlockEntityRenderState;
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

public class UrchinBlockEntityRenderer implements BlockEntityRenderer<UrchinBlockEntity, UrchinBlockEntityRenderState> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/urchin/urchin.png");

    private final UrchinModel model;

    public UrchinBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new UrchinModel(ctx.bakeLayer(WaterloggedEntityModelLayers.URCHIN));
    }

    @Override
    public UrchinBlockEntityRenderState createRenderState() {
        return new UrchinBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(UrchinBlockEntity entity, UrchinBlockEntityRenderState state, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderState.extractBase(entity, state, overlay);
        state.ageInTicks = entity.animTickCount + partialTick;
    }

    @Override
    public void submit(UrchinBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.2, 0.5);
        poseStack.scale(0.8F, -0.8F, 0.8F);
        model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutoutNoCull(TEXTURE);
        collector.submitModel(model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, 0, state.breakProgress);
        poseStack.popPose();
    }
}
