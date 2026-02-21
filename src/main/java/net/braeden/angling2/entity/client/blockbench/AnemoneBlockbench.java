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
public final class AnemoneBlockbench {
    private AnemoneBlockbench() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition tentacle = body.addOrReplaceChild("tentacle",
                CubeListBuilder.create()
                        .texOffs(-12, 35).addBox(-11.0F, 1.0F, -1.0F, 12.0F, 0.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offset(5.0F, -1.0F, -5.0F));

        tentacle.addOrReplaceChild("tentercools",
                CubeListBuilder.create()
                        .texOffs(1, 17).addBox(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.829F));

        tentacle.addOrReplaceChild("tentercools2",
                CubeListBuilder.create()
                        .texOffs(1, 17).addBox(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-8.0F, 1.0F, 5.0F, 0.0F, 0.0F, -0.829F));

        tentacle.addOrReplaceChild("tentercools3",
                CubeListBuilder.create()
                        .texOffs(1, 17).addBox(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-5.0F, 1.0F, 2.0F, 1.5708F, -0.829F, -1.5708F));

        tentacle.addOrReplaceChild("tentercools4",
                CubeListBuilder.create()
                        .texOffs(1, 17).addBox(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-5.0F, 1.0F, 8.0F, -1.5708F, 0.829F, -1.5708F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
