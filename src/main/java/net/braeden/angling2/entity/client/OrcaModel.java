package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.OrcaBlockbench;
import net.braeden.angling2.entity.client.blockbench.OrcaAnimations;
import net.braeden.angling2.entity.client.state.OrcaRenderState;
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

    public OrcaModel(ModelPart root) {
        super(root);
        this.modelRoot = root;
        this.swimAnimation = OrcaAnimations.SWIM.bake(root);
        this.flopAnimation = OrcaAnimations.FLOP.bake(root);
        this.attackAnimation = OrcaAnimations.ATTACK.bake(root);
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
        // Pitch up/down only when actively climbing or diving in water — disabled when beached
        if (state.isInWater) {
            float vy = state.verticalSpeed;
            float deadZone = 0.025F;
            if (Math.abs(vy) > deadZone) {
                float pitch = (Math.abs(vy) - deadZone) * 200.0F; // scale to degrees
                pitch = Math.min(pitch, 45.0F);
                this.modelRoot.xRot = -Math.signum(vy) * pitch * ((float) Math.PI / 180.0F);
            } else {
                this.modelRoot.xRot = 0.0F;
            }
        } else {
            this.modelRoot.xRot = 0.0F;
        }
    }
}
