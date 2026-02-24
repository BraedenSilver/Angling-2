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
public final class RightWhaleBlockbench {
    private RightWhaleBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();

        // Root wrapper so animations can reference bones by name and pitch works
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.ZERO);

        // ---- Body -------------------------------------------------------
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-22.0F, -43.0F, -30.0F, 44.0F, 43.0F, 60.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("barnacle_cluster_2",
                CubeListBuilder.create()
                        .texOffs(136, 222).addBox(5.0F, 0.0F, 6.0F, 3.0F, 5.0F, 5.0F, CubeDeformation.NONE)
                        .texOffs(152, 235).addBox(6.0F, 5.0F, -3.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(296, 94).addBox(6.0F, 2.0F, -10.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(288, 88).addBox(6.0F, -3.0F, -2.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(136, 232).addBox(52.0F, 0.0F, 6.0F, 3.0F, 5.0F, 5.0F, CubeDeformation.NONE)
                        .texOffs(74, 298).addBox(52.0F, 5.0F, -3.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(64, 298).addBox(52.0F, 2.0F, -10.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(54, 298).addBox(52.0F, -3.0F, -2.0F, 2.0F, 3.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(92, 179).addBox(10.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(104, 281).addBox(23.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(284, 94).addBox(34.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(284, 94).addBox(28.0F, -7.0F, -15.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(124, 183).addBox(16.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(124, 183).addBox(22.0F, -6.0F, -13.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(76, 179).addBox(16.0F, -7.0F, 3.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(102, 264).addBox(14.0F, -7.0F, -11.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(272, 94).addBox(14.0F, -7.0F, 10.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(92, 281).addBox(47.0F, -7.0F, -3.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(132, 183).addBox(42.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(272, 88).addBox(40.0F, -7.0F, 3.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(102, 270).addBox(42.0F, -7.0F, -11.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(102, 276).addBox(43.0F, -7.0F, 10.0F, 3.0F, 2.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(-30.0F, -38.0F, -15.0F));

        // ---- Head -------------------------------------------------------
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upperjaw = head.addOrReplaceChild("upperjaw",
                CubeListBuilder.create()
                        .texOffs(236, 245).addBox(-20.0F, -19.0F, -14.0F, 40.0F, 33.0F, 14.0F, CubeDeformation.NONE)
                        .texOffs(208, 0).addBox(-20.0F, -19.0F, -45.0F, 40.0F, 19.0F, 31.0F, CubeDeformation.NONE)
                        .texOffs(306, 88).addBox(-16.0F, 0.0F, -41.0F, 32.0F, 17.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(0, 179).addBox(-18.0F, 0.0F, -43.0F, 36.0F, 4.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(0, 298).addBox(14.0F, 0.0F, -39.0F, 2.0F, 17.0F, 25.0F, CubeDeformation.NONE)
                        .texOffs(298, 186).addBox(-16.0F, 0.0F, -39.0F, 2.0F, 17.0F, 25.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -24.0F, -30.0F));

        upperjaw.addOrReplaceChild("barnacle_cluster_1",
                CubeListBuilder.create()
                        .texOffs(118, 234).addBox(-10.0F, -2.0F, -3.0F, 5.0F, 5.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(118, 234).addBox(-14.0F, -10.0F, -3.0F, 5.0F, 5.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(118, 234).addBox(8.0F, -9.0F, -3.0F, 5.0F, 5.0F, 3.0F, CubeDeformation.NONE)
                        .texOffs(298, 228).addBox(-3.0F, 3.0F, -2.0F, 3.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(298, 233).addBox(4.0F, -1.0F, -2.0F, 3.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(298, 233).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                        .texOffs(272, 88).addBox(-5.0F, -15.0F, 32.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(102, 270).addBox(3.0F, -15.0F, 40.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(102, 270).addBox(3.0F, -15.0F, 26.0F, 4.0F, 2.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -6.0F, -45.0F));

        head.addOrReplaceChild("lowerjaw",
                CubeListBuilder.create()
                        .texOffs(148, 103).addBox(-18.0F, 0.0F, -43.0F, 36.0F, 9.0F, 43.0F, CubeDeformation.NONE)
                        .texOffs(236, 292).addBox(16.0F, -14.0F, -41.0F, 2.0F, 14.0F, 27.0F, CubeDeformation.NONE)
                        .texOffs(294, 292).addBox(-18.0F, -14.0F, -41.0F, 2.0F, 14.0F, 27.0F, CubeDeformation.NONE)
                        .texOffs(272, 76).addBox(-18.0F, -10.0F, -43.0F, 36.0F, 10.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -10.0F, -30.0F));

        // ---- Tail chain -------------------------------------------------
        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 103).addBox(-20.0F, -8.0F, 0.0F, 40.0F, 34.0F, 34.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -10.0F, 28.0F));

        PartDefinition middletail = tail.addOrReplaceChild("middletail",
                CubeListBuilder.create()
                        .texOffs(162, 186).addBox(-17.0F, -8.0F, 0.0F, 34.0F, 25.0F, 34.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 1.0F, 32.0F));

        middletail.addOrReplaceChild("endtail",
                CubeListBuilder.create()
                        .texOffs(0, 264).addBox(-11.0F, -4.0F, 0.0F, 22.0F, 10.0F, 24.0F, CubeDeformation.NONE)
                        .texOffs(148, 155).addBox(-29.0F, -1.0F, 12.0F, 58.0F, 4.0F, 27.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -3.0F, 32.0F));

        // ---- Pectoral fins ----------------------------------------------
        root.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                        .texOffs(0, 222).addBox(-22.0F, 0.0F, 0.0F, 22.0F, 5.0F, 37.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-22.0F, 19.0F, -20.0F, -0.2317F, -0.3405F, -1.0122F));

        root.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                        .texOffs(0, 222).addBox(0.0F, 0.0F, 0.0F, 22.0F, 5.0F, 37.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(22.0F, 19.0F, -20.0F, -0.2317F, 0.3405F, 1.0122F));

        // ---- Saddle (empty — reserved bone, no geometry) ----------------
        root.addOrReplaceChild("saddle",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -19.0F, 0.0F));

        return LayerDefinition.create(mesh, 512, 512);
    }
}
