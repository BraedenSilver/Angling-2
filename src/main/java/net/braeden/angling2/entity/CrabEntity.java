package net.braeden.angling2.entity;

import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.block.RoeBlock;
import net.braeden.angling2.block.entity.RoeBlockEntity;
import net.braeden.angling2.entity.util.CrabVariant;
import net.braeden.angling2.item.AnglingItems;
import net.braeden.angling2.tags.AnglingBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.AnimationState;

public class CrabEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);

    public final AnimationState movingAnimationState = new AnimationState();
    public final AnimationState rotatedAnimationState = new AnimationState();
    public final AnimationState forwardsAnimationState = new AnimationState();

    public CrabEntity(EntityType<? extends CrabEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, CrabVariant.DUNGENESS.getId());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
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

    public static CrabVariant chooseVariant(ServerLevelAccessor world, BlockPos pos) {
        var biome = world.getBiome(pos);
        if (biome.is(AnglingBiomeTags.GHOST_CRAB_BIOMES)) return CrabVariant.GHOST;
        if (biome.is(AnglingBiomeTags.BLUE_CLAW_CRAB_BIOMES)) return CrabVariant.BLUE_CLAW;
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
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(CrabVariant.byId(input.getIntOr("Variant", CrabVariant.DUNGENESS.getId())));
    }

    public static boolean canSpawn(EntityType<CrabEntity> type, LevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == AnglingItems.WORM;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
        // Instead of spawning a child, lay a Roe block
        BlockPos roePos = findRoePos(level, this.blockPosition());
        if (roePos != null) {
            boolean waterlogged = level.getFluidState(roePos).is(Fluids.WATER);
            BlockState roeState = AnglingBlocks.ROE.defaultBlockState()
                    .setValue(RoeBlock.WATERLOGGED, waterlogged);
            level.setBlock(roePos, roeState, 3);
            if (level.getBlockEntity(roePos) instanceof RoeBlockEntity roeEntity) {
                String typeId = BuiltInRegistries.ENTITY_TYPE.getKey(this.getType()).toString();
                roeEntity.setParentTypeId(typeId);
            }
        }
        this.finalizeSpawnChildFromBreeding(level, mate, null);
    }

    private static BlockPos findRoePos(ServerLevel level, BlockPos center) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dy = 0; dy >= -1; dy--) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (canPlaceRoe(level, pos)) return pos;
                }
            }
        }
        return null;
    }

    private static boolean canPlaceRoe(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockState below = level.getBlockState(pos.below());
        return (state.isAir() || state.getFluidState().is(Fluids.WATER))
                && below.isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            boolean moving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4D;
            if (moving) {
                this.movingAnimationState.startIfStopped(this.tickCount);
            } else {
                this.movingAnimationState.stop();
            }
            this.rotatedAnimationState.stop();
            this.forwardsAnimationState.stop();
        }
    }
}
