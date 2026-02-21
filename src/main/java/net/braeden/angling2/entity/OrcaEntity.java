package net.braeden.angling2.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class OrcaEntity extends AbstractFish {

    private static final EntityDataAccessor<Boolean> IS_BABY =
            SynchedEntityData.defineId(OrcaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> AIR =
            SynchedEntityData.defineId(OrcaEntity.class, EntityDataSerializers.INT);

    // Breathing mechanics — orcas need air less often than dolphins due to larger lungs
    private static final int MAX_AIR = 9000;        // 150 seconds = 2.5 minutes (dolphins are ~300 ticks)
    private static final int AIR_DRAIN_UNDERWATER = 1;    // Slow drain when fully submerged

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int attackAnimationTimer = 0;
    private static final int ATTACK_ANIMATION_DURATION = 10;

    public OrcaEntity(EntityType<? extends OrcaEntity> type, Level level) {
        super(type, level);
    }

    // -------------------------------------------------------------------------
    // Baby flag — synced so the client knows for rendering
    // -------------------------------------------------------------------------

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BABY, false);
        builder.define(AIR, MAX_AIR);
    }

    public boolean isBaby() {
        return this.getEntityData().get(IS_BABY);
    }

    public void setBaby(boolean baby) {
        this.getEntityData().set(IS_BABY, baby);
        this.refreshDimensions();
    }

    public int getAir() {
        return this.getEntityData().get(AIR);
    }

    public void setAir(int air) {
        this.getEntityData().set(AIR, Math.max(0, Math.min(air, MAX_AIR)));
    }

    // -------------------------------------------------------------------------
    // Attack — broadcast entity event so the client triggers the jaw animation
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
        } else {
            super.handleEntityEvent(status);
        }
    }

    // -------------------------------------------------------------------------
    // Persistence — orcas never despawn
    // -------------------------------------------------------------------------

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    // -------------------------------------------------------------------------
    // Goals
    // -------------------------------------------------------------------------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(2, new OrcaFlockGoal(this, 20.0, 6.0));
        this.goalSelector.addGoal(3, new BabyFollowGoal(this, 18.0, 5.0));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 20));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // Hurt-by alert: getting hit makes the whole pod retaliate
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(OrcaEntity.class));
        // Adults defend nearby babies: attack players who come too close to a calf
        this.targetSelector.addGoal(2, new DefendBabyGoal(this));
    }

    // -------------------------------------------------------------------------
    // Attributes
    // -------------------------------------------------------------------------

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 1.2)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    // -------------------------------------------------------------------------
    // Spawn — 25 % chance each orca spawns as a calf
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
    // Bucket / interaction — orcas cannot be bucketed, cannot be bred
    // -------------------------------------------------------------------------

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.DOLPHIN_HURT;
    }

    // -------------------------------------------------------------------------
    // Save / load the baby flag
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
    // Tick — animation states
    // -------------------------------------------------------------------------

    @Override
    public void tick() {
        super.tick();

        // Handle air/breathing mechanics
        this.handleAirSupply();

        if (this.attackAnimationTimer > 0) {
            this.attackAnimationTimer--;
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

    /**
     * Handle air supply for orcas. They slowly drain air when fully submerged,
     * but recover quickly when breathing (head above water).
     */
    private void handleAirSupply() {
        if (this.level().isClientSide()) return;

        var eyeBlockPos = net.minecraft.core.BlockPos.containing(this.getEyePosition(1.0F));
        boolean headAboveWater = this.level().getFluidState(eyeBlockPos).isEmpty();

        if (headAboveWater) {
            // Breathing at surface — restore air quickly
            this.setAir(Math.min(this.getAir() + 4, MAX_AIR));
        } else if (this.isInWater()) {
            // Fully submerged — drain air slowly
            this.setAir(this.getAir() - AIR_DRAIN_UNDERWATER);

            // If desperate for air, start seeking surface
            if (this.getAir() < MAX_AIR * 0.2) {
                this.setDeltaMovement(this.getDeltaMovement().x, 0.15, this.getDeltaMovement().z);
            }
        } else {
            // Out of water — restore air quickly
            this.setAir(Math.min(this.getAir() + 8, MAX_AIR));
        }
    }

    // =========================================================================
    // Inner goals
    // =========================================================================

    // -------------------------------------------------------------------------
    // Schooling / flocking: keeps the pod within ~20 blocks of each other
    // -------------------------------------------------------------------------
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

        @Override
        public boolean canUse() {
            if (this.orca.getTarget() != null) return false;
            this.leader = findNearest();
            return this.leader != null && this.orca.distanceToSqr(this.leader) > this.triggerDistSq;
        }

        @Override
        public boolean canContinueToUse() {
            return this.leader != null
                    && this.leader.isAlive()
                    && this.orca.getTarget() == null
                    && this.orca.distanceToSqr(this.leader) > this.stopDistSq;
        }

        @Override public void start()  { moveTo(); }
        @Override public void tick()   { moveTo(); }
        @Override public void stop()   { this.leader = null; }

        private void moveTo() {
            if (this.leader != null) this.orca.getNavigation().moveTo(this.leader, 1.0D);
        }

        private OrcaEntity findNearest() {
            List<OrcaEntity> nearby = this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class,
                    this.orca.getBoundingBox().inflate(20.0),
                    e -> e != this.orca && e.isAlive()
            );
            if (nearby.isEmpty()) return null;
            return nearby.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(this.orca)))
                    .orElse(null);
        }
    }

    // -------------------------------------------------------------------------
    // Baby follow: calves swim toward the nearest adult in the pod
    // -------------------------------------------------------------------------
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

        @Override
        public boolean canUse() {
            if (!this.orca.isBaby()) return false;
            if (this.orca.getTarget() != null) return false;
            this.parent = findNearestAdult();
            return this.parent != null && this.orca.distanceToSqr(this.parent) > this.triggerDistSq;
        }

        @Override
        public boolean canContinueToUse() {
            return this.orca.isBaby()
                    && this.parent != null
                    && this.parent.isAlive()
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
                    OrcaEntity.class,
                    this.orca.getBoundingBox().inflate(18.0),
                    e -> e != this.orca && e.isAlive() && !e.isBaby()
            );
            if (nearby.isEmpty()) return null;
            return nearby.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(this.orca)))
                    .orElse(null);
        }
    }

    // -------------------------------------------------------------------------
    // Defend baby: adult orcas attack players who come within 8 blocks of a calf
    // (mirrors PolarBear cub-defense behavior)
    // -------------------------------------------------------------------------
    static class DefendBabyGoal extends Goal {

        private static final double BABY_SCAN_RADIUS = 24.0;
        private static final double PLAYER_THREAT_RADIUS = 8.0;

        private final OrcaEntity orca;
        private Player threat;

        DefendBabyGoal(OrcaEntity orca) {
            this.orca = orca;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            // Only adults with no current target scan for threats
            if (this.orca.isBaby()) return false;
            if (this.orca.getTarget() != null) return false;

            // Find nearby calves
            List<OrcaEntity> babies = this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class,
                    this.orca.getBoundingBox().inflate(BABY_SCAN_RADIUS),
                    e -> e.isAlive() && e.isBaby()
            );
            if (babies.isEmpty()) return false;

            // Check if any player is dangerously close to any calf
            for (OrcaEntity baby : babies) {
                List<Player> nearPlayers = this.orca.level().getEntitiesOfClass(
                        Player.class,
                        baby.getBoundingBox().inflate(PLAYER_THREAT_RADIUS),
                        p -> !p.isSpectator() && !p.isCreative()
                );
                if (!nearPlayers.isEmpty()) {
                    this.threat = nearPlayers.get(0);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return this.threat != null && this.threat.isAlive()
                    && !this.threat.isSpectator() && !this.threat.isCreative();
        }

        @Override
        public void start() {
            this.orca.setTarget(this.threat);
            // Alert the rest of the pod
            this.orca.level().getEntitiesOfClass(
                    OrcaEntity.class,
                    this.orca.getBoundingBox().inflate(BABY_SCAN_RADIUS),
                    e -> e != this.orca && e.isAlive() && !e.isBaby() && e.getTarget() == null
            ).forEach(pod -> pod.setTarget(this.threat));
        }

        @Override
        public void stop() {
            this.threat = null;
        }

        @Override
        public void tick() {
            if (this.threat != null) this.orca.setTarget(this.threat);
        }
    }
}

