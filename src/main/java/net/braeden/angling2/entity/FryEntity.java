package net.braeden.angling2.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.entity.AnimationState;

public class FryEntity extends AbstractFish {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();

    private String parentTypeId = "";
    private int growAge = 0;
    private static final int GROW_TICKS = 6000;

    /** Packed TropicalFish variant to apply on grow. -1 = not a tropical fish. */
    private int tropicalFishVariant = -1;

    public FryEntity(EntityType<? extends FryEntity> type, Level level) {
        super(type, level);
    }

    public void setParentTypeId(String typeId) {
        this.parentTypeId = typeId;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setTropicalFishVariant(int packed) {
        this.tropicalFishVariant = packed;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0, 40));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5);
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
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("ParentType", parentTypeId);
        output.putInt("GrowAge", growAge);
        output.putInt("TropicalVariant", tropicalFishVariant);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        parentTypeId = input.getStringOr("ParentType", "");
        growAge = input.getIntOr("GrowAge", 0);
        tropicalFishVariant = input.getIntOr("TropicalVariant", -1);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && !parentTypeId.isEmpty()) {
            growAge++;
            if (growAge >= GROW_TICKS) {
                growIntoAdult();
            }
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

    private void growIntoAdult() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        Identifier id = Identifier.parse(parentTypeId);
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(id);
        if (type == null || type == EntityType.PIG) return;

        Entity adult = type.create(serverLevel, EntitySpawnReason.BREEDING);
        if (adult != null) {
            adult.setPos(this.getX(), this.getY(), this.getZ());
            if (adult instanceof TropicalFish tf && tropicalFishVariant >= 0) {
                tf.setPackedVariant(tropicalFishVariant);
            }
            serverLevel.addFreshEntity(adult);
        }
        this.discard();
    }
}
