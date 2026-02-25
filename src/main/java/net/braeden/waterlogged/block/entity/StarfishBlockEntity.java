package net.braeden.waterlogged.block.entity;

import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.entity.util.StarfishColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class StarfishBlockEntity extends BlockEntity {

    private StarfishColor color = StarfishColor.RED;

    public StarfishBlockEntity(BlockPos pos, BlockState state) {
        super(WaterloggedEntities.STARFISH, pos, state);
    }

    public StarfishColor getColor() {
        return color;
    }

    public void setColor(StarfishColor color) {
        this.color = color;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        // CUSTOM_DATA: stores color ID for restoration when the item is placed
        CompoundTag tag = new CompoundTag();
        tag.putInt("Color", color.getId());
        components.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        // CUSTOM_MODEL_DATA: stores ARGB color for the item model tint source
        components.set(DataComponents.CUSTOM_MODEL_DATA,
                new CustomModelData(List.of(), List.of(), List.of(), List.of(color.getArgb())));
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        CustomData data = components.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            color = StarfishColor.byId(data.copyTag().getIntOr("Color", 0));
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("Color", color.getId());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        color = StarfishColor.byId(input.getIntOr("Color", 0));
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
