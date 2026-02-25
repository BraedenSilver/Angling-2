package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.FryEntity;
import net.braeden.waterlogged.entity.client.state.FryRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.fish.TropicalFish;

@Environment(EnvType.CLIENT)
public class FryRenderer extends MobRenderer<FryEntity, FryRenderState, FryModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/fry/fry.png");
    private static final Identifier INNARDS_TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/fry/fry_innards.png");

    public FryRenderer(EntityRendererProvider.Context context) {
        super(context, new FryModel(context.bakeLayer(WaterloggedEntityModelLayers.FRY)), 0.2F);
        this.addLayer(new WaterloggedEyesLayer<>(this, INNARDS_TEXTURE));
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
        // Prefer direct ARGB tint (e.g. from crab parents); fall back to tropical-fish derivation
        int directArgb = entity.getArgbColorForRender();
        if (directArgb != -1) {
            state.argbColor = directArgb;
        } else {
            int variant = entity.getTropicalVariantForRender();
            if (variant >= 0) {
                int textColor = TropicalFish.getBaseColor(variant).getTextColor();
                state.argbColor = 0xFF000000 | textColor;
            } else {
                state.argbColor = 0xFFFFFFFF;
            }
        }
    }

    @Override
    protected int getModelTint(FryRenderState state) {
        return state.argbColor;
    }

    @Override
    public Identifier getTextureLocation(FryRenderState state) {
        return TEXTURE;
    }
}
