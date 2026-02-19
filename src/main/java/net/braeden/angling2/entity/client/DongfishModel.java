package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.DongfishBlockbench;
import net.braeden.angling2.entity.client.blockbench.DongfishAnimations;
import net.braeden.angling2.entity.client.state.DongfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class DongfishModel extends EntityModel<DongfishRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public DongfishModel(ModelPart root) {
        super(root);
        this.idleAnimation = DongfishAnimations.IDLE.bake(root);
        this.flopAnimation = DongfishAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return DongfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(DongfishRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
