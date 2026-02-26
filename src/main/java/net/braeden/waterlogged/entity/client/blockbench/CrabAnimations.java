package net.braeden.waterlogged.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public final class CrabAnimations {
    private CrabAnimations() {}

    public static final AnimationDefinition MOVING = AnimationDefinition.Builder.withLength(0.5F)
        .looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(0.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("front_left_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("back_left_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("back_right_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("front_right_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, 35.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-2.5F, 35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    public static final AnimationDefinition ROTATED = AnimationDefinition.Builder.withLength(2.0F)
        .looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -90.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_left_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.8F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_right_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.8F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    public static final AnimationDefinition FORWARDS = AnimationDefinition.Builder.withLength(1.0F)
        .looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    /** Crab rave: plays when a jukebox is nearby. */
    public static final AnimationDefinition DANCE = AnimationDefinition.Builder.withLength(0.5F)
        .looping()
        // Body: bounce twice per cycle + sway side to side
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        // Legs: rapid alternating stomp
        .addAnimation("front_left_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("front_right_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("back_left_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("back_right_legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F,  35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        // Claws: raised triumphantly, waving to the beat.
        // X kept to -40°/-50° delta (default is -15°, so effective -55°/-65° — raised but forward enough to clear the shell).
        // Z adds outward lean to keep arms away from the body.
        .addAnimation("claw_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-40.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(-50.0F, 25.0F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(-40.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-50.0F, 25.0F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-40.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-40.0F, 35.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(-50.0F, 20.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(-40.0F, 35.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-50.0F, 20.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-40.0F, 35.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        // Pinchers: rapid snapping, out of phase
        .addAnimation("claw_left_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("claw_right_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.125F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}