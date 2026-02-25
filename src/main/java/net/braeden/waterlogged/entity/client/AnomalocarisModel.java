package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.AnomalocarisBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.AnomalocarisAnimations;
import net.braeden.waterlogged.entity.client.state.AnomalocarisRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class AnomalocarisModel extends EntityModel<AnomalocarisRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public AnomalocarisModel(ModelPart root) {
        super(root);
        this.idleAnimation = AnomalocarisAnimations.IDLE.bake(root);
        this.flopAnimation = AnomalocarisAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return AnomalocarisBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(AnomalocarisRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
