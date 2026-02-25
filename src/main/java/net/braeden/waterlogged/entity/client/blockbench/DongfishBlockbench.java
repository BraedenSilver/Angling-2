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
public final class DongfishBlockbench {
    private DongfishBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, -1.0F));
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                .texOffs(0, 8).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.0F, 1.0F));
        head.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(0, 8).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(-1.0F, 1.0F, -1.5F));
        head.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(8, 0).addBox(0.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, CubeDeformation.NONE),
                PartPose.offset(1.0F, 1.0F, -1.5F));
        PartDefinition scungle = head.addOrReplaceChild("scungle",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 1.0F, -1.0F));
        scungle.addOrReplaceChild("horngus",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.0F, 1.0F));

        return LayerDefinition.create(mesh, 16, 16);
    }
}