package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.SeaSlugBlockbench;
import net.braeden.angling2.entity.client.blockbench.SeaSlugAnimations;
import net.braeden.angling2.entity.client.state.SeaSlugRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Environment(EnvType.CLIENT)
public class SeaSlugModel extends EntityModel<SeaSlugRenderState> {
    private final KeyframeAnimation ambientAnimation;
    private final KeyframeAnimation movingAnimation;

    public SeaSlugModel(ModelPart root) {
        super(root);
        this.ambientAnimation = SeaSlugAnimations.AMBIENT.bake(root);
        this.movingAnimation = SeaSlugAnimations.MOVING.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        return SeaSlugBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(SeaSlugRenderState state) {
        super.setupAnim(state);
        this.ambientAnimation.apply(state.ambientAnimationState, state.ageInTicks);
        this.movingAnimation.apply(state.movingAnimationState, state.ageInTicks);
    }
}
