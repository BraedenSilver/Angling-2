package net.braeden.angling2.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public final class AnomalocarisAnimations {
    private AnomalocarisAnimations() {}

    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F)
        .looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0.0F,   5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0.0F,  -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("left_lobes", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,  20.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("right_lobes", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    public static final AnimationDefinition FLOP = AnimationDefinition.Builder.withLength(0.5F)
        .looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 10.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F,  KeyframeAnimations.posVec(20.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(20.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.posVec(20.0F, -20.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}