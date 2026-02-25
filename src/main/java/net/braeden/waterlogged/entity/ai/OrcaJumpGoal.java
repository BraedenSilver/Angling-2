package net.braeden.waterlogged.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

/**
 * Porpoising/jumping behaviour for the Orca — a direct adaptation of the
 * vanilla {@code DolphinJumpGoal}, generalised to work with any
 * {@link PathfinderMob} instead of being hard-typed to {@code Dolphin}.
 */
public class OrcaJumpGoal extends JumpGoal {

    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};

    private final PathfinderMob mob;
    private final int interval;
    private boolean breached;

    public OrcaJumpGoal(PathfinderMob mob, int interval) {
        this.mob = mob;
        this.interval = reducedTickDelay(interval);
    }

    // -------------------------------------------------------------------------
    // Eligibility checks
    // -------------------------------------------------------------------------

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.interval) != 0) {
            return false;
        }
        Direction direction = this.mob.getMotionDirection();
        int dx = direction.getStepX();
        int dz = direction.getStepZ();
        BlockPos pos = this.mob.blockPosition();
        for (int k : STEPS_TO_CHECK) {
            if (!waterIsClear(pos, dx, dz, k) || !surfaceIsClear(pos, dx, dz, k)) {
                return false;
            }
        }
        return true;
    }

    private boolean waterIsClear(BlockPos pos, int dx, int dz, int k) {
        BlockPos check = pos.offset(dx * k, 0, dz * k);
        return this.mob.level().getFluidState(check).is(FluidTags.WATER)
                && this.mob.level().getBlockState(check).getCollisionShape(this.mob.level(), check).isEmpty();
    }

    private boolean surfaceIsClear(BlockPos pos, int dx, int dz, int k) {
        return this.mob.level().getBlockState(pos.offset(dx * k, 1, dz * k)).isAir()
                && this.mob.level().getBlockState(pos.offset(dx * k, 2, dz * k)).isAir();
    }

    @Override
    public boolean canContinueToUse() {
        double dy = this.mob.getDeltaMovement().y;
        return (!(dy * dy < 0.03F) || this.mob.getXRot() == 0.0F
                || !(Math.abs(this.mob.getXRot()) < 10.0F) || !this.mob.isInWater())
                && !this.mob.onGround();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @Override
    public void start() {
        Direction direction = this.mob.getMotionDirection();
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(
                direction.getStepX() * 0.6, 0.7, direction.getStepZ() * 0.6));
        this.mob.getNavigation().stop();
        this.breached = false; // reset so the splash sound fires each jump
    }

    @Override
    public void stop() {
        this.mob.setXRot(0.0F);
    }

    @Override
    public void tick() {
        boolean wasBreach = this.breached;
        if (!wasBreach) {
            FluidState fluid = this.mob.level().getFluidState(this.mob.blockPosition());
            this.breached = fluid.is(FluidTags.WATER);
        }

        // Play jump/splash sound the moment the orca re-enters water.
        // OrcaEntity.playSound() will automatically lower the pitch.
        if (this.breached && !wasBreach) {
            this.mob.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vel = this.mob.getDeltaMovement();
        if (vel.y * vel.y < 0.03F && this.mob.getXRot() != 0.0F) {
            this.mob.setXRot(Mth.rotLerp(0.2F, this.mob.getXRot(), 0.0F));
        } else if (vel.length() > 1.0E-5F) {
            double hDist = vel.horizontalDistance();
            double angle = Math.atan2(-vel.y, hDist) * 180.0 / Math.PI;
            this.mob.setXRot((float) angle);
        }
    }
}
