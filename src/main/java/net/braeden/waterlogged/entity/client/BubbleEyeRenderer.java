package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.BubbleEyeEntity;
import net.braeden.waterlogged.entity.client.state.BubbleEyeRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class BubbleEyeRenderer extends MobRenderer<BubbleEyeEntity, BubbleEyeRenderState, BubbleEyeModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/bubble_eye/bubble_eye.png");

    public BubbleEyeRenderer(EntityRendererProvider.Context context) {
        super(context, new BubbleEyeModel(context.bakeLayer(WaterloggedEntityModelLayers.BUBBLE_EYE)), 0.2F);
    }

    @Override
    public BubbleEyeRenderState createRenderState() {
        return new BubbleEyeRenderState();
    }

    @Override
    public void extractRenderState(BubbleEyeEntity entity, BubbleEyeRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(BubbleEyeRenderState state) {
        return TEXTURE;
    }
}
