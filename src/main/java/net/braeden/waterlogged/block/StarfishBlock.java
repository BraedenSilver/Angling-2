package net.braeden.waterlogged.block;

import net.braeden.waterlogged.block.entity.StarfishBlockEntity;
import net.braeden.waterlogged.entity.util.StarfishColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StarfishBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);

    // VoxelShapes per attachment face
    private static final VoxelShape SHAPE_FLOOR   = Block.box(1, 0, 1, 15, 2, 15);
    private static final VoxelShape SHAPE_CEILING = Block.box(1, 14, 1, 15, 16, 15);
    private static final VoxelShape SHAPE_NORTH   = Block.box(1, 1, 14, 15, 15, 16);
    private static final VoxelShape SHAPE_SOUTH   = Block.box(1, 1, 0, 15, 15, 2);
    private static final VoxelShape SHAPE_EAST    = Block.box(0, 1, 1, 2, 15, 15);
    private static final VoxelShape SHAPE_WEST    = Block.box(14, 1, 1, 16, 15, 15);

    private final boolean dead;

    public StarfishBlock(BlockBehaviour.Properties props, boolean dead) {
        super(props);
        this.dead = dead;
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP).setValue(ROTATION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, ROTATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        Direction clickedFace = ctx.getClickedFace();
        BlockPos supportPos = ctx.getClickedPos().relative(clickedFace.getOpposite());
        LevelReader level = ctx.getLevel();
        int rotation = horizontalToRotation(ctx.getHorizontalDirection());
        // Verify the support face is sturdy
        if (level.getBlockState(supportPos).isFaceSturdy(level, supportPos, clickedFace)) {
            return defaultBlockState()
                    .setValue(WATERLOGGED, waterlogged)
                    .setValue(FACING, clickedFace)
                    .setValue(ROTATION, rotation);
        }
        return null;
    }

    public static int horizontalToRotation(Direction dir) {
        return switch (dir) {
            case NORTH -> 0;
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess,
                                     BlockPos pos, Direction direction, BlockPos neighborPos,
                                     BlockState neighborState, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    /**
     * Returns the direction toward the supporting block (the face the starfish is attached to).
     * FACING stores the outward normal, so the support is in the opposite direction.
     */
    public static Direction getSupportDirection(BlockState state) {
        return state.getValue(FACING).getOpposite();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction support = getSupportDirection(state);
        BlockPos supportPos = pos.relative(support);
        return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, support.getOpposite());
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case UP    -> SHAPE_FLOOR;
            case DOWN  -> SHAPE_CEILING;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST  -> SHAPE_EAST;
            case WEST  -> SHAPE_WEST;
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StarfishBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!dead && !level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof StarfishBlockEntity be) {
                be.setColor(StarfishColor.random(level.getRandom()));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (dead) return;

        // Starfish eat adjacent clams and oysters (like clams eat algae)
        for (Direction dir : Direction.values()) {
            BlockPos targetPos = pos.relative(dir);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.is(WaterloggedBlocks.CLAM) || targetState.is(WaterloggedBlocks.OYSTERS)) {
                // Consume the filter feeder
                level.removeBlock(targetPos, false);

                // Try to spread starfish to a nearby position on any surface
                Direction[] allDirs = Direction.values();
                int start = random.nextInt(allDirs.length);
                for (int i = 0; i < allDirs.length; i++) {
                    Direction spreadDir = allDirs[(start + i) % allDirs.length];
                    BlockPos spreadPos = pos.relative(spreadDir);
                    boolean hasWater = level.getFluidState(spreadPos).is(FluidTags.WATER);
                    boolean canPlace = level.getBlockState(spreadPos).canBeReplaced() || hasWater;
                    if (!canPlace) continue;

                    // Try each face at the spread position for a suitable surface
                    for (Direction face : Direction.values()) {
                        BlockPos supportPos = spreadPos.relative(face.getOpposite());
                        if (level.getBlockState(supportPos).isFaceSturdy(level, supportPos, face)) {
                            BlockState newState = defaultBlockState()
                                    .setValue(WATERLOGGED, hasWater)
                                    .setValue(FACING, face)
                                    .setValue(ROTATION, random.nextInt(4));
                            level.setBlock(spreadPos, newState, 3);
                            // Propagate the parent's color to the new starfish
                            if (level.getBlockEntity(spreadPos) instanceof StarfishBlockEntity newBe
                                    && level.getBlockEntity(pos) instanceof StarfishBlockEntity parentBe) {
                                newBe.setColor(parentBe.getColor());
                            }
                            return;
                        }
                    }
                }
                return;
            }
        }
    }
}
