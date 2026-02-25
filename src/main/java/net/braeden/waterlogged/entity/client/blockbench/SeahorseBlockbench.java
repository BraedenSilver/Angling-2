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
public final class SeahorseBlockbench {
    private SeahorseBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 18.0F, 0.0F));
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                .texOffs(8, 6).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(0, 7).addBox(0.0F, 0.0F, -5.0F, 0.0F, 4.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 4.0F, 2.0F));
        body.addOrReplaceChild("fin",
                CubeListBuilder.create()
                .texOffs(10, 11).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 2.0F, 2.0F));

        return LayerDefinition.create(mesh, 16, 16);
    }
}