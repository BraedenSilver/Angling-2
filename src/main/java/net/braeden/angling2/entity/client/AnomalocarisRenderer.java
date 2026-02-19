package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.AnomalocarisEntity;
import net.braeden.angling2.entity.client.state.AnomalocarisRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class AnomalocarisRenderer extends MobRenderer<AnomalocarisEntity, AnomalocarisRenderState, AnomalocarisModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/anomalocaris/anomalocaris.png");

    public AnomalocarisRenderer(EntityRendererProvider.Context context) {
        super(context, new AnomalocarisModel(context.bakeLayer(AnglingEntityModelLayers.ANOMALOCARIS)), 0.5F);
    }

    @Override
    public AnomalocarisRenderState createRenderState() {
        return new AnomalocarisRenderState();
    }

    @Override
    public void extractRenderState(AnomalocarisEntity entity, AnomalocarisRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    public Identifier getTextureLocation(AnomalocarisRenderState state) {
        return TEXTURE;
    }
}
