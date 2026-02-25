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
public final class AnglerfishBlockbench {
    private AnglerfishBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(20, 0).addBox(0.0F, -5.0F, -2.0F, 0.0F, 2.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(20, 0).addBox(0.0F, 3.0F, 0.0F, 0.0F, 2.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        root.addOrReplaceChild("jaw",
                CubeListBuilder.create()
                .texOffs(0, 14).addBox(-3.0F, -0.5F, -3.75F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.5F, -0.5F));
        root.addOrReplaceChild("illicium",
                CubeListBuilder.create()
                .texOffs(14, 15).addBox(-0.5F, -6.0F, -6.0F, 1.0F, 6.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, 0.7854F, 0.0F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(0, 15).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        root.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(0.0F, -0.5F, 0.0F, 0.0F, 3.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(3.0F, -1.5F, 0.0F));
        root.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(22, 10).addBox(0.0F, -0.5F, 0.0F, 0.0F, 3.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(-3.0F, -1.5F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}