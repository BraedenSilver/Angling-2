package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.braeden.waterlogged.block.StarfishBlock;
import net.braeden.waterlogged.block.entity.StarfishBlockEntity;
import net.braeden.waterlogged.entity.util.StarfishColor;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class StarfishBlockEntityRenderer implements BlockEntityRenderer<StarfishBlockEntity, StarfishBlockEntityRenderer.StarfishRenderState> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/starfish/starfish.png");
    private static final Identifier DEAD_TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/starfish/dead_starfish.png");

    private final StarfishModel model;

    public StarfishBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new StarfishModel(ctx.bakeLayer(WaterloggedEntityModelLayers.STARFISH));
    }

    @Override
    public StarfishRenderState createRenderState() {
        return new StarfishRenderState();
    }

    @Override
    public void extractRenderState(StarfishBlockEntity entity, StarfishRenderState state, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay overlay) {
        BlockEntityRenderState.extractBase(entity, state, overlay);
        var blockState = entity.getBlockState();
        state.dead = blockState.getBlock() instanceof StarfishBlock sf && sf.isDead();
        state.facing = blockState.getValue(StarfishBlock.FACING);
        state.rotation = blockState.getValue(StarfishBlock.ROTATION);
        state.color = entity.getColor();
        state.gameTicks = entity.getLevel() != null ? entity.getLevel().getGameTime() : 0;
    }

    @Override
    public void submit(StarfishRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        // 1) Rotate the model to the correct face
        switch (state.facing) {
            case UP -> {}
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
            case SOUTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
            case WEST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        }

        // 2) Spin the starfish on the surface for visual variety
        poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation * 90.0F));

        // 3) Offset in local "up" (away from surface) and apply entity scale+flip
        poseStack.translate(0, 0.55, 0);
        poseStack.scale(0.7F, -0.7F, 0.7F);

        model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutoutNoCull(state.dead ? DEAD_TEXTURE : TEXTURE);
        int tint = state.dead ? -1 : state.color.getArgbForTicks(state.gameTicks);
        collector.submitModel(model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, tint, null, 0, state.breakProgress);
        poseStack.popPose();
    }

    public static class StarfishRenderState extends BlockEntityRenderState {
        public boolean dead = false;
        public Direction facing = Direction.UP;
        public int rotation = 0;
        public StarfishColor color = StarfishColor.RED;
        public long gameTicks = 0;
    }
}
