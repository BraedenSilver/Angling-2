package net.braeden.waterlogged.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AquariumGlassBlock extends TransparentBlock {

    public AquariumGlassBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Return empty shape so no occlusion check happens (allows water to be seen?)
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    /*@Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }*/

    @Override
    public boolean skipRendering(BlockState state, BlockState neighborState, Direction dir) {
        return neighborState.is(this) || super.skipRendering(state, neighborState, dir);
    }
}
