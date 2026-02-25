package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.AnglerfishEntity;
import net.braeden.waterlogged.entity.client.state.AnglerfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class AnglerfishRenderer extends MobRenderer<AnglerfishEntity, AnglerfishRenderState, AnglerfishModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/anglerfish/anglerfish.png");
    private static final Identifier OVERLAY_TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/anglerfish/anglerfish_overlay.png");

    public AnglerfishRenderer(EntityRendererProvider.Context context) {
        super(context, new AnglerfishModel(context.bakeLayer(WaterloggedEntityModelLayers.ANGLERFISH)), 0.4F);
        this.addLayer(new WaterloggedEyesLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public AnglerfishRenderState createRenderState() {
        return new AnglerfishRenderState();
    }

    @Override
    public void extractRenderState(AnglerfishEntity entity, AnglerfishRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(AnglerfishRenderState state) {
        return TEXTURE;
    }
}
