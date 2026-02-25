package net.braeden.waterlogged.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.braeden.waterlogged.entity.client.state.SeaSlugRenderState;
import net.braeden.waterlogged.entity.util.SeaSlugPattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SeaSlugPatternLayer extends RenderLayer<SeaSlugRenderState, SeaSlugModel> {

    private static final Map<SeaSlugPattern, Identifier> TEXTURES;

    static {
        TEXTURES = new EnumMap<>(SeaSlugPattern.class);
        for (SeaSlugPattern p : SeaSlugPattern.values()) {
            if (p.getTextureName() != null) {
                TEXTURES.put(p, Identifier.fromNamespaceAndPath("angling",
                        "textures/entity/sea_slug/" + p.getTextureName() + ".png"));
            }
        }
    }

    public SeaSlugPatternLayer(RenderLayerParent<SeaSlugRenderState, SeaSlugModel> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       SeaSlugRenderState state, float yRot, float xRot) {
        if (state.isInvisible || state.pattern == SeaSlugPattern.PLAIN) return;
        Identifier texture = TEXTURES.get(state.pattern);
        if (texture == null) return;
        coloredCutoutModelCopyLayerRender(this.getParentModel(), texture, poseStack, collector, packedLight, state, 0xFFFFFFFF, 0);
    }
}
