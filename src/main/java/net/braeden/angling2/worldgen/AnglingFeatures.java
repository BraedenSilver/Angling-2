package net.braeden.angling2.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static net.braeden.angling2.AnglingMod.MOD_ID;

public class AnglingFeatures {

    public static final Feature<NoneFeatureConfiguration> WATER_ADJACENT_PATCH = Registry.register(
            BuiltInRegistries.FEATURE,
            Identifier.fromNamespaceAndPath(MOD_ID, "water_adjacent_patch"),
            new WaterAdjacentPatchFeature()
    );

    public static final Feature<NoneFeatureConfiguration> PAPYRUS = Registry.register(
            BuiltInRegistries.FEATURE,
            Identifier.fromNamespaceAndPath(MOD_ID, "papyrus"),
            new PapyrusFeature()
    );

    public static void init() {
        // Features registered via static field initializers above
    }
}
