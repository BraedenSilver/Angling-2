package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.CrabEntity;
import net.braeden.angling2.entity.client.state.CrabRenderState;
import net.braeden.angling2.entity.util.CrabVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class CrabRenderer extends MobRenderer<CrabEntity, CrabRenderState, CrabModel> {

    private static final Map<CrabVariant, Identifier> TEXTURES = Map.of(
            CrabVariant.DUNGENESS, Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/dungeness.png"),
            CrabVariant.GHOST,     Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/ghost.png"),
            CrabVariant.BLUE_CLAW, Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/blue_claw.png"),
            CrabVariant.MOJANG,    Identifier.fromNamespaceAndPath("angling", "textures/entity/crab/mojang.png")
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
        state.isBaby = entity.isBaby();
    }

    @Override
    protected void scale(CrabRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public Identifier getTextureLocation(CrabRenderState state) {
        return TEXTURES.getOrDefault(state.variant, TEXTURES.get(CrabVariant.DUNGENESS));
    }
}
