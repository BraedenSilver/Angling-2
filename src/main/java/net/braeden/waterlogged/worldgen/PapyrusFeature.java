package net.braeden.waterlogged.worldgen;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.PapyrusBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PapyrusFeature extends Feature<NoneFeatureConfiguration> {

    public PapyrusFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        var random = ctx.random();

        int placed = 0;

        for (int attempt = 0; attempt < 128; attempt++) {
            BlockPos pos = origin.offset(
                    random.nextInt(16) - 8,
                    random.nextInt(8) - 4,
                    random.nextInt(16) - 8
            );

            // Position must be air (not water or other blocks)
            BlockState existing = level.getBlockState(pos);
            if (!existing.isAir()) continue;

            // Must not be underwater
            if (level.getFluidState(pos).is(FluidTags.WATER)) continue;

            // Position must have a solid block below
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (!belowState.isFaceSturdy(level, below, Direction.UP)) continue;

            // Must be near water (within 3 blocks horizontally)
            boolean nearWater = false;
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    if (dx == 0 && dz == 0) continue;
                    BlockPos checkPos = pos.offset(dx, 0, dz);
                    if (level.getFluidState(checkPos).is(FluidTags.WATER)) {
                        nearWater = true;
                        break;
                    }
                }
                if (nearWater) break;
            }

            if (!nearWater) continue;

            // Place papyrus with random age
            int age = random.nextInt(3); // 0, 1, or 2
            level.setBlock(pos, WaterloggedBlocks.PAPYRUS.defaultBlockState().setValue(PapyrusBlock.AGE, age), 2);
            placed++;

            // Stop after placing a few
            if (placed >= 8) break;
        }

        return placed > 0;
    }
}
