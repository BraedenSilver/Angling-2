package net.braeden.waterlogged.block.entity;

import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UrchinBlockEntity extends BlockEntity {
    public int animTickCount = 0;

    public UrchinBlockEntity(BlockPos pos, BlockState state) {
        super(WaterloggedEntities.URCHIN, pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, UrchinBlockEntity entity) {
        entity.animTickCount++;
    }
}
