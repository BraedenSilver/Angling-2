package net.braeden.waterlogged.entity;

import net.braeden.waterlogged.criteria.WaterloggedCriteria;
import net.braeden.waterlogged.sound.WaterloggedSounds;
import net.braeden.waterlogged.tags.WaterloggedEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class PelicanEntity extends Animal implements FlyingAnimal {

    private static final EntityDataAccessor<String> HELD_ENTITY_ID =
            SynchedEntityData.defineId(PelicanEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> TRADED =
            SynchedEntityData.defineId(PelicanEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState divingAnimationState = new AnimationState();
    public final AnimationState flappingAnimationState = new AnimationState();
    public final AnimationState beakOpenedAnimationState = new AnimationState();

    private int despawnDelay = 0;
    private int flyAwayTimer = -1;
    private int eatingTimer = 0; // client-side countdown, set via entity event
    boolean diving = false;       // server-side: disables buoyancy while SeekAndCatchFishGoal dives

    public PelicanEntity(EntityType<? extends PelicanEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HELD_ENTITY_ID, "");
        builder.define(TRADED, false);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new FollowNearestPlayerGoal(this));
        this.goalSelector.addGoal(3, new SeekAndCatchFishGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround() && !this.isInWater();
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState blockState, BlockPos pos) {
        // No fall damage for pelicans
    }

    // --- Held entity methods ---

    public String getHeldEntityId() {
        return this.getEntityData().get(HELD_ENTITY_ID);
    }

    public void setHeldEntityId(String id) {
        this.getEntityData().set(HELD_ENTITY_ID, id);
    }

    public boolean hasHeldEntity() {
        // Check for actual passenger first, fall back to string ID
        return !getPassengers().isEmpty() || !getHeldEntityId().isEmpty();
    }

    /** Get the actual mob riding in the beak. */
    @Nullable
    public Entity getHeldPassenger() {
        List<Entity> passengers = getPassengers();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    public boolean hasTraded() {
        return this.getEntityData().get(TRADED);
    }

    public void setTraded(boolean traded) {
        this.getEntityData().set(TRADED, traded);
    }

    public void setDespawnDelay(int delay) {
        this.despawnDelay = delay;
    }

    // --- Choose random held entity from tags ---

    public void chooseRandomHeldEntity(ServerLevel level) {
        boolean useCommon = level.getRandom().nextFloat() < 0.8f;
        var tag = useCommon ? WaterloggedEntityTypeTags.COMMON_ENTITIES_IN_PELICAN_BEAK
                : WaterloggedEntityTypeTags.UNCOMMON_ENTITIES_IN_PELICAN_BEAK;

        List<EntityType<?>> candidates = BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> entityType.is(tag))
                .collect(Collectors.toList());

        if (!candidates.isEmpty()) {
            EntityType<?> chosen = candidates.get(level.getRandom().nextInt(candidates.size()));
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(chosen);
            setHeldEntityId(id.toString());
            spawnAndMountHeldEntity(level, chosen);
        }
    }

    /** Actually spawn the held mob and mount it as a passenger in the beak. */
    private void spawnAndMountHeldEntity(ServerLevel level, EntityType<?> type) {
        Entity entity = type.create(level, EntitySpawnReason.MOB_SUMMONED);
        if (entity != null) {
            entity.snapTo(this.getX(), this.getY(), this.getZ());
            // Make the held entity passive: no AI, invulnerable, silent, scaled down
            if (entity instanceof Mob mob) {
                mob.setNoAi(true);
            }
            if (entity instanceof LivingEntity living) {
                var scaleAttr = living.getAttribute(Attributes.SCALE);
                if (scaleAttr != null) {
                    scaleAttr.setBaseValue(0.5);
                }
            }
            entity.setInvulnerable(true);
            entity.setSilent(true);
            level.addFreshEntity(entity);
            entity.startRiding(this, true, true);
        }
    }

    /** Catch an existing world entity (from fish-seeking goal) and mount it in the beak. */
    public void catchExistingFish(Mob entity) {
        if (!(this.level() instanceof ServerLevel)) return;
        if (!getPassengers().isEmpty()) return;
        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }
        if (entity instanceof LivingEntity living) {
            var scaleAttr = living.getAttribute(Attributes.SCALE);
            if (scaleAttr != null) {
                scaleAttr.setBaseValue(0.5);
            }
        }
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.startRiding(this, true, true);
        Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (entityId != null) {
            setHeldEntityId(entityId.toString());
        }
    }

    // --- Release the held entity ---

    private void releaseHeldEntity(ServerLevel level) {
        Entity passenger = getHeldPassenger();
        if (passenger != null) {
            // Restore normal behavior before dismounting
            if (passenger instanceof Mob mob) {
                mob.setNoAi(false);
            }
            if (passenger instanceof LivingEntity living) {
                var scaleAttr = living.getAttribute(Attributes.SCALE);
                if (scaleAttr != null) {
                    scaleAttr.setBaseValue(1.0);
                }
            }
            passenger.setInvulnerable(false);
            passenger.setSilent(false);
            passenger.stopRiding();
            // Position it at the pelican's feet
            passenger.snapTo(this.getX(), this.getY(), this.getZ());
        }
        setHeldEntityId("");
    }

    // --- Passenger positioning: place rider in the beak ---

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
        // +Z is forward (the direction the entity faces) in entity attachment space
        float yRotRad = -this.getYRot() * Mth.DEG_TO_RAD;
        // When flying, the head dips down and stretches forward in the animation
        double yOffset = isFlying() ? 0.6 : 1.1;
        double zOffset = isFlying() ? 1.1 : 0.6;
        return new Vec3(0.0, yOffset * f, zOffset * f).yRot(yRotRad);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return this.getPassengers().isEmpty();
    }

    // --- Trading interaction ---

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide() && hasHeldEntity() && !hasTraded() && isFishItem(stack)) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            ItemStack feedCopy = stack.copy(); // save before shrink for particles

            // Release the held entity
            releaseHeldEntity(serverLevel);

            // Consume the fish from the player
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            // Mark as traded
            setTraded(true);

            // Play sound
            this.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.0f);

            // Show item particles at beak level to visualize eating
            serverLevel.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, feedCopy),
                this.getX(), this.getY() + 1.2, this.getZ(),
                10, 0.2, 0.1, 0.2, 0.05);

            // Trigger advancement
            if (player instanceof ServerPlayer sp) {
                WaterloggedCriteria.TRADED_WITH_PELICAN.trigger(sp);
            }

            // Start fly-away timer
            flyAwayTimer = 60; // 3 seconds before flying off

            // Eating animation (beak stays open briefly)
            this.level().broadcastEntityEvent(this, (byte) 64);

            // Heart particles
            this.level().broadcastEntityEvent(this, (byte) 7);

            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private boolean isFishItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH)
                || stack.is(Items.PUFFERFISH) || stack.is(Items.COOKED_COD) || stack.is(Items.COOKED_SALMON)
                || isModdedFishItem(stack);
    }

    private boolean isModdedFishItem(ItemStack stack) {
        var item = stack.getItem();
        return item == net.braeden.waterlogged.item.WaterloggedItems.SUNFISH
                || item == net.braeden.waterlogged.item.WaterloggedItems.RAW_MAHI_MAHI
                || item == net.braeden.waterlogged.item.WaterloggedItems.RAW_CATFISH
                || item == net.braeden.waterlogged.item.WaterloggedItems.RAW_BUBBLE_EYE
                || item == net.braeden.waterlogged.item.WaterloggedItems.RAW_ANGLERFISH;
    }

    // --- Sounds ---

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return WaterloggedSounds.ENTITY_PELICAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return WaterloggedSounds.ENTITY_PELICAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WaterloggedSounds.ENTITY_PELICAN_DEATH;
    }

    // --- Finalize spawn: choose held entity ---

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                                   EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        if (world instanceof ServerLevel serverLevel) {
            // 80% chance to spawn with a passenger; 20% spawns empty and will seek a fish
            if (this.random.nextFloat() >= 0.2f) {
                chooseRandomHeldEntity(serverLevel);
            }
        }
        return data;
    }

    // --- Save/Load ---

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("HeldEntityId", getHeldEntityId());
        output.putBoolean("Traded", hasTraded());
        output.putInt("DespawnDelay", despawnDelay);
        output.putInt("FlyAwayTimer", flyAwayTimer);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setHeldEntityId(input.getStringOr("HeldEntityId", ""));
        setTraded(input.getBooleanOr("Traded", false));
        despawnDelay = input.getIntOr("DespawnDelay", 0);
        flyAwayTimer = input.getIntOr("FlyAwayTimer", -1);
    }

    // --- Breeding disabled ---

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    // --- Tick behavior ---

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            // Migration: if we have a saved entity ID but no passenger, spawn it
            String heldId = getHeldEntityId();
            if (!heldId.isEmpty() && getPassengers().isEmpty()) {
                Identifier entityId = Identifier.tryParse(heldId);
                if (entityId != null) {
                    ServerLevel serverLevel = (ServerLevel) this.level();
                    EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(entityId);
                    spawnAndMountHeldEntity(serverLevel, type);
                }
            }

            // Despawn timer (like wandering trader)
            if (despawnDelay > 0) {
                despawnDelay--;
                if (despawnDelay <= 0 && !this.isRemoved()) {
                    // Discard any held passenger too
                    Entity passenger = getHeldPassenger();
                    if (passenger != null) {
                        passenger.stopRiding();
                        passenger.discard();
                    }
                    this.discard();
                    return;
                }
            }

            // Fly away after trading
            if (flyAwayTimer > 0) {
                flyAwayTimer--;
                if (flyAwayTimer <= 0) {
                    // Start flying up and away
                    this.setNoGravity(true);
                    this.despawnDelay = 200; // Despawn after 10 seconds of flying
                }
            }

            // If flying away (no gravity set after trade), move upward
            if (this.isNoGravity() && hasTraded()) {
                Vec3 motion = this.getDeltaMovement();
                this.setDeltaMovement(motion.x * 0.9, 0.15, motion.z * 0.9);
            }

            // Keep pelican afloat when in water (buoyancy), but not while actively diving
            if (this.isInWater() && !diving) {
                Vec3 motion = this.getDeltaMovement();
                if (motion.y < 0) {
                    this.setDeltaMovement(motion.x, Math.max(motion.y, -0.02), motion.z);
                }
                if (this.isUnderWater()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.08, 0));
                }
            }
        }

        // Client-side animations
        if (this.level().isClientSide()) {
            boolean inWater = this.isInWater();
            boolean onGround = this.onGround();
            boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
            boolean flying = !onGround && !inWater;
            boolean divingDown = flying && this.getDeltaMovement().y < -0.2;

            if (inWater) {
                this.swimmingAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.divingAnimationState.stop();
            } else if (divingDown) {
                this.divingAnimationState.startIfStopped(this.tickCount);
                this.flyingAnimationState.stop();
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.swimmingAnimationState.stop();
            } else if (flying) {
                this.flyingAnimationState.startIfStopped(this.tickCount);
                this.divingAnimationState.stop();
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.swimmingAnimationState.stop();
            } else if (moving) {
                this.walkingAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.flyingAnimationState.stop();
                this.divingAnimationState.stop();
                this.swimmingAnimationState.stop();
            } else {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.walkingAnimationState.stop();
                this.flyingAnimationState.stop();
                this.divingAnimationState.stop();
                this.swimmingAnimationState.stop();
            }

            // Beak open when holding a passenger (not yet traded) or briefly after eating
            boolean showBeak = (!getPassengers().isEmpty() && !hasTraded()) || eatingTimer > 0;
            if (showBeak) {
                this.beakOpenedAnimationState.startIfStopped(this.tickCount);
                if (eatingTimer > 0) eatingTimer--;
            } else {
                this.beakOpenedAnimationState.stop();
            }

            this.flappingAnimationState.stop();
        }
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
        } else if (id == 64) {
            // Eating animation: keep beak open for 2 seconds
            this.eatingTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    // Don't despawn naturally — controlled by our despawnDelay system
    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return despawnDelay > 0 || super.requiresCustomPersistence();
    }

    // --- AI Goals ---

    /** Makes the pelican persistently follow the nearest player until it has traded. */
    private static class FollowNearestPlayerGoal extends Goal {
        private final PelicanEntity pelican;
        private Player targetPlayer;
        private static final double FOLLOW_RANGE = 32.0;
        private static final double STOP_DISTANCE = 4.0;

        FollowNearestPlayerGoal(PelicanEntity pelican) {
            this.pelican = pelican;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (pelican.hasTraded()) return false;
            if (!pelican.hasHeldEntity()) return false; // only follow when carrying a fish
            Player player = pelican.level().getNearestPlayer(pelican, FOLLOW_RANGE);
            if (player == null) return false;
            targetPlayer = player;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (pelican.hasTraded()) return false;
            if (!pelican.hasHeldEntity()) return false;
            if (targetPlayer == null || !targetPlayer.isAlive()) return false;
            return pelican.distanceTo(targetPlayer) < FOLLOW_RANGE * 1.5;
        }

        @Override
        public void tick() {
            if (targetPlayer == null) return;
            pelican.getLookControl().setLookAt(targetPlayer, 10.0f, pelican.getMaxHeadXRot());
            double dist = pelican.distanceTo(targetPlayer);
            if (dist > STOP_DISTANCE) {
                pelican.getMoveControl().setWantedPosition(
                    targetPlayer.getX(), targetPlayer.getY() + 1.5, targetPlayer.getZ(), 1.2);
            }
        }

        @Override
        public void stop() {
            targetPlayer = null;
        }
    }

    /** When the pelican has no passenger, seeks out a nearby huntable fish and catches it. */
    private static class SeekAndCatchFishGoal extends Goal {
        private final PelicanEntity pelican;
        private Mob targetFish;
        private static final double SEARCH_RANGE = 20.0;
        private static final double CATCH_RANGE = 1.5;

        SeekAndCatchFishGoal(PelicanEntity pelican) {
            this.pelican = pelican;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (pelican.hasHeldEntity() || pelican.hasTraded()) return false;
            targetFish = findNearestHuntTarget();
            return targetFish != null;
        }

        @Override
        public boolean canContinueToUse() {
            if (pelican.hasHeldEntity() || pelican.hasTraded()) return false;
            if (targetFish == null || targetFish.isRemoved() || targetFish.isPassenger()) return false;
            return pelican.distanceTo(targetFish) < SEARCH_RANGE * 1.5;
        }

        @Override
        public void tick() {
            if (targetFish == null) return;
            pelican.getLookControl().setLookAt(targetFish, 30.0f, 30.0f);

            double horizDist = Math.sqrt(
                Math.pow(targetFish.getX() - pelican.getX(), 2) +
                Math.pow(targetFish.getZ() - pelican.getZ(), 2));

            if (horizDist > 4.0 || pelican.getY() <= targetFish.getY() + 1.0) {
                // Approach phase: fly toward a point above the fish
                pelican.diving = false;
                pelican.getMoveControl().setWantedPosition(
                    targetFish.getX(), targetFish.getY() + 5, targetFish.getZ(), 1.5);
            } else {
                // Dive phase: bypass all navigation and plunge directly at the fish
                pelican.diving = true;
                Vec3 toFish = targetFish.position().subtract(pelican.position()).normalize().scale(0.5);
                pelican.setDeltaMovement(toFish);
            }

            if (pelican.distanceTo(targetFish) < CATCH_RANGE) {
                pelican.catchExistingFish(targetFish);
                stop();
            }
        }

        @Override
        public void stop() {
            targetFish = null;
            pelican.diving = false;
        }

        private Mob findNearestHuntTarget() {
            List<Mob> candidates = pelican.level().getEntitiesOfClass(
                Mob.class,
                pelican.getBoundingBox().inflate(SEARCH_RANGE),
                e -> e.getType().is(WaterloggedEntityTypeTags.HUNTED_BY_PELICAN)
                     && !e.isPassenger() && !e.isRemoved()
            );
            if (candidates.isEmpty()) return null;
            Mob closest = null;
            double closestDistSq = Double.MAX_VALUE;
            for (Mob mob : candidates) {
                double distSq = mob.distanceToSqr(pelican);
                if (distSq < closestDistSq) {
                    closestDistSq = distSq;
                    closest = mob;
                }
            }
            return closest;
        }
    }
}
