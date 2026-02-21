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
public final class StarfishBlockbench {
    private StarfishBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bbMain = root.addOrReplaceChild("bb_main",
                CubeListBuilder.create()
                        .texOffs(2, 0).addBox(-8.0F, -4.1F, -7.0F, 16.0F, 0.0F, 14.0F, CubeDeformation.NONE)
                        .texOffs(0, 20).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        bbMain.addOrReplaceChild("bottom_r1",
                CubeListBuilder.create()
                        .texOffs(2, 0).addBox(-8.0F, -0.1F, -7.0F, 16.0F, 0.0F, 14.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        bbMain.addOrReplaceChild("rightarm_r1",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

        bbMain.addOrReplaceChild("leftarm_r1",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.3963F, 0.0F));

        bbMain.addOrReplaceChild("rightleg_r1",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        bbMain.addOrReplaceChild("leftleg_r1",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
