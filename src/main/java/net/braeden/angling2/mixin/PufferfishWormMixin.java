package net.braeden.angling2.mixin;

import net.braeden.angling2.entity.ai.WormBreeder;
import net.braeden.angling2.entity.ai.WormBreedGoal;
import net.braeden.angling2.item.AnglingItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Makes Pufferfish breedable with worms.
 */
@Mixin(Pufferfish.class)
public abstract class PufferfishWormMixin extends PathfinderMob implements WormBreeder {

    protected PufferfishWormMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private int angling_wormBredTimer = 0;

    @Override
    public int getWormBredTimer() {
        return angling_wormBredTimer;
    }

    @Override
    public void setWormBredTimer(int timer) {
        this.angling_wormBredTimer = timer;
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void angling_addWormBreedGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == AnglingItems.WORM && !this.level().isClientSide() && angling_wormBredTimer <= 0) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            angling_wormBredTimer = 6000;
            this.level().broadcastEntityEvent(this, (byte) 18);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void angling_tickWormTimer(CallbackInfo ci) {
        if (!((PathfinderMob) (Object) this).level().isClientSide() && angling_wormBredTimer > 0) {
            angling_wormBredTimer--;
        }
    }
}
