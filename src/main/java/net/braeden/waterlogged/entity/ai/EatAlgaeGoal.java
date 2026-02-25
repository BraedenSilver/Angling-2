package net.braeden.waterlogged.entity.ai;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Iterator;

public class EatAlgaeGoal extends Goal {

    private static final int SEARCH_RANGE = 8;
    private static final int EAT_COOLDOWN = 400; // 20s between eats
    private static final double EAT_DISTANCE_SQ = 2.5 * 2.5;

    private final PathfinderMob mob;
    private BlockPos targetPos = null;
    private int eatCooldownUntil = 0;

    public EatAlgaeGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.tickCount < eatCooldownUntil) return false;
        if (!mob.isInWater()) return false;
        targetPos = findAlgae();
        return targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (targetPos == null) return false;
        return mob.level().getBlockState(targetPos).is(WaterloggedBlocks.ALGAE);
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(
                targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5, 1.0);
    }

    @Override
    public void tick() {
        if (targetPos == null) return;
        if (mob.distanceToSqr(Vec3.atCenterOf(targetPos)) <= EAT_DISTANCE_SQ) {
            if (mob.level().getBlockState(targetPos).is(WaterloggedBlocks.ALGAE)) {
                mob.level().removeBlock(targetPos, false);
                eatCooldownUntil = mob.tickCount + EAT_COOLDOWN;
                targetPos = null;
            }
        }
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        if (mob.tickCount >= eatCooldownUntil) {
            eatCooldownUntil = mob.tickCount + 100; // short cooldown if goal ended without eating
        }
        targetPos = null;
    }

    private BlockPos findAlgae() {
        Level level = mob.level();
        BlockPos center = mob.blockPosition();
        Iterator<BlockPos> iter = BlockPos.betweenClosed(
                center.offset(-SEARCH_RANGE, -SEARCH_RANGE, -SEARCH_RANGE),
                center.offset(SEARCH_RANGE, SEARCH_RANGE, SEARCH_RANGE)
        ).iterator();
        while (iter.hasNext()) {
            BlockPos p = iter.next();
            if (level.getBlockState(p).is(WaterloggedBlocks.ALGAE)) {
                return p.immutable();
            }
        }
        return null;
    }
}
