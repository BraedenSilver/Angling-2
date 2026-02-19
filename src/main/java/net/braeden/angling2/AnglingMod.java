package net.braeden.angling2;

import net.braeden.angling2.entity.util.*;
import net.braeden.angling2.entity.PelicanSpawner;
import net.braeden.angling2.tags.AnglingBiomeTags;
import net.braeden.angling2.worldgen.AnglingFeatures;
import net.braeden.angling2.worldgen.AnglingPlacedFeatures;
import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.item.AnglingItemGroup;
import net.braeden.angling2.item.AnglingItems;
import net.braeden.angling2.sound.AnglingSounds;
import net.braeden.angling2.entity.AnglingEntities;
import net.braeden.angling2.criteria.AnglingCriteria;
import net.braeden.angling2.particle.AnglingParticles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class AnglingMod implements ModInitializer {
	public static final String MOD_ID = "angling";

	@Override
	public void onInitialize() {
		AnglingBlocks.init();
		AnglingItems.init();
		AnglingItemGroup.init();
		AnglingSounds.init();
		AnglingEntities.init();
		AnglingCriteria.init();
		AnglingFeatures.init();
		AnglingPlacedFeatures.init();
		AnglingParticles.init();
		FishVariantInheritance.init();

		// Add world gen features to biomes
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.DUCKWEED_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.DUCKWEED);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.SARGASSUM_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.SARGASSUM);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.PAPYRUS_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.PAPYRUS);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.CLAMS_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.CLAM);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.OYSTER_REEF_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.OYSTERS);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.STARFISH_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.STARFISH);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.ANEMONE_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.ANEMONE);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.ALGAE_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, AnglingPlacedFeatures.ALGAE);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.WORMY_DIRT_BIOMES),
				GenerationStep.Decoration.UNDERGROUND_ORES, AnglingPlacedFeatures.WORMY_DIRT);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(AnglingBiomeTags.WORMY_MUD_BIOMES),
				GenerationStep.Decoration.UNDERGROUND_ORES, AnglingPlacedFeatures.WORMY_MUD);

		PelicanSpawner spawner = new PelicanSpawner();
		ServerTickEvents.END_WORLD_TICK.register(level -> spawner.spawn(level, true, true));

		// Boost vanilla nautilus shell drop rate as compensation for removing our nautilus mob
		ResourceKey<LootTable> nautilusLoot = ResourceKey.create(Registries.LOOT_TABLE,
				Identifier.withDefaultNamespace("entities/nautilus"));
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(nautilusLoot)) {
				tableBuilder.withPool(
						LootPool.lootPool()
								.add(LootItem.lootTableItem(Items.NAUTILUS_SHELL))
								.when(LootItemRandomChanceCondition.randomChance(0.5f))
				);
			}
		});
	}
}
