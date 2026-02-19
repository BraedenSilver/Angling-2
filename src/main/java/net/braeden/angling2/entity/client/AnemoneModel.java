package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.AnemoneAnimations;
import net.braeden.angling2.entity.client.blockbench.AnemoneBlockbench;
import net.braeden.angling2.entity.client.state.AnemoneBlockEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

@Environment(EnvType.CLIENT)
public class AnemoneModel extends Model<AnemoneBlockEntityRenderState> {

    private final KeyframeAnimation vibingAnimation;

    public AnemoneModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutNoCull);
        this.vibingAnimation = AnemoneAnimations.VIBING.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return AnemoneBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(AnemoneBlockEntityRenderState state) {
        this.resetPose();
        this.vibingAnimation.apply(state.vibingAnimationState, state.ageInTicks);
    }
}
