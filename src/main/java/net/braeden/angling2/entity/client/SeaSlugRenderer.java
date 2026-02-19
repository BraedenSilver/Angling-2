package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.SeaSlugEntity;
import net.braeden.angling2.entity.client.state.SeaSlugRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SeaSlugRenderer extends MobRenderer<SeaSlugEntity, SeaSlugRenderState, SeaSlugModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/sea_slug/sea_slug.png");

    public SeaSlugRenderer(EntityRendererProvider.Context context) {
        super(context, new SeaSlugModel(context.bakeLayer(AnglingEntityModelLayers.SEA_SLUG)), 0.3F);
        this.addLayer(new SeaSlugPatternLayer(this));
    }

    @Override
    public SeaSlugRenderState createRenderState() {
        return new SeaSlugRenderState();
    }

    @Override
    public void extractRenderState(SeaSlugEntity entity, SeaSlugRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.ambientAnimationState.copyFrom(entity.ambientAnimationState);
        state.movingAnimationState.copyFrom(entity.movingAnimationState);
        state.argbColor = entity.getColor().getArgb();
        state.pattern = entity.getPattern();
    }

    @Override
    public Identifier getTextureLocation(SeaSlugRenderState state) {
        return TEXTURE;
    }

    @Override
    protected int getModelTint(SeaSlugRenderState state) {
        return state.argbColor;
    }
}
