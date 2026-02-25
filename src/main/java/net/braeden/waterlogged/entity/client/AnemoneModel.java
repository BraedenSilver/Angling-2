package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.AnemoneAnimations;
import net.braeden.waterlogged.entity.client.blockbench.AnemoneBlockbench;
import net.braeden.waterlogged.entity.client.state.AnemoneBlockEntityRenderState;
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
    private final ModelPart body;

    public AnemoneModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutNoCull);
        this.vibingAnimation = AnemoneAnimations.VIBING.bake(root);
        this.body = root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        return AnemoneBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(AnemoneBlockEntityRenderState state) {
        this.resetPose();
        this.vibingAnimation.apply(state.vibingAnimationState, state.ageInTicks);
        body.zRot += (float) Math.sin(state.ageInTicks * 0.035) * 0.10f;
    }
}
