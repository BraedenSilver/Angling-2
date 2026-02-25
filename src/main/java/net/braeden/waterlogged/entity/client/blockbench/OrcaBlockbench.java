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
public final class OrcaBlockbench {
    private OrcaBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();

        // All parts live under a "root" wrapper so animations can reference them by name
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        // ---- Head -------------------------------------------------------
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 9.0F, -24.0F));

        head.addOrReplaceChild("lowerjaw",
                CubeListBuilder.create()
                        .texOffs(120, 167)
                        .addBox(-14.0F, 0.0F, -32.0F, 28.0F, 10.0F, 32.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 5.0F, 0.0F));

        head.addOrReplaceChild("upperjaw",
                CubeListBuilder.create()
                        .texOffs(120, 113)
                        .addBox(-14.0F, -20.0F, -34.0F, 28.0F, 20.0F, 34.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 5.0F, 0.0F));

        // ---- Body -------------------------------------------------------
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-18.0F, -35.0F, 0.0F, 36.0F, 35.0F, 48.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, -24.0F));

        // ---- Tail chain -------------------------------------------------
        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 113)
                        .addBox(-14.0F, -12.5F, 0.0F, 28.0F, 28.0F, 32.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 8.5F, 24.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2",
                CubeListBuilder.create()
                        .texOffs(168, 0)
                        .addBox(-8.0F, -8.5F, 0.0F, 16.0F, 18.0F, 32.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 6.0F, 32.0F));

        tail2.addOrReplaceChild("tail_fin",
                CubeListBuilder.create()
                        .texOffs(0, 83)
                        .addBox(-27.0F, -3.0F, 0.0F, 54.0F, 6.0F, 24.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 4.5F, 25.0F));

        // ---- Pectoral fins ----------------------------------------------
        PartDefinition leftFin = root.addOrReplaceChild("left_fin",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(18.0F, 21.0F, -23.0F, 0.0F, 0.9599F, 0.3927F));

        leftFin.addOrReplaceChild("left_fin_r1",
                CubeListBuilder.create()
                        .texOffs(168, 50)
                        .addBox(-1.0F, 0.0F, 0.0F, 4.0F, 16.0F, 32.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rightFin = root.addOrReplaceChild("right_fin",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-18.0F, 21.0F, -23.0F, 0.0F, -0.9599F, -0.3927F));

        rightFin.addOrReplaceChild("right_fin_r1",
                CubeListBuilder.create()
                        .texOffs(0, 173)
                        .addBox(-3.0F, 0.0F, 0.0F, 4.0F, 16.0F, 32.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        // ---- Dorsal fin -------------------------------------------------
        root.addOrReplaceChild("back_fin",
                CubeListBuilder.create()
                        .texOffs(72, 209)
                        .addBox(-2.5F, -2.1809F, 2.0261F, 5.0F, 15.0F, 29.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, 1.2217F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 512);
    }
}
