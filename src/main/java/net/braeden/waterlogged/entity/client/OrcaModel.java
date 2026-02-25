package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.OrcaBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.OrcaAnimations;
import net.braeden.waterlogged.entity.client.state.OrcaRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class OrcaModel extends EntityModel<OrcaRenderState> {

    private final ModelPart modelRoot;
    private final KeyframeAnimation swimAnimation;
    private final KeyframeAnimation flopAnimation;
    private final KeyframeAnimation attackAnimation;
    private final KeyframeAnimation mouthAnimation;

    // Smoothed pitch — lerped each frame to prevent abrupt transitions
    private float smoothPitch = 0.0F;

    public OrcaModel(ModelPart root) {
        super(root);
        this.modelRoot = root;
        this.swimAnimation   = OrcaAnimations.SWIM.bake(root);
        this.flopAnimation   = OrcaAnimations.FLOP.bake(root);
        this.attackAnimation = OrcaAnimations.ATTACK.bake(root);
        this.mouthAnimation  = OrcaAnimations.MOUTH.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return OrcaBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(OrcaRenderState state) {
        super.setupAnim(state);
        this.swimAnimation.apply(state.swimAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
        this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks);
        this.mouthAnimation.apply(state.mouthAnimationState, state.ageInTicks);

        // Smoothly lerp pitch when actively climbing or diving
        float targetPitch = 0.0F;
        if (state.isInWater) {
            float vy = state.verticalSpeed;
            float deadZone = 0.025F;
            if (Math.abs(vy) > deadZone) {
                float deg = Math.min((Math.abs(vy) - deadZone) * 200.0F, 45.0F);
                targetPitch = -Math.signum(vy) * deg;
            }
        }
        this.smoothPitch += (targetPitch - this.smoothPitch) * 0.15F;
        this.modelRoot.xRot = this.smoothPitch * ((float) Math.PI / 180.0F);
    }
}
