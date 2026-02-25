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
public final class SeaSlugBlockbench {
    private SeaSlugBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.0F, -1.75F, -5.0F, 4.0F, 3.0F, 5.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.25F, 1.0F));
        head.addOrReplaceChild("frills1",
                CubeListBuilder.create()
                .texOffs(12, 12).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.75F, -4.0F));
        head.addOrReplaceChild("frills2",
                CubeListBuilder.create()
                .texOffs(12, 10).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.75F, -2.0F));
        head.addOrReplaceChild("frills3",
                CubeListBuilder.create()
                .texOffs(9, 8).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.75F, 0.0F));
        PartDefinition butt = root.addOrReplaceChild("butt",
                CubeListBuilder.create()
                .texOffs(0, 8).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.5F, 1.0F));
        butt.addOrReplaceChild("frills4",
                CubeListBuilder.create()
                .texOffs(13, 0).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -1.5F, 2.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}