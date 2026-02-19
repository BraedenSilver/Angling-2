package net.braeden.angling2.entity;

import net.braeden.angling2.entity.ai.WormBreeder;
import net.braeden.angling2.entity.ai.WormBreedGoal;
import net.braeden.angling2.entity.util.SeaSlugColor;
import net.braeden.angling2.entity.util.SeaSlugPattern;
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
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.AnimationState;

public class SeaSlugEntity extends WaterAnimal implements WormBreeder {
    private static final EntityDataAccessor<Integer> COLOR =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN =
            SynchedEntityData.defineId(SeaSlugEntity.class, EntityDataSerializers.INT);

    public final AnimationState ambientAnimationState = new AnimationState();
    public final AnimationState movingAnimationState = new AnimationState();

    private int wormBredTimer = 0;

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
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 0.6, 40));
        this.goalSelector.addGoal(1, new WormBreedGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
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

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                                   EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SpawnGroupData data = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        var rng = world.getRandom();
        SeaSlugColor[] colors = SeaSlugColor.values();
        SeaSlugPattern[] patterns = SeaSlugPattern.values();
        this.setColor(colors[rng.nextInt(colors.length)]);
        this.setPattern(patterns[rng.nextInt(patterns.length)]);
        return data;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Color", this.getColor().getId());
        output.putInt("Pattern", this.getPattern().getId());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setColor(SeaSlugColor.byId(input.getIntOr("Color", SeaSlugColor.RED.getId())));
        this.setPattern(SeaSlugPattern.byId(input.getIntOr("Pattern", SeaSlugPattern.PLAIN.getId())));
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
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && wormBredTimer > 0) {
            wormBredTimer--;
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
