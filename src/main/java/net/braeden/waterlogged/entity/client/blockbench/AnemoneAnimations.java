package net.braeden.waterlogged.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public final class AnemoneAnimations {
    private AnemoneAnimations() {}

    public static final AnimationDefinition VIBING = AnimationDefinition.Builder.withLength(4.0F)
        .looping()
        .addAnimation("tentercools", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, 0, 30),   AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, 0, -30),  AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tentercools2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, 0, -30),  AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, 0, 30),   AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tentercools3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F,    KeyframeAnimations.degreeVec(0, 30, 0),  AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,    KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.0F,    KeyframeAnimations.degreeVec(0, -30, 0), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.9583F, KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tentercools4", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, -30, 0),  AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, 30, 0),   AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}
