package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.SeahorseEntity;
import net.braeden.waterlogged.entity.client.state.SeahorseRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SeahorseRenderer extends MobRenderer<SeahorseEntity, SeahorseRenderState, SeahorseModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/seahorse/seahorse.png");

    public SeahorseRenderer(EntityRendererProvider.Context context) {
        super(context, new SeahorseModel(context.bakeLayer(WaterloggedEntityModelLayers.SEAHORSE)), 0.2F);
    }

    @Override
    public SeahorseRenderState createRenderState() {
        return new SeahorseRenderState();
    }

    @Override
    public void extractRenderState(SeahorseEntity entity, SeahorseRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(SeahorseRenderState state) {
        return TEXTURE;
    }
}
