package net.braeden.angling2.block.entity;

import net.braeden.angling2.entity.AnglingEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnemoneBlockEntity extends BlockEntity {
    public final AnimationState vibingAnimationState = new AnimationState();
    public int animTickCount = 0;

    public AnemoneBlockEntity(BlockPos pos, BlockState state) {
        super(AnglingEntities.ANEMONE, pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AnemoneBlockEntity entity) {
        entity.animTickCount++;
        entity.vibingAnimationState.startIfStopped(entity.animTickCount);
    }
}
