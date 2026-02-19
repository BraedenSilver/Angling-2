package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.FryEntity;
import net.braeden.angling2.entity.client.state.FryRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class FryRenderer extends MobRenderer<FryEntity, FryRenderState, FryModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/fry/fry.png");
    private static final Identifier INNARDS_TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/fry/fry_innards.png");

    public FryRenderer(EntityRendererProvider.Context context) {
        super(context, new FryModel(context.bakeLayer(AnglingEntityModelLayers.FRY)), 0.2F);
        this.addLayer(new AnglingEyesLayer<>(this, INNARDS_TEXTURE));
    }

    @Override
    public FryRenderState createRenderState() {
        return new FryRenderState();
    }

    @Override
    public void extractRenderState(FryEntity entity, FryRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(FryRenderState state) {
        return TEXTURE;
    }
}
