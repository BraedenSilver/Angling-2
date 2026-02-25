package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.SeahorseBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.SeahorseAnimations;
import net.braeden.waterlogged.entity.client.state.SeahorseRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class SeahorseModel extends EntityModel<SeahorseRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public SeahorseModel(ModelPart root) {
        super(root);
        this.idleAnimation = SeahorseAnimations.IDLE.bake(root);
        this.flopAnimation = SeahorseAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return SeahorseBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(SeahorseRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
