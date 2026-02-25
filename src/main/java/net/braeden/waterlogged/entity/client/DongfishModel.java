package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.DongfishBlockbench;
import net.braeden.waterlogged.entity.client.blockbench.DongfishAnimations;
import net.braeden.waterlogged.entity.client.state.DongfishRenderState;
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
    private final ModelPart scungle;

    public DongfishModel(ModelPart root) {
        super(root);
        this.scungle = root.getChild("root").getChild("head").getChild("scungle");
        this.idleAnimation = DongfishAnimations.IDLE.bake(root);
        this.flopAnimation = DongfishAnimations.FLOP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return DongfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(DongfishRenderState state) {
        super.setupAnim(state);
        this.scungle.visible = !state.sheared;
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.flopAnimation.apply(state.flopAnimationState, state.ageInTicks);
    }
}
