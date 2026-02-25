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
public final class AnomalocarisBlockbench {
    private AnomalocarisBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, -1.0F, -6.0F, 5.0F, 3.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 22.0F, 0.0F));
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                .texOffs(20, 21).addBox(-2.5F, -1.0F, -4.0F, 5.0F, 3.0F, 4.0F, CubeDeformation.NONE)
                .texOffs(6, 0).addBox(-3.0F, -3.0F, -4.0F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(2.0F, -3.0F, -4.0F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(-1.5F, 0.0F, -9.0F, 0.0F, 5.0F, 5.0F, CubeDeformation.NONE)
                .texOffs(0, 10).addBox(1.5F, 0.0F, -9.0F, 0.0F, 5.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, -6.0F));
        root.addOrReplaceChild("left_lobes",
                CubeListBuilder.create()
                .texOffs(10, 0).addBox(0.0F, 0.0F, -6.0F, 4.0F, 0.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(2.5F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_lobes",
                CubeListBuilder.create()
                .texOffs(0, 15).addBox(-4.0F, 0.0F, -6.0F, 4.0F, 0.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(-2.5F, 0.0F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(14, 15).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 6.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}