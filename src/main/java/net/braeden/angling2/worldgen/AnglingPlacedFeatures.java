package net.braeden.angling2.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.braeden.angling2.AnglingMod.MOD_ID;

public class AnglingPlacedFeatures {

    public static final ResourceKey<PlacedFeature> DUCKWEED    = key("duckweed");
    public static final ResourceKey<PlacedFeature> SARGASSUM   = key("sargassum");
    public static final ResourceKey<PlacedFeature> PAPYRUS     = key("papyrus");
    public static final ResourceKey<PlacedFeature> CLAM        = key("clam");
    public static final ResourceKey<PlacedFeature> OYSTERS     = key("oysters");
    public static final ResourceKey<PlacedFeature> STARFISH    = key("starfish");
    public static final ResourceKey<PlacedFeature> ANEMONE     = key("anemone");
    public static final ResourceKey<PlacedFeature> ALGAE       = key("algae");
    public static final ResourceKey<PlacedFeature> ALGAE_SWAMP = key("algae_swamp");
    public static final ResourceKey<PlacedFeature> URCHIN      = key("urchin");
    public static final ResourceKey<PlacedFeature> WORMY_DIRT  = key("wormy_dirt");
    public static final ResourceKey<PlacedFeature> WORMY_MUD        = key("wormy_mud");
    public static final ResourceKey<PlacedFeature> HYDROTHERMAL_VENT = key("hydrothermal_vent");

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void init() {}
}
