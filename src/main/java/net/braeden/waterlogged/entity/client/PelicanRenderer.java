package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.PelicanEntity;
import net.braeden.waterlogged.entity.client.state.PelicanRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class PelicanRenderer extends MobRenderer<PelicanEntity, PelicanRenderState, PelicanModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/pelican/pelican.png");

    public PelicanRenderer(EntityRendererProvider.Context context) {
        super(context, new PelicanModel(context.bakeLayer(WaterloggedEntityModelLayers.PELICAN)), 0.5F);
    }

    @Override
    public PelicanRenderState createRenderState() {
        return new PelicanRenderState();
    }

    @Override
    public void extractRenderState(PelicanEntity entity, PelicanRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkingAnimationState.copyFrom(entity.walkingAnimationState);
        state.swimmingAnimationState.copyFrom(entity.swimmingAnimationState);
        state.flyingAnimationState.copyFrom(entity.flyingAnimationState);
        state.divingAnimationState.copyFrom(entity.divingAnimationState);
        state.flappingAnimationState.copyFrom(entity.flappingAnimationState);
        state.beakOpenedAnimationState.copyFrom(entity.beakOpenedAnimationState);
        state.hasHeldEntity = entity.hasHeldEntity();
    }

    @Override
    public Identifier getTextureLocation(PelicanRenderState state) {
        return TEXTURE;
    }
}
