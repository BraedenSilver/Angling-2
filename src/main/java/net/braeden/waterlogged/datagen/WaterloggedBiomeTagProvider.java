package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.tags.WaterloggedBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class WaterloggedBiomeTagProvider extends FabricTagProvider<Biome> {

    // Vanilla biomes
    private static final ResourceKey<Biome> MUSHROOM_FIELDS = mc("mushroom_fields");
    private static final ResourceKey<Biome> SAVANNA = mc("savanna");
    private static final ResourceKey<Biome> SAVANNA_PLATEAU = mc("savanna_plateau");
    private static final ResourceKey<Biome> WINDSWEPT_SAVANNA = mc("windswept_savanna");
    private static final ResourceKey<Biome> SWAMP = mc("swamp");
    private static final ResourceKey<Biome> MANGROVE_SWAMP = mc("mangrove_swamp");
    private static final ResourceKey<Biome> BEACH = mc("beach");
    private static final ResourceKey<Biome> STONY_SHORE = mc("stony_shore");
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
    private static final ResourceKey<Biome> RIVER = mc("river");
    private static final ResourceKey<Biome> FROZEN_RIVER = mc("frozen_river");

    public WaterloggedBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Crab sub-variant biomes
        builder(WaterloggedBiomeTags.DUNGENESS_CRAB_BIOMES).add(BEACH);
        builder(WaterloggedBiomeTags.GHOST_CRAB_BIOMES).add(MANGROVE_SWAMP);
        builder(WaterloggedBiomeTags.BLUE_CLAW_CRAB_BIOMES).add(STONY_SHORE);
        builder(WaterloggedBiomeTags.MOJANG_CRAB_BIOMES).add(SWAMP);

        // crab_spawn_in references the sub-variant tags
        builder(WaterloggedBiomeTags.CRAB_SPAWN_IN)
                .forceAddTag(WaterloggedBiomeTags.DUNGENESS_CRAB_BIOMES)
                .forceAddTag(WaterloggedBiomeTags.GHOST_CRAB_BIOMES)
                .forceAddTag(WaterloggedBiomeTags.BLUE_CLAW_CRAB_BIOMES)
                .forceAddTag(WaterloggedBiomeTags.MOJANG_CRAB_BIOMES);

        builder(WaterloggedBiomeTags.SUNFISH_SPAWN_IN).add(SWAMP);
        builder(WaterloggedBiomeTags.SEA_SLUG_SPAWN_IN).add(WARM_OCEAN);
        builder(WaterloggedBiomeTags.CATFISH_SPAWN_IN).add(SWAMP);
        builder(WaterloggedBiomeTags.SEAHORSE_SPAWN_IN).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(WaterloggedBiomeTags.BUBBLE_EYE_SPAWN_IN)
                .add(RIVER).add(FROZEN_RIVER)
                .add(FOREST).add(BIRCH_FOREST).add(OLD_GROWTH_BIRCH_FOREST).add(DARK_FOREST);
        builder(WaterloggedBiomeTags.ANOMALOCARIS_SPAWN_IN).add(MUSHROOM_FIELDS);
        builder(WaterloggedBiomeTags.ANGLERFISH_SPAWN_IN)
                .add(DEEP_OCEAN).add(DEEP_COLD_OCEAN).add(DEEP_FROZEN_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(WaterloggedBiomeTags.MAHI_MAHI_SPAWN_IN)
                .add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN)
                .add(WARM_OCEAN);

        // Orcas — cold and frozen ocean biomes only
        builder(WaterloggedBiomeTags.ORCA_SPAWN_IN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN)
                .add(FROZEN_OCEAN).add(DEEP_FROZEN_OCEAN);

        // Feature biomes
        builder(WaterloggedBiomeTags.OYSTER_REEF_BIOMES)
                .add(OCEAN).add(DEEP_OCEAN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN)
                .add(FROZEN_OCEAN).add(DEEP_FROZEN_OCEAN);
        builder(WaterloggedBiomeTags.CLAMS_BIOMES)
                .add(WARM_OCEAN)
                .add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(WaterloggedBiomeTags.DUCKWEED_BIOMES).add(SWAMP);
        builder(WaterloggedBiomeTags.SARGASSUM_BIOMES).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN);
        builder(WaterloggedBiomeTags.PAPYRUS_BIOMES).add(SAVANNA).add(SAVANNA_PLATEAU).add(WINDSWEPT_SAVANNA).add(DESERT);

        // Starfish and anemone — shallow warm/lukewarm ocean (reef-like biomes)
        builder(WaterloggedBiomeTags.STARFISH_BIOMES)
                .add(WARM_OCEAN).add(LUKEWARM_OCEAN);
        builder(WaterloggedBiomeTags.ANEMONE_BIOMES)
                .add(WARM_OCEAN).add(LUKEWARM_OCEAN);

        // Urchin — most ocean biomes
        builder(WaterloggedBiomeTags.URCHIN_BIOMES)
                .add(WARM_OCEAN).add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN)
                .add(OCEAN).add(DEEP_OCEAN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN);

        // Algae — most underwater biomes (swamps handled separately with higher density)
        builder(WaterloggedBiomeTags.ALGAE_BIOMES)
                .add(OCEAN).add(DEEP_OCEAN)
                .add(COLD_OCEAN).add(DEEP_COLD_OCEAN)
                .add(LUKEWARM_OCEAN).add(DEEP_LUKEWARM_OCEAN)
                .add(WARM_OCEAN);

        builder(WaterloggedBiomeTags.ALGAE_SWAMP_BIOMES)
                .add(SWAMP).add(MANGROVE_SWAMP);

        // Wormy blocks — surface patches indicating worms underground
        builder(WaterloggedBiomeTags.WORMY_DIRT_BIOMES)
                .add(PLAINS).add(SUNFLOWER_PLAINS)
                .add(FOREST).add(BIRCH_FOREST).add(OLD_GROWTH_BIRCH_FOREST)
                .add(DARK_FOREST)
                .add(TAIGA).add(SNOWY_TAIGA)
                .add(MEADOW)
                .add(SWAMP);
        builder(WaterloggedBiomeTags.WORMY_MUD_BIOMES)
                .add(MANGROVE_SWAMP);
    }

    private static ResourceKey<Biome> mc(String id) {
        return ResourceKey.create(Registries.BIOME, Identifier.withDefaultNamespace(id));
    }
}
