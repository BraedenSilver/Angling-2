package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.CrabBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.CrabAnimations;
import net.braeden.waterlogged.entity.client.state.CrabRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class CrabModel extends EntityModel<CrabRenderState> {
    private final KeyframeAnimation movingAnimation;
    private final KeyframeAnimation rotatedAnimation;
    private final KeyframeAnimation forwardsAnimation;
    private final KeyframeAnimation dancingAnimation;

    public CrabModel(ModelPart root) {
        super(root);
        this.movingAnimation = CrabAnimations.MOVING.bake(root);
        this.rotatedAnimation = CrabAnimations.ROTATED.bake(root);
        this.forwardsAnimation = CrabAnimations.FORWARDS.bake(root);
        this.dancingAnimation = CrabAnimations.DANCE.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return CrabBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(CrabRenderState state) {
        super.setupAnim(state);
        this.dancingAnimation.apply(state.dancingAnimationState, state.ageInTicks);
        this.movingAnimation.apply(state.movingAnimationState, state.ageInTicks);
        this.rotatedAnimation.apply(state.rotatedAnimationState, state.ageInTicks);
        this.forwardsAnimation.apply(state.forwardsAnimationState, state.ageInTicks);
    }
}
