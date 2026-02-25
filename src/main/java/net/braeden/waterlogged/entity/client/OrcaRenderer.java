package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.entity.OrcaEntity;
import net.braeden.waterlogged.entity.client.state.OrcaRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class OrcaRenderer extends MobRenderer<OrcaEntity, OrcaRenderState, OrcaModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("angling", "textures/entity/orca/orca.png");

    public OrcaRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcaModel(context.bakeLayer(WaterloggedEntityModelLayers.ORCA)), 1.2F);
    }

    @Override
    public OrcaRenderState createRenderState() {
        return new OrcaRenderState();
    }

    @Override
    public void extractRenderState(OrcaEntity entity, OrcaRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.swimAnimationState.copyFrom(entity.swimAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.mouthAnimationState.copyFrom(entity.mouthAnimationState);
        state.isBaby = entity.isBaby();
        state.verticalSpeed = (float) entity.getDeltaMovement().y;
        state.isInWater = entity.isInWater();
    }

    @Override
    protected void scale(OrcaRenderState state, PoseStack poseStack) {
        poseStack.translate(0, -0.5f, 0);
        if (state.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public Identifier getTextureLocation(OrcaRenderState state) {
        return TEXTURE;
    }
}
