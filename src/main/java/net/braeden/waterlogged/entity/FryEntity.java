package net.braeden.waterlogged.entity;

import net.braeden.waterlogged.item.WaterloggedItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.component.CustomModelData;
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
import net.braeden.waterlogged.entity.util.SunfishVariant;
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

    private static final EntityDataAccessor<Integer> TROPICAL_VARIANT =
            SynchedEntityData.defineId(FryEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> ARGB_COLOR =
            SynchedEntityData.defineId(FryEntity.class, EntityDataSerializers.INT);

    private String parentTypeId = "";
    private int growAge = 0;
    private static final int GROW_TICKS = 6000;

    /** Packed TropicalFish variant to apply on grow. -1 = not a tropical fish. */
    private int tropicalFishVariant = -1;
    /** Generic parent variant ID (e.g. SunfishVariant id). -1 = not set. */
    private int parentVariantId = -1;

    public FryEntity(EntityType<? extends FryEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TROPICAL_VARIANT, -1);
        builder.define(ARGB_COLOR, -1);
    }

    /** Returns the synced packed TropicalFish variant for client-side tinting. -1 = no tint. */
    public int getTropicalVariantForRender() {
        return this.getEntityData().get(TROPICAL_VARIANT);
    }

    /** Returns a direct ARGB tint colour synced from the server. -1 = no override. */
    public int getArgbColorForRender() {
        return this.getEntityData().get(ARGB_COLOR);
    }

    public void setParentTypeId(String typeId) {
        this.parentTypeId = typeId;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setTropicalFishVariant(int packed) {
        this.tropicalFishVariant = packed;
        this.getEntityData().set(TROPICAL_VARIANT, packed);
    }

    /** Set a direct ARGB tint colour (overrides tropical-fish-variant colour derivation). */
    public void setTintArgb(int argb) {
        this.getEntityData().set(ARGB_COLOR, argb);
    }

    /** Set a parent variant ID to inherit on grow (e.g. a SunfishVariant id). */
    public void setParentVariantId(int id) {
        this.parentVariantId = id;
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
        net.minecraft.world.item.ItemStack stack = new net.minecraft.world.item.ItemStack(WaterloggedItems.FRY_BUCKET);
        int argb = this.getEntityData().get(ARGB_COLOR);
        if (argb != -1) {
            stack.set(DataComponents.CUSTOM_MODEL_DATA,
                    new CustomModelData(java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of(argb)));
        }
        return stack;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("ParentType", parentTypeId);
        output.putInt("GrowAge", growAge);
        output.putInt("TropicalVariant", tropicalFishVariant);
        int argb = this.getEntityData().get(ARGB_COLOR);
        if (argb != -1) output.putInt("TintArgb", argb);
        if (parentVariantId != -1) output.putInt("ParentVariantId", parentVariantId);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        parentTypeId = input.getStringOr("ParentType", "");
        growAge = input.getIntOr("GrowAge", 0);
        tropicalFishVariant = input.getIntOr("TropicalVariant", -1);
        this.getEntityData().set(TROPICAL_VARIANT, tropicalFishVariant);
        int argb = input.getIntOr("TintArgb", -1);
        this.getEntityData().set(ARGB_COLOR, argb);
        parentVariantId = input.getIntOr("ParentVariantId", -1);
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
            if (adult instanceof SunfishEntity sf) {
                SunfishVariant[] wild = SunfishVariant.WILD_VARIANTS;
                SunfishVariant base = (parentVariantId >= 0)
                        ? SunfishVariant.byId(parentVariantId)
                        : wild[serverLevel.getRandom().nextInt(wild.length)];
                sf.setVariant(serverLevel.getRandom().nextFloat() < 0.05f
                        ? wild[serverLevel.getRandom().nextInt(wild.length)]
                        : base);
            }
            if (adult instanceof net.minecraft.world.entity.Mob mob) mob.setPersistenceRequired();
            serverLevel.addFreshEntity(adult);
        }
        this.discard();
    }
}
