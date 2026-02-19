package net.braeden.angling2.entity.client.state;

import net.braeden.angling2.entity.util.SunfishVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class SunfishRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState floppingAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public SunfishVariant variant = SunfishVariant.BLUEGILL;
}
