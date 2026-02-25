package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.DongfishEntity;
import net.braeden.waterlogged.entity.client.state.DongfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class DongfishRenderer extends MobRenderer<DongfishEntity, DongfishRenderState, DongfishModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/dongfish/dongfish.png");

    public DongfishRenderer(EntityRendererProvider.Context context) {
        super(context, new DongfishModel(context.bakeLayer(WaterloggedEntityModelLayers.DONGFISH)), 0.3F);
    }

    @Override
    public DongfishRenderState createRenderState() {
        return new DongfishRenderState();
    }

    @Override
    public void extractRenderState(DongfishEntity entity, DongfishRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
        state.sheared = entity.isSheared();
    }

    @Override
    public Identifier getTextureLocation(DongfishRenderState state) {
        return TEXTURE;
    }
}
