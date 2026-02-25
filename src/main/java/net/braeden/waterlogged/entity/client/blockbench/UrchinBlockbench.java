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
public final class UrchinBlockbench {
    private UrchinBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone = root.addOrReplaceChild("bone",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 25.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition spinessmall = bone.addOrReplaceChild("spinessmall",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 1.0F));

        spinessmall.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(4, 10).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, -1.5708F, 0.7854F, 3.1416F));

        spinessmall.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(4, 10).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, -1.0F, -2.0F, -1.5708F, -0.7854F, -3.1416F));

        spinessmall.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(4, 10).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, -1.0F, -2.0F, 1.5708F, -0.7854F, 0.0F));

        spinessmall.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(4, 10).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, -1.0F, 0.0F, 1.5708F, 0.7854F, 0.0F));

        PartDefinition spineslarge = bone.addOrReplaceChild("spineslarge",
                CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -11.0F, 0.0F, 0.0F, 12.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 1.0F));

        spineslarge.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        spineslarge.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

        spineslarge.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, 3.1416F, 0.0F));

        root.addOrReplaceChild("bb_main",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}
