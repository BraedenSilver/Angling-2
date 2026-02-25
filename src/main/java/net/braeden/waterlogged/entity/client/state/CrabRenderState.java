package net.braeden.waterlogged.entity.client.state;

import net.braeden.waterlogged.entity.util.CrabVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class CrabRenderState extends LivingEntityRenderState {
    public final AnimationState movingAnimationState = new AnimationState();
    public final AnimationState rotatedAnimationState = new AnimationState();
    public final AnimationState forwardsAnimationState = new AnimationState();
    public CrabVariant variant = CrabVariant.DUNGENESS;
    public boolean isBaby = false;
}
