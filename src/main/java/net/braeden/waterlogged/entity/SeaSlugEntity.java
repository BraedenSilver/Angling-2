package net.braeden.waterlogged.entity;

import net.braeden.waterlogged.entity.ai.WormBreeder;
import net.braeden.waterlogged.entity.ai.WormBreedGoal;
import net.braeden.waterlogged.entity.util.SeaSlugBioluminescence;
import net.braeden.waterlogged.entity.util.SeaSlugColor;
import net.braeden.waterlogged.entity.util.SeaSlugPattern;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.braeden.waterlogged.entity.ai.WormTemptGoal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AnimationState;

public class SeaSlugEntity extends WaterAnimal implements WormBreeder, Bucketable {
    private static final EntityDataAccessor<Integer> COLOR =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BIOLUMINESCENCE =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.BYTE);
    private static final int FLAG_CLIMBING = 0x1;

    public final AnimationState ambientAnimationState = new AnimationState();
    public final AnimationState movingAnimationState = new AnimationState();

    private int wormBredTimer = 0;
    private boolean fromBucket = false;

    public SeaSlugEntity(EntityType<? extends SeaSlugEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getWormBredTimer() { return wormBredTimer; }

    @Override
    public void setWormBredTimer(int timer) { this.wormBredTimer = timer; }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR, SeaSlugColor.RED.getId());
        builder.define(PATTERN, SeaSlugPattern.PLAIN.getId());
        builder.define(BIOLUMINESCENCE, SeaSlugBioluminescence.NONE.getId());
        builder.define(DATA_FLAGS, (byte) 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
        this.goalSelector.addGoal(2, new WormTemptGoal(this, 1.0));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 0.6, 40));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 8.0);
    }

    public SeaSlugColor getColor() {
        return SeaSlugColor.byId(this.getEntityData().get(COLOR));
    }

    public void setColor(SeaSlugColor color) {
        this.getEntityData().set(COLOR, color.getId());
    }

    public SeaSlugPattern getPattern() {
        return SeaSlugPattern.byId(this.getEntityData().get(PATTERN));
    }

    public void setPattern(SeaSlugPattern pattern) {
        this.getEntityData().set(PATTERN, pattern.getId());
    }

    public SeaSlugBioluminescence getBioluminescence() {
        return SeaSlugBioluminescence.byId(this.getEntityData().get(BIOLUMINESCENCE));
    }

    public void setBioluminescence(SeaSlugBioluminescence biolum) {
        this.getEntityData().set(BIOLUMINESCENCE, biolum.getId());
    }

    public static boolean canSpawn(EntityType<SeaSlugEntity> type, LevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        FluidState fluid = level.getFluidState(pos);
        FluidState above = level.getFluidState(pos.above());
        return fluid.is(FluidTags.WATER) && above.is(FluidTags.WATER);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                                   EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        var rng = world.getRandom();
        SeaSlugColor[] colors = SeaSlugColor.values();
        SeaSlugPattern[] patterns = SeaSlugPattern.values();
        this.setColor(colors[rng.nextInt(colors.length)]);
        this.setPattern(patterns[rng.nextInt(patterns.length)]);
        int biolumRoll = rng.nextInt(10);
        if (biolumRoll == 7) this.setBioluminescence(SeaSlugBioluminescence.BODY);
        else if (biolumRoll == 8) this.setBioluminescence(SeaSlugBioluminescence.PATTERN);
        else if (biolumRoll == 9) this.setBioluminescence(SeaSlugBioluminescence.BOTH);
        return data;
    }

    public boolean isClimbing() {
        return (this.getEntityData().get(DATA_FLAGS) & FLAG_CLIMBING) != 0;
    }

    public void setClimbing(boolean climbing) {
        byte flags = this.getEntityData().get(DATA_FLAGS);
        this.getEntityData().set(DATA_FLAGS, climbing ? (byte) (flags | FLAG_CLIMBING) : (byte) (flags & ~FLAG_CLIMBING));
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    @Override
    public boolean fromBucket() { return this.fromBucket; }

    @Override
    public void setFromBucket(boolean b) { this.fromBucket = b; }

    @Override
    public SoundEvent getPickupSound() { return SoundEvents.BUCKET_FILL_FISH; }

    @Override
    public ItemStack getBucketItemStack() {
        ItemStack stack = new ItemStack(WaterloggedItems.SEA_SLUG_BUCKET);
        int argb = this.getColor().getArgb();
        stack.set(DataComponents.CUSTOM_MODEL_DATA,
                new CustomModelData(java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of(argb, argb)));
        return stack;
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, tag -> {
            tag.putFloat("Health", this.getHealth());
            tag.putInt("Color", this.getColor().getId());
            tag.putInt("Pattern", this.getPattern().getId());
            tag.putInt("Bioluminescence", this.getBioluminescence().getId());
        });
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        this.setHealth(tag.getFloatOr("Health", this.getMaxHealth()));
        this.setColor(SeaSlugColor.byId(tag.getIntOr("Color", SeaSlugColor.RED.getId())));
        this.setPattern(SeaSlugPattern.byId(tag.getIntOr("Pattern", SeaSlugPattern.PLAIN.getId())));
        this.setBioluminescence(SeaSlugBioluminescence.byId(tag.getIntOr("Bioluminescence", 0)));
    }

    @Override
    public void checkDespawn() {
        if (!this.fromBucket() && !this.hasCustomName()) {
            super.checkDespawn();
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Color", this.getColor().getId());
        output.putInt("Pattern", this.getPattern().getId());
        output.putInt("Bioluminescence", this.getBioluminescence().getId());
        output.putBoolean("FromBucket", this.fromBucket);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setColor(SeaSlugColor.byId(input.getIntOr("Color", SeaSlugColor.RED.getId())));
        this.setPattern(SeaSlugPattern.byId(input.getIntOr("Pattern", SeaSlugPattern.PLAIN.getId())));
        this.setBioluminescence(SeaSlugBioluminescence.byId(input.getIntOr("Bioluminescence", 0)));
        this.fromBucket = input.getBooleanOr("FromBucket", false);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.WATER_BUCKET)) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        if (stack.getItem() == WaterloggedItems.WORM && !this.level().isClientSide() && wormBredTimer <= 0) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            wormBredTimer = 6000;
            this.level().broadcastEntityEvent(this, (byte) 7);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            for (int i = 0; i < 7; i++) {
                double ox = this.random.nextGaussian() * 0.02;
                double oy = this.random.nextGaussian() * 0.02;
                double oz = this.random.nextGaussian() * 0.02;
                this.level().addParticle(ParticleTypes.HEART,
                        this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0 - this.getBbWidth(),
                        this.getY() + 0.5 + this.random.nextFloat() * this.getBbHeight(),
                        this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0 - this.getBbWidth(),
                        ox, oy, oz);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (wormBredTimer > 0) wormBredTimer--;
            this.setClimbing(this.horizontalCollision);
        }

        if (this.level().isClientSide()) {
            boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
            if (moving) {
                this.movingAnimationState.startIfStopped(this.tickCount);
                this.ambientAnimationState.stop();
            } else {
                this.ambientAnimationState.startIfStopped(this.tickCount);
                this.movingAnimationState.stop();
            }
        }
    }
}
