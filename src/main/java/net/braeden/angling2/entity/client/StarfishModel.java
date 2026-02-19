package net.braeden.angling2.entity.client;

import net.braeden.angling2.entity.client.blockbench.StarfishBlockbench;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

@Environment(EnvType.CLIENT)
public class StarfishModel extends Model<StarfishBlockEntityRenderer.StarfishRenderState> {

    public StarfishModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        return StarfishBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(StarfishBlockEntityRenderer.StarfishRenderState state) {
        this.resetPose();
    }
}
