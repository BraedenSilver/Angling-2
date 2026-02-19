package net.braeden.angling2.entity.client.state;

import net.braeden.angling2.entity.util.SeaSlugPattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class SeaSlugRenderState extends LivingEntityRenderState {
    public final AnimationState ambientAnimationState = new AnimationState();
    public final AnimationState movingAnimationState = new AnimationState();
    /** Packed ARGB body tint color. */
    public int argbColor = 0xFFFFFFFF;
    public SeaSlugPattern pattern = SeaSlugPattern.PLAIN;
}
