package net.braeden.waterlogged.entity.ai;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedMemoryModuleTypes {

    public static final MemoryModuleType<Unit> SOARING_COOLDOWN = register("soaring_cooldown", Unit.CODEC);
    public static final MemoryModuleType<Unit> CAN_TRADE = register("can_trade", Unit.CODEC);
    public static final MemoryModuleType<Unit> HAS_TRADED = register("has_traded", Unit.CODEC);

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }
}
