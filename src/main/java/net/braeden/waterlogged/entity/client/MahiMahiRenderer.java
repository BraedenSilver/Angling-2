package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.MahiMahiEntity;
import net.braeden.waterlogged.entity.client.state.MahiMahiRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class MahiMahiRenderer extends MobRenderer<MahiMahiEntity, MahiMahiRenderState, MahiMahiModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("angling", "textures/entity/mahi_mahi/mahi_mahi.png");

    public MahiMahiRenderer(EntityRendererProvider.Context context) {
        super(context, new MahiMahiModel(context.bakeLayer(WaterloggedEntityModelLayers.MAHI_MAHI)), 0.4F);
    }

    @Override
    public MahiMahiRenderState createRenderState() {
        return new MahiMahiRenderState();
    }

    @Override
    public void extractRenderState(MahiMahiEntity entity, MahiMahiRenderState state, float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.flopAnimationState.copyFrom(entity.flopAnimationState);
    }

    @Override
    public Identifier getTextureLocation(MahiMahiRenderState state) {
        return TEXTURE;
    }
}
