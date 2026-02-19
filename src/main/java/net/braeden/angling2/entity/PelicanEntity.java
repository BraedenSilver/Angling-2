package net.braeden.angling2.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.AnimationState;

public class PelicanEntity extends Animal {
        public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState divingAnimationState = new AnimationState();
    public final AnimationState flappingAnimationState = new AnimationState();
    public final AnimationState beakOpenedAnimationState = new AnimationState();

public PelicanEntity(EntityType<? extends PelicanEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return false;
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            boolean inWater = this.isInWater();
            boolean onGround = this.onGround();
            boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
            if (inWater) {
                this.swimmingAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.flyingAnimationState.stop();
            } else if (!onGround) {
                this.flyingAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.swimmingAnimationState.stop();
            } else if (moving) {
                this.walkingAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.flyingAnimationState.stop();
                this.swimmingAnimationState.stop();
            } else {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.walkingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.swimmingAnimationState.stop();
            }
            this.divingAnimationState.stop();
            this.flappingAnimationState.stop();
            this.beakOpenedAnimationState.stop();
        }
    }

}
