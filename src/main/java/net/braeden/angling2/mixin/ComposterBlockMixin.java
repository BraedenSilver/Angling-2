package net.braeden.angling2.mixin;

import net.braeden.angling2.item.AnglingItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.Entity;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {

    @Inject(method = "extractProduce", at = @At("HEAD"))
    private static void angling_extractProduce(Entity entity, BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (!level.isClientSide()) {
             // Spawn 1 worm
            ItemEntity itemEntity = new ItemEntity(level, 
                pos.getX() + 0.5D, 
                pos.getY() + 1.1D,
                pos.getZ() + 0.5D,
                new ItemStack(AnglingItems.WORM, 1)
            );
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }
}
