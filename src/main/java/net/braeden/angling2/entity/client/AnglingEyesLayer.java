package net.braeden.angling2.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * Generic glow overlay layer — equivalent to EnderEyesLayer/SpiderEyesLayer.
 * Renders the provided texture at full brightness regardless of block light.
 */
@Environment(EnvType.CLIENT)
public class AnglingEyesLayer<S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends EyesLayer<S, M> {

    private final RenderType renderType;

    public AnglingEyesLayer(RenderLayerParent<S, M> renderer, Identifier texture) {
        super(renderer);
        this.renderType = RenderTypes.eyes(texture);
    }

    @Override
    public RenderType renderType() {
        return renderType;
    }
}
