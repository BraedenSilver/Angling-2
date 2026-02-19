package net.braeden.angling2.entity;

import net.braeden.angling2.entity.ai.WormBreeder;
import net.braeden.angling2.entity.ai.WormBreedGoal;
import net.braeden.angling2.entity.util.SunfishVariant;
import net.braeden.angling2.item.AnglingItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.AnimationState;

public class SunfishEntity extends AbstractFish implements WormBreeder {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(SunfishEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState floppingAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    private int wormBredTimer = 0;

    public SunfishEntity(EntityType<? extends SunfishEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getWormBredTimer() { return wormBredTimer; }

    @Override
    public void setWormBredTimer(int timer) { this.wormBredTimer = timer; }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, SunfishVariant.BLUEGILL.getId());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    public SunfishVariant getVariant() {
        return SunfishVariant.byId(this.getEntityData().get(VARIANT));
    }

    public void setVariant(SunfishVariant variant) {
        this.getEntityData().set(VARIANT, variant.getId());
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                                   EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        SunfishVariant[] variants = SunfishVariant.values();
        this.setVariant(variants[world.getRandom().nextInt(variants.length)]);
        return data;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(SunfishVariant.byId(input.getIntOr("Variant", SunfishVariant.BLUEGILL.getId())));
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
                this.floppingAnimationState.stop();
                this.flopAnimationState.stop();
            } else {
                this.flopAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.floppingAnimationState.stop();
            }
        }
    }
}
