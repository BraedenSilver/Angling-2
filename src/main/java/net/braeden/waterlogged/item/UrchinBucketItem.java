package net.braeden.waterlogged.item;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.UrchinBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class UrchinBucketItem extends Item {

    public UrchinBucketItem(Item.Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos placePos = ctx.getClickedPos().relative(ctx.getClickedFace());
        boolean waterlogged = level.getFluidState(placePos).getType() == Fluids.WATER;
        BlockState newState = WaterloggedBlocks.URCHIN.defaultBlockState()
                .setValue(UrchinBlock.WATERLOGGED, waterlogged);

        if (!level.getBlockState(placePos).canBeReplaced() || !newState.canSurvive(level, placePos)) {
            return InteractionResult.FAIL;
        }

        Player player = ctx.getPlayer();
        if (!level.isClientSide()) {
            level.setBlock(placePos, newState, 3);
            SoundType sound = newState.getSoundType();
            level.playSound(null, placePos, sound.getPlaceSound(), SoundSource.BLOCKS,
                    (sound.getVolume() + 1f) / 2f, sound.getPitch() * 0.8f);
            if (player != null && !player.getAbilities().instabuild) {
                player.setItemInHand(ctx.getHand(), new ItemStack(Items.BUCKET));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
