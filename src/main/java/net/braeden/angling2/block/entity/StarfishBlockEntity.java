package net.braeden.angling2.block.entity;

import net.braeden.angling2.entity.AnglingEntities;
import net.braeden.angling2.entity.util.StarfishColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class StarfishBlockEntity extends BlockEntity {

    private StarfishColor color = StarfishColor.RED;

    public StarfishBlockEntity(BlockPos pos, BlockState state) {
        super(AnglingEntities.STARFISH, pos, state);
    }

    public StarfishColor getColor() {
        return color;
    }

    public void setColor(StarfishColor color) {
        this.color = color;
        setChanged();
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
}
