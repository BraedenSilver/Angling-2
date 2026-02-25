package net.braeden.waterlogged.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.braeden.waterlogged.sound.WaterloggedSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class RightWhaleEntity extends AbstractFish {

    // Entity event for periodic mouth animation
    private static final byte ENTITY_EVENT_MOUTH_OPEN = 60;

    private static final EntityDataAccessor<Boolean> IS_BABY =
            SynchedEntityData.defineId(RightWhaleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> AIR =
            SynchedEntityData.defineId(RightWhaleEntity.class, EntityDataSerializers.INT);
    /** 0 = no barnacles, 1 = some barnacles, 2 = full barnacles */
    private static final EntityDataAccessor<Integer> BARNACLE_LEVEL =
            SynchedEntityData.defineId(RightWhaleEntity.class, EntityDataSerializers.INT);

    // Right whales have larger lungs — can stay under ~4 minutes
    private static final int MAX_AIR = 15000;

    // Server-side mouth animation timer (not synced — broadcast via entity event)
    private int mouthTimer = 400;

    public final AnimationState swimAnimationState = new AnimationState();
    /** Used for both periodic mouth-opens and surfacing breaths */
    public final AnimationState surfaceAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    public RightWhaleEntity(EntityType<? extends RightWhaleEntity> type, Level level) {
        super(type, level);
    }

    // -------------------------------------------------------------------------
    // Synced data
    // -------------------------------------------------------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BABY, false);
        builder.define(AIR, MAX_AIR);
        builder.define(BARNACLE_LEVEL, 2);
    }

    public boolean isBaby() { return this.getEntityData().get(IS_BABY); }
    public void setBaby(boolean baby) {
        this.getEntityData().set(IS_BABY, baby);
        this.refreshDimensions();
    }

    public int getAir() { return this.getEntityData().get(AIR); }
    public void setAir(int air) { this.getEntityData().set(AIR, Math.max(0, Math.min(air, MAX_AIR))); }

    /** 0=none, 1=some, 2=full */
    public int getBarnacleLevel() { return this.getEntityData().get(BARNACLE_LEVEL); }
    public void setBarnacleLevel(int level) { this.getEntityData().set(BARNACLE_LEVEL, Math.max(0, Math.min(level, 2))); }

    // -------------------------------------------------------------------------
    // Hitbox — babies get a proper half-size hitbox
    // -------------------------------------------------------------------------

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (this.isBaby()) {
            return getType().getDimensions().scale(0.5f);
        }
        return super.getDefaultDimensions(pose);
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new WhaleFlockGoal(this, 24.0, 8.0));
        this.goalSelector.addGoal(2, new WhaleBabyFollowGoal(this, 18.0, 6.0));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.0));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8, 30));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    // -------------------------------------------------------------------------
    // Attributes — 3× orca health, slower speed
    // -------------------------------------------------------------------------

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 90.0)
                .add(Attributes.MOVEMENT_SPEED, 0.8)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    // -------------------------------------------------------------------------
    // Spawn — 30% calf; random barnacle coverage assigned once at spawn
    // -------------------------------------------------------------------------

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, reason, groupData);
        if (world.getRandom().nextFloat() < 0.30F) {
            this.setBaby(true);
        }
        // Randomise barnacle coverage: equal chance of none / some / full
        this.setBarnacleLevel(world.getRandom().nextInt(3));
        return data;
    }

    // -------------------------------------------------------------------------
    // Persistence — right whales never despawn
    // -------------------------------------------------------------------------

    @Override
    public boolean requiresCustomPersistence() { return true; }

    // -------------------------------------------------------------------------
    // Interaction — shearing removes barnacles one level at a time
    // -------------------------------------------------------------------------

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(Items.SHEARS) && this.getBarnacleLevel() > 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.setBarnacleLevel(this.getBarnacleLevel() - 1);
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                int drops = 1 + this.random.nextInt(3);
                for (int i = 0; i < drops; i++) {
                    this.spawnAtLocation(serverLevel, new ItemStack(Items.NAUTILUS_SHELL));
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getBucketItemStack() { return new ItemStack(Items.WATER_BUCKET); }

    @Override protected SoundEvent getAmbientSound() { return WaterloggedSounds.ENTITY_RIGHT_WHALE_AMBIENT; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return WaterloggedSounds.ENTITY_RIGHT_WHALE_HURT; }
    @Override protected SoundEvent getDeathSound() { return WaterloggedSounds.ENTITY_RIGHT_WHALE_DEATH; }
    @Override protected SoundEvent getFlopSound() { return SoundEvents.DOLPHIN_HURT; }

    // -------------------------------------------------------------------------
    // Entity events — client triggers animation on receiving mouth-open event
    // -------------------------------------------------------------------------

    @Override
    public void handleEntityEvent(byte status) {
        if (status == ENTITY_EVENT_MOUTH_OPEN) {
            this.surfaceAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(status);
        }
    }

    // -------------------------------------------------------------------------
    // Save / load
    // -------------------------------------------------------------------------

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("IsBaby", this.isBaby());
        output.putInt("Air", this.getAir());
        output.putInt("BarnacleLevel", this.getBarnacleLevel());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setBaby(input.getBooleanOr("IsBaby", false));
        this.setAir(input.getIntOr("Air", MAX_AIR));
        this.setBarnacleLevel(input.getIntOr("BarnacleLevel", 2));
    }

    // -------------------------------------------------------------------------
    // Tick — air, mouth timer, animation states
    // -------------------------------------------------------------------------

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.handleAirSupply();

            // Periodic mouth animation — every 20–30 seconds while alive
            if (this.mouthTimer > 0) {
                this.mouthTimer--;
            } else {
                this.mouthTimer = 400 + this.random.nextInt(200);
                this.level().broadcastEntityEvent(this, ENTITY_EVENT_MOUTH_OPEN);
            }
        }

        if (this.level().isClientSide()) {
            if (this.isInWater()) {
                this.swimAnimationState.startIfStopped(this.tickCount);
                this.flopAnimationState.stop();
            } else {
                this.flopAnimationState.startIfStopped(this.tickCount);
                this.swimAnimationState.stop();
                this.surfaceAnimationState.stop();
            }
        }
    }

    private void handleAirSupply() {
        BlockPos eyePos = BlockPos.containing(this.getEyePosition(1.0F));
        boolean headAboveWater = this.level().getFluidState(eyePos).isEmpty();

        if (headAboveWater) {
            this.setAir(Math.min(this.getAir() + 3, MAX_AIR));
            // Trigger a breath animation when surfacing after being low on air
            if (this.getAir() < MAX_AIR / 2) {
                this.mouthTimer = Math.max(this.mouthTimer, 1); // let the periodic timer handle it
            }
        } else if (this.isInWater()) {
            this.setAir(this.getAir() - 1);

            if (this.getAir() < MAX_AIR * 0.15) {
                if (this.hasIceAbove()) {
                    // Under ice: drift laterally every second to find open water
                    if (this.tickCount % 20 == 0) {
                        double angle = this.random.nextDouble() * Math.PI * 2;
                        this.setDeltaMovement(
                                Math.cos(angle) * 0.15 + this.getDeltaMovement().x * 0.6,
                                0.04,
                                Math.sin(angle) * 0.15 + this.getDeltaMovement().z * 0.6
                        );
                    }
                } else {
                    // Clear path — rise to surface
                    this.setDeltaMovement(this.getDeltaMovement().x, 0.10, this.getDeltaMovement().z);
                }
            }
        } else {
            this.setAir(Math.min(this.getAir() + 6, MAX_AIR));
        }
    }

    /** Returns true if ice blocks the path to the surface within 20 blocks above. */
    private boolean hasIceAbove() {
        BlockPos pos = this.blockPosition();
        for (int dy = 1; dy <= 20; dy++) {
            BlockPos above = pos.above(dy);
            BlockState state = this.level().getBlockState(above);
            if (!state.getFluidState().isEmpty()) continue; // still water
            return state.is(Blocks.ICE) || state.is(Blocks.PACKED_ICE)
                    || state.is(Blocks.BLUE_ICE) || state.is(Blocks.FROSTED_ICE);
        }
        return false;
    }

    // =========================================================================
    // Inner goals
    // =========================================================================

    static class WhaleFlockGoal extends Goal {
        private final RightWhaleEntity whale;
        private final double triggerDistSq;
        private final double stopDistSq;
        private RightWhaleEntity nearest;

        WhaleFlockGoal(RightWhaleEntity whale, double triggerDist, double stopDist) {
            this.whale = whale;
            this.triggerDistSq = triggerDist * triggerDist;
            this.stopDistSq = stopDist * stopDist;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override public boolean canUse() {
            this.nearest = findNearest();
            return this.nearest != null && this.whale.distanceToSqr(this.nearest) > this.triggerDistSq;
        }
        @Override public boolean canContinueToUse() {
            return this.nearest != null && this.nearest.isAlive()
                    && this.whale.distanceToSqr(this.nearest) > this.stopDistSq;
        }
        @Override public void start() { moveTo(); }
        @Override public void tick()  { moveTo(); }
        @Override public void stop()  { this.nearest = null; }

        private void moveTo() {
            if (this.nearest != null) this.whale.getNavigation().moveTo(this.nearest, 1.0D);
        }
        private RightWhaleEntity findNearest() {
            List<RightWhaleEntity> nearby = this.whale.level().getEntitiesOfClass(
                    RightWhaleEntity.class, this.whale.getBoundingBox().inflate(24.0),
                    e -> e != this.whale && e.isAlive());
            if (nearby.isEmpty()) return null;
            return nearby.stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(this.whale))).orElse(null);
        }
    }

    static class WhaleBabyFollowGoal extends Goal {
        private final RightWhaleEntity whale;
        private final double triggerDistSq;
        private final double stopDistSq;
        private RightWhaleEntity parent;

        WhaleBabyFollowGoal(RightWhaleEntity whale, double triggerDist, double stopDist) {
            this.whale = whale;
            this.triggerDistSq = triggerDist * triggerDist;
            this.stopDistSq = stopDist * stopDist;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override public boolean canUse() {
            if (!this.whale.isBaby()) return false;
            this.parent = findNearestAdult();
            return this.parent != null && this.whale.distanceToSqr(this.parent) > this.triggerDistSq;
        }
        @Override public boolean canContinueToUse() {
            return this.whale.isBaby() && this.parent != null && this.parent.isAlive()
                    && this.whale.distanceToSqr(this.parent) > this.stopDistSq;
        }
        @Override public void start() { moveTo(); }
        @Override public void tick()  { moveTo(); }
        @Override public void stop()  { this.parent = null; }

        private void moveTo() {
            if (this.parent != null) this.whale.getNavigation().moveTo(this.parent, 1.05D);
        }
        private RightWhaleEntity findNearestAdult() {
            List<RightWhaleEntity> nearby = this.whale.level().getEntitiesOfClass(
                    RightWhaleEntity.class, this.whale.getBoundingBox().inflate(18.0),
                    e -> e != this.whale && e.isAlive() && !e.isBaby());
            if (nearby.isEmpty()) return null;
            return nearby.stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(this.whale))).orElse(null);
        }
    }
}
