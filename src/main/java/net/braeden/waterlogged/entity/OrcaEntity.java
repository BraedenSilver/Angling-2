package net.braeden.waterlogged.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.braeden.waterlogged.entity.ai.OrcaJumpGoal;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class OrcaEntity extends AbstractFish {

    private static final byte ENTITY_EVENT_MOUTH_OPEN = 62;

    private static final EntityDataAccessor<Boolean> IS_BABY =
            SynchedEntityData.defineId(OrcaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> AIR =
            SynchedEntityData.defineId(OrcaEntity.class, EntityDataSerializers.INT);

    // Orcas need air less often than dolphins — 150 seconds
    private static final int MAX_AIR = 9000;
    private static final int AIR_DRAIN_UNDERWATER = 1;

    // Server-side timers (not synced — driven by entity events)
    private int attackAnimationTimer = 0;
    private static final int ATTACK_ANIMATION_DURATION = 10;
    private int mouthTimer = 300; // Initial 15 s delay

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState mouthAnimationState = new AnimationState();

    public OrcaEntity(EntityType<? extends OrcaEntity> type, Level level) {
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
    }

    public boolean isBaby() { return this.getEntityData().get(IS_BABY); }
    public void setBaby(boolean baby) {
        this.getEntityData().set(IS_BABY, baby);
        this.refreshDimensions();
    }

    public int getAir() { return this.getEntityData().get(AIR); }
    public void setAir(int air) { this.getEntityData().set(AIR, Math.max(0, Math.min(air, MAX_AIR))); }

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
    // Attack — broadcast event so client triggers the jaw animation
    // -------------------------------------------------------------------------

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            this.attackAnimationTimer = ATTACK_ANIMATION_DURATION;
            this.attackAnimationState.start(this.tickCount);
            level.broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        }
        return result;
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.START_ATTACKING) {
            this.attackAnimationTimer = ATTACK_ANIMATION_DURATION;
            this.attackAnimationState.start(this.tickCount);
        } else if (status == ENTITY_EVENT_MOUTH_OPEN) {
            this.mouthAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(status);
        }
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(2, new OrcaFlockGoal(this, 20.0, 6.0));
        this.goalSelector.addGoal(3, new BabyFollowGoal(this, 18.0, 5.0));
        this.goalSelector.addGoal(4, new OrcaJumpGoal(this, 10));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0, 20));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(OrcaEntity.class));
        this.targetSelector.addGoal(2, new DefendBabyGoal(this));
    }

    // -------------------------------------------------------------------------
    // Attributes
    // -------------------------------------------------------------------------

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    // -------------------------------------------------------------------------
    // Spawn — 25% calf chance
    // -------------------------------------------------------------------------

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, reason, groupData);
        if (world.getRandom().nextFloat() < 0.25F) {
            this.setBaby(true);
        }
        return data;
    }

    // -------------------------------------------------------------------------
    // Interaction
    // -------------------------------------------------------------------------

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getBucketItemStack() { return new ItemStack(Items.WATER_BUCKET); }

    @Override
    protected SoundEvent getFlopSound() { return SoundEvents.DOLPHIN_HURT; }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.DOLPHIN_HURT; }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() { return SoundEvents.DOLPHIN_DEATH; }

    @Override
    protected SoundEvent getSwimSound() { return SoundEvents.DOLPHIN_SWIM; }

    @Override
    protected SoundEvent getSwimSplashSound() { return SoundEvents.DOLPHIN_SPLASH; }

    /**
     * All orca sounds are dolphin sounds pitched down to give that girthy,
     * deep-voiced quality. Multiplying every outgoing pitch by 0.65 achieves
     * roughly a perfect-fifth lower than the dolphin.
     */
    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        super.playSound(sound, volume, pitch * 0.4F);
    }

    // -------------------------------------------------------------------------
    // Save / load
    // -------------------------------------------------------------------------

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("IsBaby", this.isBaby());
        output.putInt("Air", this.getAir());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setBaby(input.getBooleanOr("IsBaby", false));
        this.setAir(input.getIntOr("Air", MAX_AIR));
    }

    // -------------------------------------------------------------------------
    // Tick
    // -------------------------------------------------------------------------

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.handleAirSupply();

            // Attack animation timer
            if (this.attackAnimationTimer > 0) {
                this.attackAnimationTimer--;
            }

            // Periodic mouth animation — every 20–30 seconds
            if (this.mouthTimer > 0) {
                this.mouthTimer--;
            } else {
                this.mouthTimer = 400 + this.random.nextInt(200);
                this.level().broadcastEntityEvent(this, ENTITY_EVENT_MOUTH_OPEN);
            }
        }

        this.attackAnimationState.animateWhen(this.attackAnimationTimer > 0, this.tickCount);

        if (this.level().isClientSide()) {
            if (this.isInWater()) {
                this.swimAnimationState.startIfStopped(this.tickCount);
                this.flopAnimationState.stop();
            } else {
                this.flopAnimationState.startIfStopped(this.tickCount);
                this.swimAnimationState.stop();
            }
        }
    }

    private void handleAirSupply() {
        BlockPos eyePos = BlockPos.containing(this.getEyePosition(1.0F));
        boolean headAboveWater = this.level().getFluidState(eyePos).isEmpty();

        if (headAboveWater) {
            this.setAir(Math.min(this.getAir() + 4, MAX_AIR));
        } else if (this.isInWater()) {
            this.setAir(this.getAir() - AIR_DRAIN_UNDERWATER);

            if (this.getAir() < MAX_AIR * 0.2) {
                if (this.hasIceAbove()) {
                    // Under ice: drift laterally to find open water
                    if (this.tickCount % 20 == 0) {
                        double angle = this.random.nextDouble() * Math.PI * 2;
                        this.setDeltaMovement(
                                Math.cos(angle) * 0.18 + this.getDeltaMovement().x * 0.6,
                                0.05,
                                Math.sin(angle) * 0.18 + this.getDeltaMovement().z * 0.6
                        );
                    }
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().x, 0.15, this.getDeltaMovement().z);
                }
            }
        } else {
            this.setAir(Math.min(this.getAir() + 8, MAX_AIR));
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

    static class OrcaFlockGoal extends Goal {
        private final OrcaEntity orca;
        private final double triggerDistSq;
        private final double stopDistSq;
        private OrcaEntity leader;

        OrcaFlockGoal(OrcaEntity orca, double triggerDist, double stopDist) {
            this.orca = orca;
            this.triggerDistSq = triggerDist * triggerDist;
            this.stopDistSq = stopDist * stopDist;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override public boolean canUse() {
            if (this.orca.getTarget() != null) return false;
            this.leader = findNearest();
            return this.leader != null && this.orca.distanceToSqr(this.leader) > this.triggerDistSq;
        }
        @Override public boolean canContinueToUse() {
            return this.leader != null && this.leader.isAlive()
                    && this.orca.getTarget() == null
                    && this.orca.distanceToSqr(this.leader) > this.stopDistSq;
        }
        @Override public void start() { moveTo(); }
        @Override public void tick()  { moveTo(); }
        @Override public void stop()  { this.leader = null; }

        private void moveTo() {
            if (this.leader != null) this.orca.getNavigation().moveTo(this.leader, 1.0D);
        }
        private OrcaEntity findNearest() {
            List<OrcaEntity> nearby = this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class, this.orca.getBoundingBox().inflate(20.0),
                    e -> e != this.orca && e.isAlive());
            if (nearby.isEmpty()) return null;
            return nearby.stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(this.orca))).orElse(null);
        }
    }

    static class BabyFollowGoal extends Goal {
        private final OrcaEntity orca;
        private final double triggerDistSq;
        private final double stopDistSq;
        private OrcaEntity parent;

        BabyFollowGoal(OrcaEntity orca, double triggerDist, double stopDist) {
            this.orca = orca;
            this.triggerDistSq = triggerDist * triggerDist;
            this.stopDistSq = stopDist * stopDist;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override public boolean canUse() {
            if (!this.orca.isBaby()) return false;
            if (this.orca.getTarget() != null) return false;
            this.parent = findNearestAdult();
            return this.parent != null && this.orca.distanceToSqr(this.parent) > this.triggerDistSq;
        }
        @Override public boolean canContinueToUse() {
            return this.orca.isBaby() && this.parent != null && this.parent.isAlive()
                    && this.orca.getTarget() == null
                    && this.orca.distanceToSqr(this.parent) > this.stopDistSq;
        }
        @Override public void start() { moveTo(); }
        @Override public void tick()  { moveTo(); }
        @Override public void stop()  { this.parent = null; }

        private void moveTo() {
            if (this.parent != null) this.orca.getNavigation().moveTo(this.parent, 1.05D);
        }
        private OrcaEntity findNearestAdult() {
            List<OrcaEntity> nearby = this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class, this.orca.getBoundingBox().inflate(18.0),
                    e -> e != this.orca && e.isAlive() && !e.isBaby());
            if (nearby.isEmpty()) return null;
            return nearby.stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(this.orca))).orElse(null);
        }
    }

    static class DefendBabyGoal extends Goal {
        private static final double BABY_SCAN_RADIUS = 24.0;
        private static final double PLAYER_THREAT_RADIUS = 8.0;
        private final OrcaEntity orca;
        private Player threat;

        DefendBabyGoal(OrcaEntity orca) {
            this.orca = orca;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override public boolean canUse() {
            if (this.orca.isBaby()) return false;
            if (this.orca.getTarget() != null) return false;
            List<OrcaEntity> babies = this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class, this.orca.getBoundingBox().inflate(BABY_SCAN_RADIUS),
                    e -> e.isAlive() && e.isBaby());
            if (babies.isEmpty()) return false;
            for (OrcaEntity baby : babies) {
                List<Player> nearPlayers = this.orca.level().getEntitiesOfClass(
                        Player.class, baby.getBoundingBox().inflate(PLAYER_THREAT_RADIUS),
                        p -> !p.isSpectator() && !p.isCreative());
                if (!nearPlayers.isEmpty()) {
                    this.threat = nearPlayers.get(0);
                    return true;
                }
            }
            return false;
        }
        @Override public boolean canContinueToUse() {
            return this.threat != null && this.threat.isAlive()
                    && !this.threat.isSpectator() && !this.threat.isCreative();
        }
        @Override public void start() {
            this.orca.setTarget(this.threat);
            this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class, this.orca.getBoundingBox().inflate(BABY_SCAN_RADIUS),
                    e -> e != this.orca && e.isAlive() && !e.isBaby() && e.getTarget() == null
            ).forEach(pod -> pod.setTarget(this.threat));
        }
        @Override public void stop() { this.threat = null; }
        @Override public void tick() { if (this.threat != null) this.orca.setTarget(this.threat); }
    }
}
