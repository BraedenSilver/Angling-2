package net.braeden.waterlogged.entity.client.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

@Environment(EnvType.CLIENT)
public class UrchinBlockEntityRenderState extends BlockEntityRenderState {
    public float ageInTicks;
}
