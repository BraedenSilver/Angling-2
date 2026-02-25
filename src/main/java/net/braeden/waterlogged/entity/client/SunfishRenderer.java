package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.SunfishEntity;
import net.braeden.waterlogged.entity.client.state.SunfishRenderState;
import net.braeden.waterlogged.entity.util.SunfishVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SunfishRenderer extends MobRenderer<SunfishEntity, SunfishRenderState, SunfishModel> {

    private static final Map<SunfishVariant, Identifier> TEXTURES;

    static {
        TEXTURES = new EnumMap<>(SunfishVariant.class);
        for (SunfishVariant v : SunfishVariant.values()) {
            TEXTURES.put(v, Identifier.fromNamespaceAndPath("angling",
                    "textures/entity/sunfish/" + v.getTextureName() + ".png"));
        }
    }

    public SunfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SunfishModel(context.bakeLayer(WaterloggedEntityModelLayers.SUNFISH)), 0.3F);
    }

    @Override
    public SunfishRenderState createRenderState() {
        return new SunfishRenderState();
    }

    @Override
    public void extractRenderState(SunfishEntity entity, SunfishRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.floppingAnimationState.copyFrom(entity.floppingAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
        state.variant = entity.getVariant();
    }

    @Override
    public Identifier getTextureLocation(SunfishRenderState state) {
        return TEXTURES.getOrDefault(state.variant, TEXTURES.get(SunfishVariant.BLUEGILL));
    }
}
