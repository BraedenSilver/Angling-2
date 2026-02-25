package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.PelicanBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.PelicanAnimations;
import net.braeden.waterlogged.entity.client.state.PelicanRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class PelicanModel extends EntityModel<PelicanRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation swimmingAnimation;
    private final KeyframeAnimation flyingAnimation;
    private final KeyframeAnimation divingAnimation;
    private final KeyframeAnimation flappingAnimation;
    private final KeyframeAnimation beakOpenedAnimation;

    public PelicanModel(ModelPart root) {
        super(root);
        this.idleAnimation = PelicanAnimations.IDLE.bake(root);
        this.walkingAnimation = PelicanAnimations.WALKING.bake(root);
        this.swimmingAnimation = PelicanAnimations.SWIMMING.bake(root);
        this.flyingAnimation = PelicanAnimations.FLYING.bake(root);
        this.divingAnimation = PelicanAnimations.DIVING.bake(root);
        this.flappingAnimation = PelicanAnimations.FLAPPING.bake(root);
        this.beakOpenedAnimation = PelicanAnimations.BEAK_OPENED.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return PelicanBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(PelicanRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.walkingAnimation.apply(state.walkingAnimationState, state.ageInTicks);
        this.swimmingAnimation.apply(state.swimmingAnimationState, state.ageInTicks);
        this.flyingAnimation.apply(state.flyingAnimationState, state.ageInTicks);
        this.divingAnimation.apply(state.divingAnimationState, state.ageInTicks);
        this.flappingAnimation.apply(state.flappingAnimationState, state.ageInTicks);
        this.beakOpenedAnimation.apply(state.beakOpenedAnimationState, state.ageInTicks);
    }
}
