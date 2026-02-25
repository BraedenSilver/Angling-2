package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.FryBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.FryAnimations;
import net.braeden.waterlogged.entity.client.state.FryRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class FryModel extends EntityModel<FryRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public FryModel(ModelPart root) {
        super(root);
        this.idleAnimation = FryAnimations.IDLE.bake(root);
        this.flopAnimation = FryAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return FryBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(FryRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
