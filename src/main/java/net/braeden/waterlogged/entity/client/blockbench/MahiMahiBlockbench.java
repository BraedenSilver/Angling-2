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
public final class MahiMahiBlockbench {
    private MahiMahiBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 20.0F, 0.0F));
        PartDefinition front = root.addOrReplaceChild("front",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.0F, -4.0F, -11.0F, 4.0F, 9.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(36, 32).addBox(-1.0F, 5.0F, -5.0F, 2.0F, 6.0F, 6.0F, CubeDeformation.NONE)
                .texOffs(20, 22).addBox(0.0F, -9.0F, -11.0F, 0.0F, 5.0F, 11.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.0F, -2.0F));
        front.addOrReplaceChild("right_fin",
                CubeListBuilder.create()
                .texOffs(0, 13).addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 3.0F, -5.0F, 0.0F, 0.5236F, 0.0F));
        front.addOrReplaceChild("left_fin",
                CubeListBuilder.create()
                .texOffs(0, 17).addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(2.0F, 3.0F, -5.0F, 0.0F, -0.5236F, 0.0F));
        PartDefinition back = root.addOrReplaceChild("back",
                CubeListBuilder.create()
                .texOffs(19, 9).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 8.0F, 11.0F, CubeDeformation.NONE)
                .texOffs(20, 17).addBox(0.0F, -9.0F, 0.0F, 0.0F, 5.0F, 11.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.0F, -2.0F));
        back.addOrReplaceChild("tail",
                CubeListBuilder.create()
                .texOffs(0, 18).addBox(0.0F, -6.0F, 0.0F, 0.0F, 11.0F, 10.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 11.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}