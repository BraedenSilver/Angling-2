package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.entity.client.state.SeaSlugRenderState;
import net.braeden.waterlogged.entity.util.SeaSlugBioluminescence;
import net.braeden.waterlogged.entity.util.SeaSlugPattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

import java.util.EnumMap;
import java.util.Map;

/**
 * Renders bioluminescent sea slugs at full brightness so they glow in the dark.
 * Body bioluminescence re-renders the body texture at max light with the entity's color tint.
 * Pattern bioluminescence re-renders the pattern overlay at max light.
 */
@Environment(EnvType.CLIENT)
public class SeaSlugBiolumLayer extends RenderLayer<SeaSlugRenderState, SeaSlugModel> {

    private static final Identifier BODY_TEXTURE =
            Identifier.fromNamespaceAndPath("angling", "textures/entity/sea_slug/sea_slug.png");

    private static final Map<SeaSlugPattern, Identifier> PATTERN_TEXTURES =
            new EnumMap<>(SeaSlugPattern.class);

    /** (15 << 20) | (15 << 4) — maximum sky + block light. */
    private static final int FULL_BRIGHT = 0xF000F0;

    static {
        for (SeaSlugPattern p : SeaSlugPattern.values()) {
            if (p.getTextureName() != null) {
                PATTERN_TEXTURES.put(p, Identifier.fromNamespaceAndPath("angling",
                        "textures/entity/sea_slug/" + p.getTextureName() + ".png"));
            }
        }
    }

    public SeaSlugBiolumLayer(RenderLayerParent<SeaSlugRenderState, SeaSlugModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       SeaSlugRenderState state, float yRot, float xRot) {
        if (state.isInvisible) return;
        SeaSlugBioluminescence biolum = state.bioluminescence;
        if (biolum == SeaSlugBioluminescence.NONE) return;

        SeaSlugModel model = this.getParentModel();

        if (biolum == SeaSlugBioluminescence.BODY || biolum == SeaSlugBioluminescence.BOTH) {
            // Re-render body texture at full brightness with the entity's color tint.
            coloredCutoutModelCopyLayerRender(model, BODY_TEXTURE, poseStack, collector,
                    FULL_BRIGHT, state, state.argbColor, 0);
        }

        if ((biolum == SeaSlugBioluminescence.PATTERN || biolum == SeaSlugBioluminescence.BOTH)
                && state.pattern != SeaSlugPattern.PLAIN) {
            Identifier patternTex = PATTERN_TEXTURES.get(state.pattern);
            if (patternTex != null) {
                coloredCutoutModelCopyLayerRender(model, patternTex, poseStack, collector,
                        FULL_BRIGHT, state, 0xFFFFFFFF, 0);
            }
        }
    }
}
