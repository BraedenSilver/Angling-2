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
public final class CatfishBlockbench {
    private CatfishBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(14, 11).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(-2.5F, -3.0F, -6.0F, 5.0F, 4.0F, 12.0F, CubeDeformation.NONE)
                .texOffs(0, 26).addBox(-2.5F, -3.0F, -6.0F, 5.0F, 4.0F, 12.0F, new CubeDeformation(0.15F))
                .texOffs(0, 6).addBox(0.0F, -6.0F, -4.0F, 0.0F, 3.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        root.addOrReplaceChild("left_whisker",
                CubeListBuilder.create()
                .texOffs(0, 9).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(2.5F, -3.0F, -5.5F));
        root.addOrReplaceChild("right_whisker",
                CubeListBuilder.create()
                .texOffs(0, 8).addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(-2.5F, -3.0F, -5.5F));
        root.addOrReplaceChild("chin_whiskers",
                CubeListBuilder.create()
                .texOffs(0, 10).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 1.0F, -5.0F));
        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(22, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.5F, 6.0F));
        tail.addOrReplaceChild("tail_fin",
                CubeListBuilder.create()
                .texOffs(0, 12).addBox(0.0F, -3.5F, -1.0F, 0.0F, 7.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        root.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(0, 4).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(2.5F, 0.0F, -3.0F));
        root.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(-2.5F, 0.0F, -3.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}