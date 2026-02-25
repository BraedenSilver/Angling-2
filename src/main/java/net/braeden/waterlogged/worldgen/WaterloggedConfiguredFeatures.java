package net.braeden.waterlogged.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> DUCKWEED    = key("duckweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SARGASSUM   = key("sargassum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PAPYRUS     = key("papyrus");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLAM        = key("clam");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OYSTERS     = key("oysters");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARFISH    = key("starfish");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANEMONE     = key("anemone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALGAE       = key("algae");
    public static final ResourceKey<ConfiguredFeature<?, ?>> URCHIN            = key("urchin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HYDROTHERMAL_VENT = key("hydrothermal_vent");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void init() {}
}
