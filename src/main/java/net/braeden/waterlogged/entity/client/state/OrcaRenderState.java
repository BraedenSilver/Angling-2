package net.braeden.waterlogged.entity.client.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class OrcaRenderState extends LivingEntityRenderState {
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState mouthAnimationState = new AnimationState();
    public boolean isBaby = false;
    public float verticalSpeed = 0.0F;
    public boolean isInWater = false;
}
