package net.braeden.angling2.entity;

import net.braeden.angling2.entity.ai.WormBreeder;
import net.braeden.angling2.entity.ai.WormBreedGoal;
import net.braeden.angling2.item.AnglingItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.AnimationState;

public class AnomalocarisEntity extends WaterAnimal implements WormBreeder {
    public final AnimationState idleAnimationState = new AnimationState();

    private int wormBredTimer = 0;

    public AnomalocarisEntity(EntityType<? extends AnomalocarisEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getWormBredTimer() { return wormBredTimer; }

    @Override
    public void setWormBredTimer(int timer) { this.wormBredTimer = timer; }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    public static boolean canSpawn(EntityType<AnomalocarisEntity> type, LevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return true;
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
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && wormBredTimer > 0) {
            wormBredTimer--;
        }

        if (this.level().isClientSide()) {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }
}
