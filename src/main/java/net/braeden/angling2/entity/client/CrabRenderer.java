package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.CrabEntity;
import net.braeden.angling2.entity.client.state.CrabRenderState;
import net.braeden.angling2.entity.util.CrabVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class CrabRenderer extends MobRenderer<CrabEntity, CrabRenderState, CrabModel> {

    private static final Map<CrabVariant, Identifier> TEXTURES = Map.of(
            CrabVariant.DUNGENESS, Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/dungeness.png"),
            CrabVariant.GHOST,     Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/ghost.png"),
            CrabVariant.BLUE_CLAW, Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/blue_claw.png")
    );

    public CrabRenderer(EntityRendererProvider.Context context) {
        super(context, new CrabModel(context.bakeLayer(AnglingEntityModelLayers.CRAB)), 0.3F);
    }

    @Override
    public CrabRenderState createRenderState() {
        return new CrabRenderState();
    }

    @Override
    public void extractRenderState(CrabEntity entity, CrabRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.movingAnimationState.copyFrom(entity.movingAnimationState);
        state.rotatedAnimationState.copyFrom(entity.rotatedAnimationState);
        state.forwardsAnimationState.copyFrom(entity.forwardsAnimationState);
        state.variant = entity.getVariant();
    }

    @Override
    public Identifier getTextureLocation(CrabRenderState state) {
        return TEXTURES.getOrDefault(state.variant, TEXTURES.get(CrabVariant.DUNGENESS));
    }
}
