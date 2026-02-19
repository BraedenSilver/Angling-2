package net.braeden.angling2.datagen;

import net.braeden.angling2.tags.AnglingBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class AnglingBiomeTagProvider extends FabricTagProvider<Biome> {

    // Vanilla biomes
    private static final ResourceKey<Biome> MUSHROOM_FIELDS = mc("mushroom_fields");
    private static final ResourceKey<Biome> SAVANNA = mc("savanna");
    private static final ResourceKey<Biome> SAVANNA_PLATEAU = mc("savanna_plateau");
    private static final ResourceKey<Biome> WINDSWEPT_SAVANNA = mc("windswept_savanna");
    private static final ResourceKey<Biome> SWAMP = mc("swamp");
    private static final ResourceKey<Biome> MANGROVE_SWAMP = mc("mangrove_swamp");
    private static final ResourceKey<Biome> BEACH = mc("beach");
    private static final ResourceKey<Biome> STONY_SHORE = mc("stony_shore");
    private static final ResourceKey<Biome> OLD_GROWTH_SPRUCE_TAIGA = mc("old_growth_spruce_taiga");
    private static final ResourceKey<Biome> OLD_GROWTH_PINE_TAIGA = mc("old_growth_pine_taiga");
    private static final ResourceKey<Biome> OCEAN = mc("ocean");
    private static final ResourceKey<Biome> DEEP_OCEAN = mc("deep_ocean");
    private static final ResourceKey<Biome> COLD_OCEAN = mc("cold_ocean");
    private static final ResourceKey<Biome> DEEP_COLD_OCEAN = mc("deep_cold_ocean");
    private static final ResourceKey<Biome> FROZEN_OCEAN = mc("frozen_ocean");
    private static final ResourceKey<Biome> DEEP_FROZEN_OCEAN = mc("deep_frozen_ocean");
    private static final ResourceKey<Biome> LUKEWARM_OCEAN = mc("lukewarm_ocean");
    private static final ResourceKey<Biome> DEEP_LUKEWARM_OCEAN = mc("deep_lukewarm_ocean");
    private static final ResourceKey<Biome> WARM_OCEAN = mc("warm_ocean");
    private static final ResourceKey<Biome> PLAINS = mc("plains");
    private static final ResourceKey<Biome> SUNFLOWER_PLAINS = mc("sunflower_plains");
    private static final ResourceKey<Biome> FOREST = mc("forest");
    private static final ResourceKey<Biome> BIRCH_FOREST = mc("birch_forest");
    private static final ResourceKey<Biome> OLD_GROWTH_BIRCH_FOREST = mc("old_growth_birch_forest");
    private static final ResourceKey<Biome> DARK_FOREST = mc("dark_forest");
    private static final ResourceKey<Biome> TAIGA = mc("taiga");
    private static final ResourceKey<Biome> SNOWY_TAIGA = mc("snowy_taiga");
    private static final ResourceKey<Biome> MEADOW = mc("meadow");
    private static final ResourceKey<Biome> DESERT = mc("desert");

    public AnglingBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Crab sub-variant biomes
        builder(AnglingBiomeTags.DUNGENESS_CRAB_BIOMES).add(BEACH);
        builder(AnglingBiomeTags.GHOST_CRAB_BIOMES).add(MANGROVE_SWAMP);
        builder(AnglingBiomeTags.BLUE_CLAW_CRAB_BIOMES).add(STONY_SHORE);

        // crab_spawn_in references the sub-variant tags
        builder(AnglingBiomeTags.CRAB_SPAWN_IN)
                .forceAddTag(AnglingBiomeTags.DUNGENESS_CRAB_BIOMES)
                .forceAddTag(AnglingBiomeTags.GHOST_CRAB_BIOMES)
                .forceAddTag(AnglingBiomeTags.BLUE_CLAW_CRAB_BIOMES);

        builder(AnglingBiomeTags.SUNFISH_SPAWN_IN).add(SWAMP);
        builder(AnglingBiomeTags.SEA_SLUG_SPAWN_IN).add(WARM_OCEAN);
        builder(AnglingBiomeTags.CATFISH_SPAWN_IN).add(SWAMP);
        builder(AnglingBiomeTags.SEAHORSE_SPAWN_IN).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(AnglingBiomeTags.BUBBLE_EYE_SPAWN_IN).add(OLD_GROWTH_SPRUCE_TAIGA).add(OLD_GROWTH_PINE_TAIGA);
        builder(AnglingBiomeTags.ANOMALOCARIS_SPAWN_IN).add(MUSHROOM_FIELDS);
        builder(AnglingBiomeTags.ANGLERFISH_SPAWN_IN).add(DEEP_COLD_OCEAN);
        builder(AnglingBiomeTags.MAHI_MAHI_SPAWN_IN).add(WARM_OCEAN).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);

        // Feature biomes
        builder(AnglingBiomeTags.OYSTER_REEF_BIOMES)
                .add(OCEAN).add(DEEP_OCEAN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN)
                .add(FROZEN_OCEAN).add(DEEP_FROZEN_OCEAN);
        builder(AnglingBiomeTags.CLAMS_BIOMES)
                .add(WARM_OCEAN)
                .add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(AnglingBiomeTags.DUCKWEED_BIOMES).add(SWAMP);
        builder(AnglingBiomeTags.SARGASSUM_BIOMES).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(AnglingBiomeTags.PAPYRUS_BIOMES).add(SAVANNA).add(SAVANNA_PLATEAU).add(WINDSWEPT_SAVANNA).add(DESERT);

        // Starfish and anemone — shallow warm/lukewarm ocean (reef-like biomes)
        builder(AnglingBiomeTags.STARFISH_BIOMES)
                .add(WARM_OCEAN).add(LUKEWARM_OCEAN);
        builder(AnglingBiomeTags.ANEMONE_BIOMES)
                .add(WARM_OCEAN).add(LUKEWARM_OCEAN);

        // Algae — most underwater biomes
        builder(AnglingBiomeTags.ALGAE_BIOMES)
                .add(OCEAN).add(DEEP_OCEAN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN)
                .add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN)
                .add(WARM_OCEAN)
                .add(SWAMP).add(MANGROVE_SWAMP);

        // Wormy blocks — surface patches indicating worms underground
        builder(AnglingBiomeTags.WORMY_DIRT_BIOMES)
                .add(PLAINS).add(SUNFLOWER_PLAINS)
                .add(FOREST).add(BIRCH_FOREST).add(OLD_GROWTH_BIRCH_FOREST)
                .add(DARK_FOREST)
                .add(TAIGA).add(SNOWY_TAIGA)
                .add(MEADOW)
                .add(SWAMP);
        builder(AnglingBiomeTags.WORMY_MUD_BIOMES)
                .add(MANGROVE_SWAMP);
    }

    private static ResourceKey<Biome> mc(String id) {
        return ResourceKey.create(Registries.BIOME, Identifier.withDefaultNamespace(id));
    }
}
