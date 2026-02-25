package net.braeden.waterlogged.block.entity;

import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.entity.FryEntity;
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
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RoeBlockEntity extends BlockEntity {

    private String parentTypeId = "";
    private int age = 0;
    // Packed variant ints for tropical fish parent colours; -1 = not a tropical fish
    private int parent1Variant = -1;
    private int parent2Variant = -1;
    /** Bottom layer ARGB tint (base colour). -1 = no tint. */
    private int tintColor0 = -1;
    /** Top layer ARGB tint (pattern/accent colour). -1 = no tint. */
    private int tintColor1 = -1;
    /** Generic parent variant ID for species like Sunfish. -1 = not set. */
    private int parentVariantId = -1;

    private static final int HATCH_TICKS = 6000;
    private static final int MIN_FRY = 2;
    private static final int MAX_FRY = 4;

    public RoeBlockEntity(BlockPos pos, BlockState state) {
        super(WaterloggedEntities.ROE, pos, state);
    }

    public void setParentTypeId(String typeId) {
        this.parentTypeId = typeId;
        this.setChanged();
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    /** Store packed TropicalFish variants from both parents for colour mixing. */
    public void setParentVariants(int v1, int v2) {
        this.parent1Variant = v1;
        this.parent2Variant = v2;
        // Bottom layer: average base colour from both parents
        int c1b = TropicalFish.getBaseColor(v1).getTextColor();
        int c2b = TropicalFish.getBaseColor(v2).getTextColor();
        int rb = ((c1b >> 16 & 0xFF) + (c2b >> 16 & 0xFF)) / 2;
        int gb = ((c1b >> 8 & 0xFF) + (c2b >> 8 & 0xFF)) / 2;
        int bb = ((c1b & 0xFF) + (c2b & 0xFF)) / 2;
        this.tintColor0 = 0xFF000000 | (rb << 16) | (gb << 8) | bb;
        // Top layer: average pattern colour from both parents
        int c1p = TropicalFish.getPatternColor(v1).getTextColor();
        int c2p = TropicalFish.getPatternColor(v2).getTextColor();
        int rp = ((c1p >> 16 & 0xFF) + (c2p >> 16 & 0xFF)) / 2;
        int gp = ((c1p >> 8 & 0xFF) + (c2p >> 8 & 0xFF)) / 2;
        int bp = ((c1p & 0xFF) + (c2p & 0xFF)) / 2;
        this.tintColor1 = 0xFF000000 | (rp << 16) | (gp << 8) | bp;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getParent1Variant() { return parent1Variant; }
    public int getParent2Variant() { return parent2Variant; }

    /** Set a single ARGB tint for species without two-colour variants (both layers get the same colour). */
    public void setTintArgb(int argb) {
        this.tintColor0 = argb;
        this.tintColor1 = argb;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getTintArgb() { return tintColor0; }

    public void setParentVariantId(int id) {
        this.parentVariantId = id;
        this.setChanged();
    }

    public int getParentVariantId() { return parentVariantId; }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RoeBlockEntity be) {
        if (level.isClientSide()) return;
        be.age++;
        if (be.age % 100 == 0) be.setChanged();
        if (be.age >= HATCH_TICKS) {
            hatch((ServerLevel) level, pos, be);
        }
    }

    private static void hatch(ServerLevel level, BlockPos pos, RoeBlockEntity be) {
        level.removeBlock(pos, false);

        int count = MIN_FRY + level.getRandom().nextInt(MAX_FRY - MIN_FRY + 1);
        for (int i = 0; i < count; i++) {
            FryEntity fry = new FryEntity(WaterloggedEntities.FRY, level);
            if (!be.parentTypeId.isEmpty()) {
                fry.setParentTypeId(be.parentTypeId);
            }
            if (be.parent1Variant >= 0 && be.parent2Variant >= 0) {
                fry.setTropicalFishVariant(mixVariants(level.getRandom(), be.parent1Variant, be.parent2Variant));
            }
            if (be.tintColor0 != -1) {
                fry.setTintArgb(be.tintColor0);
            }
            if (be.parentVariantId != -1) {
                fry.setParentVariantId(be.parentVariantId);
            }
            fry.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            level.addFreshEntity(fry);
        }
    }

    /** Randomly pick each colour/pattern component from one of the two parents. */
    private static int mixVariants(RandomSource rng, int v1, int v2) {
        TropicalFish.Pattern pattern = rng.nextBoolean() ? TropicalFish.getPattern(v1) : TropicalFish.getPattern(v2);
        net.minecraft.world.item.DyeColor base = rng.nextBoolean() ? TropicalFish.getBaseColor(v1) : TropicalFish.getBaseColor(v2);
        net.minecraft.world.item.DyeColor patternColor = rng.nextBoolean() ? TropicalFish.getPatternColor(v1) : TropicalFish.getPatternColor(v2);
        return new TropicalFish.Variant(pattern, base, patternColor).getPackedId();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        // CUSTOM_DATA: stores all parent data for restoration when the item is placed
        CompoundTag tag = new CompoundTag();
        tag.putInt("TintColor0", tintColor0);
        tag.putInt("TintColor1", tintColor1);
        tag.putString("ParentType", parentTypeId);
        tag.putInt("Parent1Variant", parent1Variant);
        tag.putInt("Parent2Variant", parent2Variant);
        tag.putInt("ParentVariantId", parentVariantId);
        components.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        // CUSTOM_MODEL_DATA: two colours for bottom (colors[0]) and top (colors[1]) layers
        if (tintColor0 != -1) {
            int c1 = tintColor0;
            int c2 = (tintColor1 != -1) ? tintColor1 : tintColor0;
            components.set(DataComponents.CUSTOM_MODEL_DATA,
                    new CustomModelData(List.of(), List.of(), List.of(), List.of(c1, c2)));
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        CustomData data = components.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            int oldTint = tag.getIntOr("TintArgb", -1);  // backward compat
            tintColor0 = tag.getIntOr("TintColor0", oldTint);
            tintColor1 = tag.getIntOr("TintColor1", tintColor0);
            parentTypeId = tag.getStringOr("ParentType", "");
            parent1Variant = tag.getIntOr("Parent1Variant", -1);
            parent2Variant = tag.getIntOr("Parent2Variant", -1);
            parentVariantId = tag.getIntOr("ParentVariantId", -1);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putString("ParentType", parentTypeId);
        output.putInt("Age", age);
        output.putInt("Parent1Variant", parent1Variant);
        output.putInt("Parent2Variant", parent2Variant);
        output.putInt("TintColor0", tintColor0);
        output.putInt("TintColor1", tintColor1);
        output.putInt("ParentVariantId", parentVariantId);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        parentTypeId = input.getStringOr("ParentType", "");
        age = input.getIntOr("Age", 0);
        parent1Variant = input.getIntOr("Parent1Variant", -1);
        parent2Variant = input.getIntOr("Parent2Variant", -1);
        int oldTint = input.getIntOr("TintArgb", -1);  // backward compat
        tintColor0 = input.getIntOr("TintColor0", oldTint);
        tintColor1 = input.getIntOr("TintColor1", tintColor0);
        parentVariantId = input.getIntOr("ParentVariantId", -1);
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
