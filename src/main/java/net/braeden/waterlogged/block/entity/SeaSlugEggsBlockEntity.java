package net.braeden.waterlogged.block.entity;

import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.entity.SeaSlugEntity;
import net.braeden.waterlogged.entity.util.SeaSlugBioluminescence;
import net.braeden.waterlogged.entity.util.SeaSlugColor;
import net.braeden.waterlogged.entity.util.SeaSlugPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;

import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SeaSlugEggsBlockEntity extends BlockEntity {

    private int parent1Color = 0;
    private int parent1Pattern = 0;
    private int parent1Biolum = 0;
    private int parent2Color = 0;
    private int parent2Pattern = 0;
    private int parent2Biolum = 0;
    /** Color ID of the parent that laid this egg cluster, used to tint the block model. */
    private int layingParentColorId = 0;
    private int age = 0;

    private static final int HATCH_TICKS = 6000;
    private static final int MIN_BABIES = 1;
    private static final int MAX_BABIES = 3;
    /** Chance (0.0–1.0) that a trait mutates to a random value instead of copying from a parent. */
    private static final float MUTATION_CHANCE = 0.10f;

    public SeaSlugEggsBlockEntity(BlockPos pos, BlockState state) {
        super(WaterloggedEntities.SEA_SLUG_EGGS, pos, state);
    }

    public void setParents(SeaSlugEntity mob, SeaSlugEntity partner, int layingParentColorId) {
        this.parent1Color   = mob.getColor().getId();
        this.parent1Pattern = mob.getPattern().getId();
        this.parent1Biolum  = mob.getBioluminescence().getId();
        this.parent2Color   = partner.getColor().getId();
        this.parent2Pattern = partner.getPattern().getId();
        this.parent2Biolum  = partner.getBioluminescence().getId();
        this.layingParentColorId = layingParentColorId;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getLayingParentColorId() { return layingParentColorId; }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SeaSlugEggsBlockEntity be) {
        if (level.isClientSide()) return;
        be.age++;
        if (be.age % 100 == 0) be.setChanged();
        if (be.age >= HATCH_TICKS) {
            hatch((ServerLevel) level, pos, be);
        }
    }

    private static void hatch(ServerLevel level, BlockPos pos, SeaSlugEggsBlockEntity be) {
        level.removeBlock(pos, false);
        RandomSource rng = level.getRandom();
        int count = MIN_BABIES + rng.nextInt(MAX_BABIES - MIN_BABIES + 1);
        for (int i = 0; i < count; i++) {
            SeaSlugEntity slug = new SeaSlugEntity(WaterloggedEntities.SEA_SLUG, level);
            slug.setColor(inheritTrait(rng, be.parent1Color, be.parent2Color, SeaSlugColor.values()));
            slug.setPattern(inheritTrait(rng, be.parent1Pattern, be.parent2Pattern, SeaSlugPattern.values()));
            slug.setBioluminescence(inheritTrait(rng, be.parent1Biolum, be.parent2Biolum, SeaSlugBioluminescence.values()));
            slug.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            level.addFreshEntity(slug);
        }
    }

    /**
     * Randomly picks the trait from parent A or parent B.
     * With MUTATION_CHANCE probability the trait mutates to any value in the pool.
     */
    private static <T> T inheritTrait(RandomSource rng, int idA, int idB, T[] values) {
        if (rng.nextFloat() < MUTATION_CHANCE) {
            return values[rng.nextInt(values.length)];
        }
        return rng.nextBoolean() ? values[idA] : values[idB];
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        // CUSTOM_DATA: stores all parent data for restoration when the item is placed
        CompoundTag tag = new CompoundTag();
        tag.putInt("Parent1Color",   parent1Color);
        tag.putInt("Parent1Pattern", parent1Pattern);
        tag.putInt("Parent1Biolum",  parent1Biolum);
        tag.putInt("Parent2Color",   parent2Color);
        tag.putInt("Parent2Pattern", parent2Pattern);
        tag.putInt("Parent2Biolum",  parent2Biolum);
        tag.putInt("LayingParentColor", layingParentColorId);
        components.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        // CUSTOM_MODEL_DATA: stores ARGB color of the laying parent for the item model tint source
        int argb = SeaSlugColor.byId(layingParentColorId).getArgb();
        components.set(DataComponents.CUSTOM_MODEL_DATA,
                new CustomModelData(List.of(), List.of(), List.of(), List.of(argb)));
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        CustomData data = components.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            parent1Color   = tag.getIntOr("Parent1Color",   0);
            parent1Pattern = tag.getIntOr("Parent1Pattern", 0);
            parent1Biolum  = tag.getIntOr("Parent1Biolum",  0);
            parent2Color   = tag.getIntOr("Parent2Color",   0);
            parent2Pattern = tag.getIntOr("Parent2Pattern", 0);
            parent2Biolum  = tag.getIntOr("Parent2Biolum",  0);
            layingParentColorId = tag.getIntOr("LayingParentColor", 0);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("Parent1Color",   parent1Color);
        output.putInt("Parent1Pattern", parent1Pattern);
        output.putInt("Parent1Biolum",  parent1Biolum);
        output.putInt("Parent2Color",   parent2Color);
        output.putInt("Parent2Pattern", parent2Pattern);
        output.putInt("Parent2Biolum",  parent2Biolum);
        output.putInt("LayingParentColor", layingParentColorId);
        output.putInt("Age", age);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        parent1Color   = input.getIntOr("Parent1Color",   0);
        parent1Pattern = input.getIntOr("Parent1Pattern", 0);
        parent1Biolum  = input.getIntOr("Parent1Biolum",  0);
        parent2Color   = input.getIntOr("Parent2Color",   0);
        parent2Pattern = input.getIntOr("Parent2Pattern", 0);
        parent2Biolum  = input.getIntOr("Parent2Biolum",  0);
        layingParentColorId = input.getIntOr("LayingParentColor", 0);
        age = input.getIntOr("Age", 0);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveCustomOnly(provider);
    }
}
