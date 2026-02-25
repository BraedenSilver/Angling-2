package net.braeden.waterlogged.block;

import net.braeden.waterlogged.particle.WaterloggedParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WormyDirtBlock extends Block implements WormyBlock {
    public WormyDirtBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) == 0) {
            // Try to find a valid spot above the block, up to 5 blocks high
            for (int i = 1; i <= 5; i++) {
                BlockPos checkPos = pos.above(i);
                BlockState checkState = level.getBlockState(checkPos);

                // If it's a solid block that isn't dirt/grass/mud, we're blocked
                if (checkState.isRedstoneConductor(level, checkPos) && !isDirtLike(checkState)) {
                    break;
                }

                // If it's not a full block (like air, water, tall grass), spawn particle here
                if (!checkState.isRedstoneConductor(level, checkPos) || !checkState.isCollisionShapeFullBlock(level, checkPos)) {
                    double x = checkPos.getX() + random.nextDouble();
                    double y = checkPos.getY() + 0.1;
                    double z = checkPos.getZ() + random.nextDouble();
                    level.addParticle(WaterloggedParticles.WORM, x, y, z, 0, 0, 0);
                    break;
                }
            }
        }
    }

    private boolean isDirtLike(BlockState state) {
        return state.is(net.minecraft.tags.BlockTags.DIRT);
    }
}
