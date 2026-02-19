package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.AnglerfishBlockbench;
import net.braeden.angling2.entity.client.blockbench.AnglerfishAnimations;
import net.braeden.angling2.entity.client.state.AnglerfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class AnglerfishModel extends EntityModel<AnglerfishRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public AnglerfishModel(ModelPart root) {
        super(root);
        this.idleAnimation = AnglerfishAnimations.IDLE.bake(root);
        this.flopAnimation = AnglerfishAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return AnglerfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(AnglerfishRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
