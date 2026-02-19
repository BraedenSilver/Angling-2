package net.braeden.angling2.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.angling2.block.entity.UrchinBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;

public class UrchinBlockEntityRenderer implements BlockEntityRenderer<UrchinBlockEntity, BlockEntityRenderState> {

    public UrchinBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(UrchinBlockEntity entity, BlockEntityRenderState state, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderState.extractBase(entity, state, overlay);
    }

    @Override
    public void submit(BlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
    }
}
