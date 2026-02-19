package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.BubbleEyeBlockbench;
import net.braeden.angling2.entity.client.blockbench.BubbleEyeAnimations;
import net.braeden.angling2.entity.client.state.BubbleEyeRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class BubbleEyeModel extends EntityModel<BubbleEyeRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public BubbleEyeModel(ModelPart root) {
        super(root);
        this.idleAnimation = BubbleEyeAnimations.IDLE.bake(root);
        this.flopAnimation = BubbleEyeAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return BubbleEyeBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(BubbleEyeRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
