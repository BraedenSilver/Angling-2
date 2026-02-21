package net.braeden.angling2.entity.client.blockbench;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public final class OrcaAnimations {
    private OrcaAnimations() {}

    // Mammalian swimming: only the tail chain pivots, body stays locked.
    // Slower 2s cycle for a more majestic pace.
    public static final AnimationDefinition SWIM = AnimationDefinition.Builder.withLength(2.0F)
            .looping()
            // Tiny heading drift on the whole root — barely perceptible, just adds life
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, -1.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0.0F,  1.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0.0F, -1.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            // Tail base — drives the stroke
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-12.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            // Tail segment 2 — follows with more amplitude
            .addAnimation("tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            // Flukes — maximum pivot at the tip
            .addAnimation("tail_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-22.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 22.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-22.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    // Attack lunge: lower jaw snaps open then closes
    public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.5F)
            // Head dips forward into the lunge
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            // Lower jaw drops open quickly, then snaps shut
            .addAnimation("lowerjaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    // Stranded / out-of-water flopping: orca rolls side to side on the Z axis
    public static final AnimationDefinition FLOP = AnimationDefinition.Builder.withLength(0.6F)
            .looping()
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.0F),  AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F,   KeyframeAnimations.degreeVec(0.0F, 0.0F,  8.0F),  AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0.0F, 0.0F, -8.0F),  AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F,   KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
