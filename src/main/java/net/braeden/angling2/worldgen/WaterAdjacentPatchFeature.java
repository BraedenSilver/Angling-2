package net.braeden.angling2.worldgen;

import net.braeden.angling2.block.AlgaeBlock;
import net.braeden.angling2.block.AnglingBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WaterAdjacentPatchFeature extends Feature<NoneFeatureConfiguration> {

    public WaterAdjacentPatchFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        var random = ctx.random();

        int placed = 0;

        for (int attempt = 0; attempt < 64; attempt++) {
            BlockPos pos = origin.offset(
                    random.nextInt(8) - 4,
                    random.nextInt(6) - 3,
                    random.nextInt(8) - 4
            );

            // Position must be submerged in water
            if (!level.getFluidState(pos).is(FluidTags.WATER)) continue;

            // Position must not be a solid block (we're placing INTO the water here)
            BlockState existing = level.getBlockState(pos);
            if (!existing.canBeReplaced() && !existing.is(AnglingBlocks.ALGAE)) continue;

            // Find all directions with a sturdy neighboring face
            boolean isAlgaeAlready = existing.is(AnglingBlocks.ALGAE);
            BlockState algaeState = isAlgaeAlready
                    ? existing
                    : AnglingBlocks.ALGAE.defaultBlockState().setValue(AlgaeBlock.WATERLOGGED, true);

            boolean hasAnyFace = false;
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = pos.relative(dir);
                BlockState neighborState = level.getBlockState(neighbor);
                if (neighborState.isFaceSturdy(level, neighbor, dir.getOpposite())) {
                    algaeState = algaeState.setValue(AlgaeBlock.propertyForFace(dir), true);
                    hasAnyFace = true;
                }
            }

            if (!hasAnyFace) continue;

            level.setBlock(pos, algaeState, 2);
            placed++;
        }

        return placed > 0;
    }
}
