package net.braeden.waterlogged.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface FilterFeeder {
    void onFeed(BlockPos pos, BlockState state, ServerLevel world);
}
