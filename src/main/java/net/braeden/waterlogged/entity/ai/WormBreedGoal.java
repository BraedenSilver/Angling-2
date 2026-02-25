package net.braeden.waterlogged.entity.ai;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.RoeBlock;
import net.braeden.waterlogged.block.SeaSlugEggsBlock;
import net.braeden.waterlogged.block.entity.RoeBlockEntity;
import net.braeden.waterlogged.block.entity.SeaSlugEggsBlockEntity;
import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.entity.SeaSlugEntity;
import net.braeden.waterlogged.entity.util.SeaSlugBioluminescence;
import net.braeden.waterlogged.entity.SunfishEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import java.util.EnumSet;
import java.util.List;

/**
 * Two-phase breeding goal for worm-fed aquatic entities.
 *
 * APPROACH: navigate to partner until within range.
 * DESCEND:  navigate down to the nearest floor position, then lay a Roe block.
 */
public class WormBreedGoal extends Goal {

    private enum Phase { APPROACH, DESCEND }

    private final PathfinderMob mob;
    private PathfinderMob partner;
    private Phase phase = Phase.APPROACH;
    private BlockPos targetRoePos = null;

    public WormBreedGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(mob instanceof WormBreeder wb) || !wb.wantsToBreed()) return false;
        if (!(mob.level() instanceof ServerLevel)) return false;
        partner = findBreedingPartner();
        if (partner == null) return false;
        phase = Phase.APPROACH;
        targetRoePos = null;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!(mob instanceof WormBreeder wb) || !wb.wantsToBreed()) return false;
        if (!(mob.level() instanceof ServerLevel)) return false;
        // Once descending, we're committed — partner no longer needs to keep up.
        if (phase == Phase.APPROACH) {
            if (partner == null || !partner.isAlive()) return false;
            if (!(partner instanceof WormBreeder pb) || !pb.wantsToBreed()) return false;
        }
        return true;
    }

    @Override
    public void stop() {
        partner = null;
        targetRoePos = null;
        phase = Phase.APPROACH;
    }

    @Override
    public void tick() {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        if (phase == Phase.APPROACH) {
            if (partner == null) return;
            mob.getLookControl().setLookAt(partner, 10.0f, mob.getMaxHeadXRot());
            mob.getNavigation().moveTo(partner, 1.0);

            if (mob.distanceTo(partner) < 5.0f) {
                targetRoePos = findRoePos(serverLevel, mob.blockPosition());
                if (targetRoePos != null) {
                    phase = Phase.DESCEND;
                }
            }

        } else { // DESCEND
            if (targetRoePos == null) {
                stop();
                return;
            }
            mob.getNavigation().moveTo(
                    targetRoePos.getX() + 0.5,
                    targetRoePos.getY() + 0.5,
                    targetRoePos.getZ() + 0.5,
                    1.0
            );
            mob.getLookControl().setLookAt(
                    targetRoePos.getX() + 0.5,
                    targetRoePos.getY() + 0.5,
                    targetRoePos.getZ() + 0.5,
                    10.0f, mob.getMaxHeadXRot()
            );

            if (mob.blockPosition().distSqr(targetRoePos) <= 4) {
                breed(serverLevel);
            }
        }
    }

    private void breed(ServerLevel level) {
        if (targetRoePos == null) return;

        boolean waterlogged = level.getFluidState(targetRoePos).is(Fluids.WATER);

        if (mob instanceof SeaSlugEntity slugMob && partner instanceof SeaSlugEntity slugPartner) {
            // Sea slugs are hermaphrodites — both parents lay an egg cluster.
            boolean mobGlowing     = slugMob.getBioluminescence() != SeaSlugBioluminescence.NONE;
            boolean partnerGlowing = slugPartner.getBioluminescence() != SeaSlugBioluminescence.NONE;

            // Egg 1 laid by the mob (near its current position).
            BlockState eggState1 = WaterloggedBlocks.SEA_SLUG_EGGS.defaultBlockState()
                    .setValue(SeaSlugEggsBlock.WATERLOGGED, waterlogged)
                    .setValue(SeaSlugEggsBlock.GLOWING, mobGlowing);
            level.setBlock(targetRoePos, eggState1, 3);
            if (level.getBlockEntity(targetRoePos) instanceof SeaSlugEggsBlockEntity eg1) {
                eg1.setParents(slugMob, slugPartner, slugMob.getColor().getId());
            }
            // Egg 2 laid by the partner (near its current position).
            BlockPos partnerRoePos = findRoePos(level, slugPartner.blockPosition());
            if (partnerRoePos != null && !partnerRoePos.equals(targetRoePos)) {
                boolean partnerWaterlogged = level.getFluidState(partnerRoePos).is(Fluids.WATER);
                BlockState eggState2 = WaterloggedBlocks.SEA_SLUG_EGGS.defaultBlockState()
                        .setValue(SeaSlugEggsBlock.WATERLOGGED, partnerWaterlogged)
                        .setValue(SeaSlugEggsBlock.GLOWING, partnerGlowing);
                level.setBlock(partnerRoePos, eggState2, 3);
                if (level.getBlockEntity(partnerRoePos) instanceof SeaSlugEggsBlockEntity eg2) {
                    eg2.setParents(slugMob, slugPartner, slugPartner.getColor().getId());
                }
            }
        } else {
            // All other fish lay generic roe.
            BlockState roeState = WaterloggedBlocks.ROE.defaultBlockState()
                    .setValue(RoeBlock.WATERLOGGED, waterlogged);
            level.setBlock(targetRoePos, roeState, 3);
            if (level.getBlockEntity(targetRoePos) instanceof RoeBlockEntity roeEntity) {
                roeEntity.setParentTypeId(BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).toString());
                if (mob instanceof TropicalFish tf1 && partner instanceof TropicalFish tf2) {
                    int v1 = new TropicalFish.Variant(tf1.getPattern(), tf1.getBaseColor(), tf1.getPatternColor()).getPackedId();
                    int v2 = new TropicalFish.Variant(tf2.getPattern(), tf2.getBaseColor(), tf2.getPatternColor()).getPackedId();
                    roeEntity.setParentVariants(v1, v2);
                } else {
                    int tint = speciesColor(mob.getType());
                    if (tint != -1) roeEntity.setTintArgb(tint);
                    if (mob instanceof SunfishEntity sf) {
                        roeEntity.setParentVariantId(sf.getVariant().getId());
                    }
                }
            }
        }

        if (mob instanceof WormBreeder wb) wb.clearWormBred();
        if (partner instanceof WormBreeder pb) pb.clearWormBred();

        stop();
    }

    private PathfinderMob findBreedingPartner() {
        List<PathfinderMob> nearby = mob.level().getEntitiesOfClass(
                PathfinderMob.class,
                mob.getBoundingBox().inflate(8.0)
        );
        for (PathfinderMob candidate : nearby) {
            if (candidate == mob) continue;
            if (candidate.getType() != mob.getType()) continue;
            if (!(candidate instanceof WormBreeder wb) || !wb.wantsToBreed()) continue;
            return candidate;
        }
        return null;
    }

    /**
     * Search downward (up to 20 blocks) for the first position above a solid floor.
     * Prefers positions closest to the mob vertically.
     */
    private static BlockPos findRoePos(ServerLevel level, BlockPos center) {
        for (int dy = 0; dy >= -20; dy--) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (canPlaceRoe(level, pos)) return pos;
                }
            }
        }
        return null;
    }

    private static boolean canPlaceRoe(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockState below = level.getBlockState(pos.below());
        return (state.isAir() || state.getFluidState().is(Fluids.WATER))
                && below.isFaceSturdy(level, pos.below(), Direction.UP);
    }

    /**
     * Returns a hardcoded ARGB body colour for species that lack per-individual colour variants.
     * -1 means no tint should be applied.
     */
    private static int speciesColor(EntityType<?> type) {
        if (type == WaterloggedEntities.ANGLERFISH)  return 0xFF3A2515;
        if (type == WaterloggedEntities.BUBBLE_EYE)  return 0xFFE67030;
        if (type == WaterloggedEntities.CATFISH)      return 0xFF9B855A;
        if (type == WaterloggedEntities.ANOMALOCARIS) return 0xFFCC7820;
        if (type == WaterloggedEntities.MAHI_MAHI)    return 0xFF2298B0;
        if (type == WaterloggedEntities.SEAHORSE)     return 0xFFDAA030;
        if (type == WaterloggedEntities.DONGFISH)     return 0xFF997755;
        if (type == WaterloggedEntities.SUNFISH)      return 0xFF7EA8B8;
        if (type == EntityType.PUFFERFISH)        return 0xFFD4C014;
        if (type == EntityType.COD)               return 0xFFC27C3C;
        if (type == EntityType.SALMON)            return 0xFFE86E4C;
        return -1;
    }
}
