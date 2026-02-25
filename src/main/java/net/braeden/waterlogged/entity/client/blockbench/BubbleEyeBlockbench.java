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
public final class BubbleEyeBlockbench {
    private BubbleEyeBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(0, -2).addBox(0.0F, 1.5F, 0.0F, 0.0F, 1.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 22.5F, 0.0F));
        root.addOrReplaceChild("left_eye",
                CubeListBuilder.create()
                .texOffs(8, 9).addBox(0.0F, -0.5176F, -2.9318F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, -1.5F, 0.0F, -0.2618F, 0.0F, -0.3927F));
        root.addOrReplaceChild("right_eye",
                CubeListBuilder.create()
                .texOffs(0, 9).addBox(-2.0F, -0.5176F, -2.9318F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, -1.5F, 0.0F, -0.2618F, 0.0F, 0.3927F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(7, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -0.5F, 3.0F));
        root.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(4, 13).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(1.0F, 1.5F, -1.0F));
        root.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(0, 13).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(-1.0F, 1.5F, -1.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}