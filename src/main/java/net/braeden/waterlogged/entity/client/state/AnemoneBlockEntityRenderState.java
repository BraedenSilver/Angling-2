package net.braeden.waterlogged.entity.client.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class AnemoneBlockEntityRenderState extends BlockEntityRenderState {
    public final AnimationState vibingAnimationState = new AnimationState();
    public float ageInTicks;
}
