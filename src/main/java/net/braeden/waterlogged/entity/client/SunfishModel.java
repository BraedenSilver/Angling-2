package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.SunfishBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.SunfishAnimations;
import net.braeden.waterlogged.entity.client.state.SunfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class SunfishModel extends EntityModel<SunfishRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation floppingAnimation;
    private final KeyframeAnimation flopAnimation;

    public SunfishModel(ModelPart root) {
        super(root);
        this.idleAnimation = SunfishAnimations.IDLE.bake(root);
        this.floppingAnimation = SunfishAnimations.FLOPPING.bake(root);
        this.flopAnimation = SunfishAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return SunfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(SunfishRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.floppingAnimation.apply(state.floppingAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
