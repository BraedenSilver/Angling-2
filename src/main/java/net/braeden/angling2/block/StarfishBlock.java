package net.braeden.angling2.block;

import net.braeden.angling2.block.entity.StarfishBlockEntity;
import net.braeden.angling2.entity.util.StarfishColor;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class StarfishBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final boolean dead;

    public StarfishBlock(BlockBehaviour.Properties props, boolean dead) {
        super(props);
        this.dead = dead;
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean waterlogged = ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER;
        return defaultBlockState()
                .setValue(WATERLOGGED, waterlogged)
                .setValue(FACING, ctx.getHorizontalDirection());
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

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
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
            if (targetState.is(AnglingBlocks.CLAM) || targetState.is(AnglingBlocks.OYSTERS)) {
                // Consume the filter feeder
                level.removeBlock(targetPos, false);

                // Try to spread starfish to a nearby horizontal position
                Direction[] horiz = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                int start = random.nextInt(4);
                for (int i = 0; i < 4; i++) {
                    BlockPos spreadPos = pos.relative(horiz[(start + i) % 4]);
                    boolean hasWater = level.getFluidState(spreadPos).is(FluidTags.WATER);
                    boolean canPlace = level.getBlockState(spreadPos).canBeReplaced() || hasWater;
                    BlockPos below = spreadPos.below();
                    if (canPlace && level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
                        level.setBlock(spreadPos, defaultBlockState().setValue(WATERLOGGED, hasWater).setValue(FACING, state.getValue(FACING)), 3);
                        // Propagate the parent's color to the new starfish
                        if (level.getBlockEntity(spreadPos) instanceof StarfishBlockEntity newBe
                                && level.getBlockEntity(pos) instanceof StarfishBlockEntity parentBe) {
                            newBe.setColor(parentBe.getColor());
                        }
                        return;
                    }
                }
                return;
            }
        }
    }
}
