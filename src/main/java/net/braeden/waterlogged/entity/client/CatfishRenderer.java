package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.CatfishEntity;
import net.braeden.waterlogged.entity.client.state.CatfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class CatfishRenderer extends MobRenderer<CatfishEntity, CatfishRenderState, CatfishModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/catfish/catfish.png");

    public CatfishRenderer(EntityRendererProvider.Context context) {
        super(context, new CatfishModel(context.bakeLayer(WaterloggedEntityModelLayers.CATFISH)), 0.4F);
    }

    @Override
    public CatfishRenderState createRenderState() {
        return new CatfishRenderState();
    }

    @Override
    public void extractRenderState(CatfishEntity entity, CatfishRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(CatfishRenderState state) {
        return TEXTURE;
    }
}
