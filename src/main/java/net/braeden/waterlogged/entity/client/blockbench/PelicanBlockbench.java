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
public final class PelicanBlockbench {
    private PelicanBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 17.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -7.0F, -9.0F, 8.0F, 7.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 2.0F, 0.0F));
        body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(20, 55).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 6.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -4.0F, 3.0F));
        PartDefinition neck = body.addOrReplaceChild("neck",
                CubeListBuilder.create()
                .texOffs(38, 55).addBox(-1.5F, -9.5F, -1.5F, 3.0F, 11.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -6.5F, -6.5F));
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -8.5F, 0.0F));
        PartDefinition head_joint = head.addOrReplaceChild("head_joint",
                CubeListBuilder.create()
                .texOffs(0, 55).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition beak = head_joint.addOrReplaceChild("beak",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, -4.0F));
        beak.addOrReplaceChild("beak_top",
                CubeListBuilder.create()
                .texOffs(34, 40).addBox(-2.0F, -0.5F, -13.0F, 4.0F, 1.0F, 13.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -0.5F, 0.0F));
        beak.addOrReplaceChild("beak_bottom",
                CubeListBuilder.create()
                .texOffs(0, 40).addBox(-2.0F, -0.5F, -13.0F, 4.0F, 2.0F, 13.0F, CubeDeformation.NONE)
                .texOffs(40, 0).addBox(0.0F, 1.5F, -13.0F, 0.0F, 4.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.5F, 0.0F));
        body.addOrReplaceChild("left_wing",
                CubeListBuilder.create()
                .texOffs(0, 20).addBox(0.0F, -2.0F, -2.0F, 1.0F, 6.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(4.0F, -5.0F, -6.0F));
        body.addOrReplaceChild("right_wing",
                CubeListBuilder.create()
                .texOffs(30, 20).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 6.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offset(-4.0F, -5.0F, -6.0F));
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                .texOffs(64, 55).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 5.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(-2.5F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                .texOffs(50, 55).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 5.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(2.5F, 2.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}