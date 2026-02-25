package net.braeden.waterlogged.entity.client;

import net.braeden.waterlogged.entity.client.blockbench.UrchinBlockbench;
import net.braeden.waterlogged.entity.client.state.UrchinBlockEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

@Environment(EnvType.CLIENT)
public class UrchinModel extends Model<UrchinBlockEntityRenderState> {

    private final ModelPart bone;

    public UrchinModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutNoCull);
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition getTexturedModelData() {
        return UrchinBlockbench.getTexturedModelData();
    }

    @Override
    public void setupAnim(UrchinBlockEntityRenderState state) {
        this.resetPose();
        bone.yRot = (float) Math.sin(state.ageInTicks * 0.04) * 0.12f;
    }
}
