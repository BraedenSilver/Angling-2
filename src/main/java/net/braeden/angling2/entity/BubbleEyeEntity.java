package net.braeden.angling2.entity;

import net.braeden.angling2.entity.ai.WormBreeder;
import net.braeden.angling2.entity.ai.WormBreedGoal;
import net.braeden.angling2.item.AnglingItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.AnimationState;

public class BubbleEyeEntity extends AbstractFish implements WormBreeder {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    private int wormBredTimer = 0;

    public BubbleEyeEntity(EntityType<? extends BubbleEyeEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getWormBredTimer() { return wormBredTimer; }

    @Override
    public void setWormBredTimer(int timer) { this.wormBredTimer = timer; }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 0.6, 40));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == AnglingItems.WORM && !this.level().isClientSide() && wormBredTimer <= 0) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            wormBredTimer = 6000;
            this.level().broadcastEntityEvent(this, (byte) 18);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getFlopSound() {
        return net.minecraft.sounds.SoundEvents.COD_FLOP;
    }

    @Override
    public net.minecraft.world.item.ItemStack getBucketItemStack() {
        return new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.WATER_BUCKET);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && wormBredTimer > 0) {
            wormBredTimer--;
        }

        if (this.level().isClientSide()) {
            if (this.isInWater()) {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.flopAnimationState.stop();
            } else {
                this.flopAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
            }
        }
    }
}
