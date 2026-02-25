package net.braeden.waterlogged;

import net.braeden.waterlogged.entity.util.*;
import net.braeden.waterlogged.entity.PelicanSpawner;
import net.braeden.waterlogged.tags.WaterloggedBiomeTags;
import net.braeden.waterlogged.worldgen.WaterloggedFeatures;
import net.braeden.waterlogged.worldgen.WaterloggedPlacedFeatures;
import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.item.WaterloggedItemGroup;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.braeden.waterlogged.sound.WaterloggedSounds;
import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.criteria.WaterloggedCriteria;
import net.braeden.waterlogged.particle.WaterloggedParticles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.tags.BiomeTags;
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

public class WaterloggedMod implements ModInitializer {
	public static final String MOD_ID = "angling";

	@Override
	public void onInitialize() {
		WaterloggedBlocks.init();
		WaterloggedItems.init();
		WaterloggedItemGroup.init();
		WaterloggedSounds.init();
		WaterloggedEntities.init();
		WaterloggedCriteria.init();
		WaterloggedFeatures.init();
		WaterloggedPlacedFeatures.init();
		WaterloggedParticles.init();
		FishVariantInheritance.init();

		// Add world gen features to biomes
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.DUCKWEED_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.DUCKWEED);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.SARGASSUM_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.SARGASSUM);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.PAPYRUS_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.PAPYRUS);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.CLAMS_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.CLAM);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.OYSTER_REEF_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.OYSTERS);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.STARFISH_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.STARFISH);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ANEMONE_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.ANEMONE);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ALGAE_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.ALGAE);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ALGAE_SWAMP_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.ALGAE_SWAMP);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.WORMY_DIRT_BIOMES),
				GenerationStep.Decoration.UNDERGROUND_ORES, WaterloggedPlacedFeatures.WORMY_DIRT);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.WORMY_MUD_BIOMES),
				GenerationStep.Decoration.UNDERGROUND_ORES, WaterloggedPlacedFeatures.WORMY_MUD);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(WaterloggedBiomeTags.URCHIN_BIOMES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.URCHIN);
		BiomeModifications.addFeature(
				ctx -> ctx.getBiomeRegistryEntry().is(BiomeTags.IS_DEEP_OCEAN),
				GenerationStep.Decoration.VEGETAL_DECORATION, WaterloggedPlacedFeatures.HYDROTHERMAL_VENT);

		PelicanSpawner spawner = new PelicanSpawner();
		ServerTickEvents.END_WORLD_TICK.register(level -> spawner.spawn(level, true, true));

		// Boost vanilla nautilus shell drop rate as compensation for removing our nautilus mob
		ResourceKey<LootTable> nautilusLoot = ResourceKey.create(Registries.LOOT_TABLE,
				Identifier.withDefaultNamespace("entities/nautilus"));
		// Modded fish catchable by fishing rod
		ResourceKey<LootTable> fishingFishLoot = ResourceKey.create(Registries.LOOT_TABLE,
				Identifier.withDefaultNamespace("gameplay/fishing/fish"));
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(nautilusLoot)) {
				tableBuilder.withPool(
						LootPool.lootPool()
								.add(LootItem.lootTableItem(Items.NAUTILUS_SHELL))
								.when(LootItemRandomChanceCondition.randomChance(0.5f))
				);
			}
			if (key.equals(fishingFishLoot)) {
				tableBuilder.withPool(
						LootPool.lootPool()
								.setRolls(net.minecraft.world.level.storage.loot.providers.number.ConstantValue.exactly(1))
								.when(LootItemRandomChanceCondition.randomChance(0.25f))
								.add(LootItem.lootTableItem(WaterloggedItems.SUNFISH))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_MAHI_MAHI))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_CATFISH))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_DONGFISH))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_SEAHORSE))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_BUBBLE_EYE))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_ANOMALOCARIS))
								.add(LootItem.lootTableItem(WaterloggedItems.RAW_ANGLERFISH))
				);
			}
		});
	}
}
