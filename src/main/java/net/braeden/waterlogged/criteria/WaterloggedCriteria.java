package net.braeden.waterlogged.criteria;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedCriteria {
    public static final TradedWithPelicanCriterion TRADED_WITH_PELICAN = Registry.register(
            BuiltInRegistries.TRIGGER_TYPES,
            Identifier.fromNamespaceAndPath(MOD_ID, "traded_with_pelican"),
            new TradedWithPelicanCriterion()
    );

    public static void init() {}
}
