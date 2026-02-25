package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.RightWhaleAnimations;
import net.braeden.waterlogged.entity.client.blockbench.RightWhaleBlockbench;
import net.braeden.waterlogged.entity.client.state.RightWhaleRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class RightWhaleModel extends EntityModel<RightWhaleRenderState> {

    private final ModelPart modelRoot;
    /** Barnacle cluster on the head (upperjaw child) — visible when level >= 2 */
    private final ModelPart barnacleCluster1;
    /** Barnacle cluster on the body — visible when level >= 1 */
    private final ModelPart barnacleCluster2;

    private final KeyframeAnimation swimAnimation;
    private final KeyframeAnimation surfaceAnimation;
    private final KeyframeAnimation flopAnimation;

    // Smoothed pitch value — lerped each frame to avoid abrupt transitions
    private float smoothPitch = 0.0F;

    public RightWhaleModel(ModelPart root) {
        super(root);
        this.modelRoot = root;

        ModelPart whaleRoot = root.getChild("root");
        ModelPart body = whaleRoot.getChild("body");
        this.barnacleCluster2 = body.getChild("barnacle_cluster_2");
        ModelPart head = whaleRoot.getChild("head");
        ModelPart upperjaw = head.getChild("upperjaw");
        this.barnacleCluster1 = upperjaw.getChild("barnacle_cluster_1");

        this.swimAnimation    = RightWhaleAnimations.SWIM.bake(root);
        this.surfaceAnimation = RightWhaleAnimations.SURFACE_BREATHE.bake(root);
        this.flopAnimation    = RightWhaleAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return RightWhaleBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(RightWhaleRenderState state) {
        super.setupAnim(state);

        this.swimAnimation.apply(state.swimAnimationState, state.ageInTicks);
        this.surfaceAnimation.apply(state.surfaceAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);

        // Barnacle visibility driven by barnacleLevel
        this.barnacleCluster2.visible = state.barnacleLevel >= 1;
        this.barnacleCluster1.visible = state.barnacleLevel >= 2;

        // Smoothly lerp pitch when diving or ascending — avoids jarring snaps
        float targetPitch = 0.0F;
        if (state.isInWater) {
            float vy = state.verticalSpeed;
            float deadZone = 0.02F;
            if (Math.abs(vy) > deadZone) {
                float deg = Math.min((Math.abs(vy) - deadZone) * 180.0F, 40.0F);
                targetPitch = -Math.signum(vy) * deg;
            }
        }
        this.smoothPitch += (targetPitch - this.smoothPitch) * 0.12F;
        this.modelRoot.xRot = this.smoothPitch * ((float) Math.PI / 180.0F);
    }
}
