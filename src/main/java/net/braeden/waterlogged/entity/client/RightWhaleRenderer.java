package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.entity.RightWhaleEntity;
import net.braeden.waterlogged.entity.client.state.RightWhaleRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class RightWhaleRenderer extends MobRenderer<RightWhaleEntity, RightWhaleRenderState, RightWhaleModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("angling", "textures/entity/right_whale/right_whale.png");

    public RightWhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new RightWhaleModel(context.bakeLayer(WaterloggedEntityModelLayers.RIGHT_WHALE)), 1.8F);
    }

    @Override
    public RightWhaleRenderState createRenderState() {
        return new RightWhaleRenderState();
    }

    @Override
    public void extractRenderState(RightWhaleEntity entity, RightWhaleRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.swimAnimationState.copyFrom(entity.swimAnimationState);
        state.surfaceAnimationState.copyFrom(entity.surfaceAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
        state.barnacleLevel = entity.getBarnacleLevel();
        state.isBaby = entity.isBaby();
        state.verticalSpeed = (float) entity.getDeltaMovement().y;
        state.isInWater = entity.isInWater();
    }

    @Override
    protected void scale(RightWhaleRenderState state, PoseStack poseStack) {
        // 20% scale-up relative to the Orca baseline
        poseStack.scale(1.2f, 1.2f, 1.2f);
        poseStack.translate(0, -0.5f, 0);
        if (state.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public Identifier getTextureLocation(RightWhaleRenderState state) {
        return TEXTURE;
    }
}
