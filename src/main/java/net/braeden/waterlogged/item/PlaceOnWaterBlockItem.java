package net.braeden.waterlogged.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class PlaceOnWaterBlockItem extends BlockItem {

    public PlaceOnWaterBlockItem(Block block, Item.Properties props) {
        super(block, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos clicked = ctx.getClickedPos();
        Level level = ctx.getLevel();
        if (level.getFluidState(clicked).is(FluidTags.WATER)) {
            return tryPlace(ctx.getPlayer(), level, ctx.getItemInHand(), clicked.above());
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        }
        return tryPlace(player, level, stack, hit.getBlockPos().above());
    }

    private InteractionResult tryPlace(Player player, Level level, ItemStack stack, BlockPos placePos) {
        if (!level.getFluidState(placePos).isEmpty()) {
            return InteractionResult.FAIL;
        }
        BlockState state = getBlock().defaultBlockState();
        if (!state.canSurvive(level, placePos) || !level.getBlockState(placePos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }
        if (!level.isClientSide()) {
            level.setBlock(placePos, state, 3);
            SoundType sound = state.getSoundType();
            level.playSound(null, placePos, sound.getPlaceSound(), SoundSource.BLOCKS,
                    (sound.getVolume() + 1f) / 2f, sound.getPitch() * 0.8f);
            if (player == null || !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
