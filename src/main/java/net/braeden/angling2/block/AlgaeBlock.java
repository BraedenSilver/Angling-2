package net.braeden.angling2.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AlgaeBlock extends Block implements SimpleWaterloggedBlock, BonemealableBlock {

    // Thin 1px shapes per face (like vines/glow lichen)
    private static final VoxelShape DOWN_SHAPE  = Block.box(0, 0,  0, 16, 1,  16);
    private static final VoxelShape UP_SHAPE    = Block.box(0, 15, 0, 16, 16, 16);
    private static final VoxelShape NORTH_SHAPE = Block.box(0, 0,  0, 16, 16, 1);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0, 0,  15, 16, 16, 16);
    private static final VoxelShape EAST_SHAPE  = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape WEST_SHAPE  = Block.box(0,  0, 0, 1,  16, 16);

    public static final BooleanProperty NORTH     = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH     = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST      = BlockStateProperties.EAST;
    public static final BooleanProperty WEST      = BlockStateProperties.WEST;
    public static final BooleanProperty UP        = BlockStateProperties.UP;
    public static final BooleanProperty DOWN      = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AlgaeBlock(BlockBehaviour.Properties props) {
        super(props);
        registerDefaultState(stateDefinition.any()
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, WATERLOGGED);
    }

    public static BooleanProperty propertyForFace(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST  -> EAST;
            case WEST  -> WEST;
            case UP    -> UP;
            case DOWN  -> DOWN;
        };
    }

    private static boolean hasFace(BlockState state, Direction dir) {
        return state.getValue(propertyForFace(dir));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }


    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        for (int i = 0; i < 4; i++) { // Try up to 4 times
            Direction dir = Direction.getRandom(random);
            BlockPos targetPos = pos.relative(dir);
            FluidState fluidState = level.getFluidState(targetPos);
            BlockState targetBlockState = level.getBlockState(targetPos);
            
            if (fluidState.getType() == Fluids.WATER && fluidState.isSource() && !targetBlockState.is(this)) {
                 BlockState newState = defaultBlockState().setValue(WATERLOGGED, true);
                 boolean valid = false;
                 // Check all faces for valid attachment in the new position
                 for (Direction face : Direction.values()) {
                     if (canAttachTo(level, targetPos, face)) {
                         newState = newState.setValue(propertyForFace(face), true);
                         valid = true;
                     }
                 }
                 
                 if (valid) {
                     level.setBlock(targetPos, newState, 3);
                     return; // Spread once per bonemeal
                 }
            }
        }
    }

    private static boolean canAttachTo(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos target = pos.relative(dir);
        return level.getBlockState(target).isFaceSturdy(level, target, dir.getOpposite());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (hasFace(state, dir) && canAttachTo(level, pos, dir)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction face = ctx.getClickedFace();
        BlockPos pos = ctx.getClickedPos();
        LevelAccessor level = ctx.getLevel();

        BlockState existing = level.getBlockState(pos);
        boolean isAlgae = existing.is(this);
        BlockState base = isAlgae ? existing : defaultBlockState();

        boolean waterlogged = !isAlgae && level.getFluidState(pos).getType() == Fluids.WATER;

        return base
                .setValue(propertyForFace(face.getOpposite()), true)
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (!ctx.getItemInHand().is(this.asItem())) return false;
        Direction face = ctx.getClickedFace();
        return !state.getValue(propertyForFace(face.getOpposite()));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess,
                                     BlockPos pos, Direction direction, BlockPos neighborPos,
                                     BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        // Remove the face touching the changed neighbor if it can no longer attach
        if (hasFace(state, direction) && !canAttachTo(level, pos, direction)) {
            state = state.setValue(propertyForFace(direction), false);
        }

        // If no faces remain, remove the block
        for (Direction dir : Direction.values()) {
            if (hasFace(state, dir)) {
                return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        if (state.getValue(DOWN))  shape = Shapes.or(shape, DOWN_SHAPE);
        if (state.getValue(UP))    shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(EAST))  shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(WEST))  shape = Shapes.or(shape, WEST_SHAPE);
        return shape.isEmpty() ? Shapes.block() : shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
