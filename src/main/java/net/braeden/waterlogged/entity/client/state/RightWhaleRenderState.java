package net.braeden.waterlogged.entity.client.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class RightWhaleRenderState extends LivingEntityRenderState {
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState surfaceAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    /** 0 = no barnacles, 1 = some (body only), 2 = full (head + body) */
    public int barnacleLevel = 2;
    public boolean isBaby = false;
    public float verticalSpeed = 0.0F;
    public boolean isInWater = false;
}
