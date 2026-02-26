package net.braeden.waterlogged.entity;

import net.braeden.waterlogged.entity.ai.WormTemptGoal;
import net.braeden.waterlogged.entity.util.CrabVariant;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.braeden.waterlogged.tags.WaterloggedBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.AnimationState;

public class CrabEntity extends Animal implements Bucketable {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_DANCING =
            SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);

    private static final int JUKEBOX_RANGE = 6;
    private static final int JUKEBOX_CHECK_INTERVAL = 20;
    private int jukeboxScanTimer = 0;

    public final AnimationState movingAnimationState = new AnimationState();
    public final AnimationState rotatedAnimationState = new AnimationState();
    public final AnimationState forwardsAnimationState = new AnimationState();
    public final AnimationState dancingAnimationState = new AnimationState();

    private boolean fromBucket = false;

    public CrabEntity(EntityType<? extends CrabEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, CrabVariant.DUNGENESS.getId());
        builder.define(DATA_DANCING, false);
    }

    public boolean isDancing() {
        return this.getEntityData().get(DATA_DANCING);
    }

    public void setDancing(boolean dancing) {
        this.getEntityData().set(DATA_DANCING, dancing);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(2, new WormTemptGoal(this, 1.0));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 8.0);
    }

    public CrabVariant getVariant() {
        return CrabVariant.byId(this.getEntityData().get(VARIANT));
    }

    public void setVariant(CrabVariant variant) {
        this.getEntityData().set(VARIANT, variant.getId());
    }

    @Override public boolean fromBucket() { return this.fromBucket; }
    @Override public void setFromBucket(boolean b) { this.fromBucket = b; }
    @Override public SoundEvent getPickupSound() { return SoundEvents.BUCKET_FILL_FISH; }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(WaterloggedItems.CRAB_BUCKET);
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, tag -> {
            tag.putFloat("Health", this.getHealth());
            tag.putInt("Variant", this.getVariant().getId());
            tag.putInt("Age", this.getAge());
        });
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        this.setHealth(tag.getFloatOr("Health", this.getMaxHealth()));
        this.setVariant(CrabVariant.byId(tag.getIntOr("Variant", CrabVariant.DUNGENESS.getId())));
        this.setAge(tag.getIntOr("Age", 0));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(Items.WATER_BUCKET)) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        // Worm feeding → vanilla Animal.mobInteract handles love mode since isFood() returns true for worms
        return super.mobInteract(player, hand);
    }

    public static CrabVariant chooseVariant(ServerLevelAccessor world, BlockPos pos) {
        var biome = world.getBiome(pos);
        if (biome.is(WaterloggedBiomeTags.BLUE_CLAW_CRAB_BIOMES)) return CrabVariant.BLUE_CLAW;
        if (biome.is(WaterloggedBiomeTags.GHOST_CRAB_BIOMES)) {
            // Mojang crab is a secret rare variant within ghost crab territory
            if (world.getRandom().nextFloat() < 0.05f) return CrabVariant.MOJANG;
            return CrabVariant.GHOST;
        }
        return CrabVariant.DUNGENESS;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                                   EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        this.setVariant(chooseVariant(world, this.blockPosition()));
        return data;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getVariant().getId());
        output.putBoolean("FromBucket", this.fromBucket);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(CrabVariant.byId(input.getIntOr("Variant", CrabVariant.DUNGENESS.getId())));
        this.fromBucket = input.getBooleanOr("FromBucket", false);
    }

    public static boolean canSpawn(EntityType<CrabEntity> type, LevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        CrabEntity baby = WaterloggedEntities.CRAB.create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            CrabVariant v1 = this.getVariant();
            CrabVariant v2 = (partner instanceof CrabEntity other) ? other.getVariant() : v1;
            baby.setVariant(inheritVariant(level.getRandom(), v1, v2));
        }
        return baby;
    }

    /** Pick one parent's variant, with a 5% chance of mutating to any variant. */
    private static CrabVariant inheritVariant(RandomSource rng, CrabVariant v1, CrabVariant v2) {
        if (rng.nextFloat() < 0.05f) {
            CrabVariant[] all = CrabVariant.values();
            return all[rng.nextInt(all.length)];
        }
        return rng.nextBoolean() ? v1 : v2;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == WaterloggedItems.WORM;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (++jukeboxScanTimer >= JUKEBOX_CHECK_INTERVAL) {
                jukeboxScanTimer = 0;
                boolean found = false;
                BlockPos center = this.blockPosition();
                for (BlockPos p : BlockPos.betweenClosed(
                        center.offset(-JUKEBOX_RANGE, -JUKEBOX_RANGE, -JUKEBOX_RANGE),
                        center.offset( JUKEBOX_RANGE,  JUKEBOX_RANGE,  JUKEBOX_RANGE))) {
                    if (this.level().getBlockEntity(p) instanceof JukeboxBlockEntity jukebox
                            && jukebox.getSongPlayer().isPlaying()) {
                        found = true;
                        break;
                    }
                }
                this.setDancing(found);
            }
        }

        if (this.level().isClientSide()) {
            if (this.isDancing()) {
                this.dancingAnimationState.startIfStopped(this.tickCount);
                this.movingAnimationState.stop();
            } else {
                this.dancingAnimationState.stop();
                boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
                if (moving) {
                    this.movingAnimationState.startIfStopped(this.tickCount);
                } else {
                    this.movingAnimationState.stop();
                }
            }
            this.rotatedAnimationState.startIfStopped(this.tickCount);
            this.forwardsAnimationState.stop();
        }
    }
}
