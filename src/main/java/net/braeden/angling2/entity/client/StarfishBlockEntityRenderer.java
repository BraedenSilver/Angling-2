package net.braeden.angling2.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.braeden.angling2.block.StarfishBlock;
import net.braeden.angling2.block.entity.StarfishBlockEntity;
import net.braeden.angling2.entity.util.StarfishColor;
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
        this.model = new StarfishModel(ctx.bakeLayer(AnglingEntityModelLayers.STARFISH));
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
        state.color = entity.getColor();
    }

    @Override
    public void submit(StarfishRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        float yRot = switch (state.facing) {
            case SOUTH -> 0.0F;
            case WEST -> 90.0F;
            case NORTH -> 180.0F;
            case EAST -> 270.0F;
            default -> 0.0F;
        };
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.scale(0.7F, -0.7F, 0.7F);
        model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutoutNoCull(state.dead ? DEAD_TEXTURE : TEXTURE);
        int tint = state.dead ? -1 : state.color.getArgb();
        collector.submitModel(model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, tint, null, 0, state.breakProgress);
        poseStack.popPose();
    }

    public static class StarfishRenderState extends BlockEntityRenderState {
        public boolean dead = false;
        public Direction facing = Direction.NORTH;
        public StarfishColor color = StarfishColor.RED;
    }
}
