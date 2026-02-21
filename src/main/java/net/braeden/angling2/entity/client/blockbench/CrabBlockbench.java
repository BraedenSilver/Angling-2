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
public final class CrabBlockbench {
    private CrabBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 4.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 22.0F, 0.0F));
        root.addOrReplaceChild("eyes",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, CubeDeformation.NONE)
                .texOffs(0, 3).addBox(-3.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -4.0F, -2.0F));
        root.addOrReplaceChild("front_left_legs",
                CubeListBuilder.create()
                .texOffs(20, 3).addBox(0.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(4.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.7418F));
        root.addOrReplaceChild("back_left_legs",
                CubeListBuilder.create()
                .texOffs(20, 0).addBox(0.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(4.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.7418F));
        PartDefinition claw_left = root.addOrReplaceChild("claw_left",
                CubeListBuilder.create()
                .texOffs(12, 21).addBox(0.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(4.0F, -2.0F, -3.0F, -0.2618F, -0.6545F, 0.0F));
        claw_left.addOrReplaceChild("claw_left_top",
                CubeListBuilder.create()
                .texOffs(12, 13).addBox(-2.0F, -2.0F, -6.0F, 3.0F, 2.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(2.0F, 1.0F, 0.0F));
        PartDefinition claw_right = root.addOrReplaceChild("claw_right",
                CubeListBuilder.create()
                .texOffs(0, 19).addBox(-3.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(-4.0F, -2.0F, -3.0F, -0.2618F, 0.6545F, 0.0F));
        claw_right.addOrReplaceChild("claw_right_top",
                CubeListBuilder.create()
                .texOffs(0, 11).addBox(-1.0F, -2.0F, -6.0F, 3.0F, 2.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(-2.0F, 1.0F, 0.0F));
        root.addOrReplaceChild("back_right_legs",
                CubeListBuilder.create()
                .texOffs(9, 11).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-4.0F, 0.0F, 3.0F, 0.0F, 0.0F, -0.7418F));
        root.addOrReplaceChild("front_right_legs",
                CubeListBuilder.create()
                .texOffs(9, 14).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 0.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-4.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.7418F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}