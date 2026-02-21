package net.braeden.angling2.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public final class FryBlockbench {
    private FryBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 3).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 23.5F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(4, 5).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 8, 8);
    }
}