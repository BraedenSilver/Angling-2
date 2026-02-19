package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.CatfishBlockbench;
import net.braeden.angling2.entity.client.blockbench.CatfishAnimations;
import net.braeden.angling2.entity.client.state.CatfishRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class CatfishModel extends EntityModel<CatfishRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public CatfishModel(ModelPart root) {
        super(root);
        this.idleAnimation = CatfishAnimations.IDLE.bake(root);
        this.flopAnimation = CatfishAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return CatfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(CatfishRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
