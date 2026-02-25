package net.braeden.waterlogged.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public final class SunfishBlockbench {
    private SunfishBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 5.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(10, 0).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 4.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 5).addBox(0.0F, -4.0F, -2.0F, 0.0F, 1.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(0.0F, 2.0F, 1.0F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 16).addBox(-1.0F, 2.0F, -2.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 22.0F, -0.5F));
        root.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(6, 11).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, 0.5672F, 0.0F));
        root.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, -0.5672F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(0, 9).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}