package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.MahiMahiBlockbench;
import net.braeden.angling2.entity.client.blockbench.MahiMahiAnimations;
import net.braeden.angling2.entity.client.state.MahiMahiRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class MahiMahiModel extends EntityModel<MahiMahiRenderState> {
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flopAnimation;

    public MahiMahiModel(ModelPart root) {
        super(root);
        this.idleAnimation = MahiMahiAnimations.IDLE.bake(root);
        this.flopAnimation = MahiMahiAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return MahiMahiBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(MahiMahiRenderState state) {
        super.setupAnim(state);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
