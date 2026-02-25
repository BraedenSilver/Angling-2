package net.braeden.waterlogged.worldgen;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.StarfishBlock;
import net.braeden.waterlogged.block.entity.StarfishBlockEntity;
import net.braeden.waterlogged.entity.util.StarfishColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StarfishFeature extends Feature<NoneFeatureConfiguration> {

    public StarfishFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        var random = ctx.random();

        int placed = 0;

        for (int attempt = 0; attempt < 24; attempt++) {
            BlockPos pos = origin.offset(
                    random.nextInt(8) - 4,
                    random.nextInt(6) - 3,
                    random.nextInt(8) - 4
            );

            // Position must be submerged in water
            if (!level.getFluidState(pos).is(FluidTags.WATER)) continue;

            // Position must not already be occupied by a solid block
            BlockState existing = level.getBlockState(pos);
            if (!existing.canBeReplaced()) continue;

            // Try each direction to find a suitable surface
            // Shuffle by picking a random start index
            Direction[] directions = Direction.values();
            int start = random.nextInt(directions.length);
            for (int i = 0; i < directions.length; i++) {
                Direction face = directions[(start + i) % directions.length];
                Direction support = face.getOpposite();
                BlockPos supportPos = pos.relative(support);

                if (level.getBlockState(supportPos).isFaceSturdy(level, supportPos, face)) {
                    BlockState starfishState = WaterloggedBlocks.STARFISH.defaultBlockState()
                            .setValue(StarfishBlock.WATERLOGGED, true)
                            .setValue(StarfishBlock.FACING, face)
                            .setValue(StarfishBlock.ROTATION, random.nextInt(4));
                    level.setBlock(pos, starfishState, 2);

                    // Assign a random color
                    if (level.getBlockEntity(pos) instanceof StarfishBlockEntity be) {
                        be.setColor(StarfishColor.random(random));
                    }

                    placed++;
                    break;
                }
            }
        }

        return placed > 0;
    }
}
