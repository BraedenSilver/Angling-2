package net.braeden.angling2.block.entity;

import net.braeden.angling2.entity.AnglingEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UrchinBlockEntity extends BlockEntity {
    public UrchinBlockEntity(BlockPos pos, BlockState state) {
        super(AnglingEntities.URCHIN, pos, state);
    }
}
