package net.braeden.angling2.entity;

import net.braeden.angling2.block.entity.*;
import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.tags.AnglingBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;

import static net.braeden.angling2.AnglingMod.MOD_ID;

public class AnglingEntities {

    // Block Entity Types
    public static final BlockEntityType<RoeBlockEntity> ROE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "roe"),
            FabricBlockEntityTypeBuilder.create(RoeBlockEntity::new, AnglingBlocks.ROE).build()
    );

    public static final BlockEntityType<StarfishBlockEntity> STARFISH = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "starfish"),
            FabricBlockEntityTypeBuilder.create(StarfishBlockEntity::new, AnglingBlocks.STARFISH, AnglingBlocks.DEAD_STARFISH).build()
    );

    public static final BlockEntityType<SeaSlugEggsBlockEntity> SEA_SLUG_EGGS = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "sea_slug_eggs"),
            FabricBlockEntityTypeBuilder.create(SeaSlugEggsBlockEntity::new, AnglingBlocks.SEA_SLUG_EGGS).build()
    );

    public static final BlockEntityType<AnemoneBlockEntity> ANEMONE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "anemone"),
            FabricBlockEntityTypeBuilder.create(AnemoneBlockEntity::new, AnglingBlocks.ANEMONE).build()
    );

    public static final BlockEntityType<UrchinBlockEntity> URCHIN = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "urchin"),
            FabricBlockEntityTypeBuilder.create(UrchinBlockEntity::new, AnglingBlocks.URCHIN).build()
    );

    // Entity Types
    public static final EntityType<FryEntity> FRY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "fry")),
            EntityType.Builder.<FryEntity>of(FryEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.2f, 0.15f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "fry")))
    );

    public static final EntityType<SunfishEntity> SUNFISH = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "sunfish")),
            EntityType.Builder.<SunfishEntity>of(SunfishEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.3f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "sunfish")))
    );

    public static final EntityType<PelicanEntity> PELICAN = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "pelican")),
            EntityType.Builder.<PelicanEntity>of(PelicanEntity::new, MobCategory.AMBIENT)
                    .sized(0.7f, 1.65f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "pelican")))
    );

    public static final EntityType<SeaSlugEntity> SEA_SLUG = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "sea_slug")),
            EntityType.Builder.<SeaSlugEntity>of(SeaSlugEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.3f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "sea_slug")))
    );

    public static final EntityType<CrabEntity> CRAB = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "crab")),
            EntityType.Builder.<CrabEntity>of(CrabEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.4f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "crab")))
    );

    public static final EntityType<DongfishEntity> DONGFISH = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "dongfish")),
            EntityType.Builder.<DongfishEntity>of(DongfishEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.4f, 0.3f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "dongfish")))
    );

    public static final EntityType<CatfishEntity> CATFISH = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "catfish")),
            EntityType.Builder.<CatfishEntity>of(CatfishEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.6f, 0.4f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "catfish")))
    );

    public static final EntityType<SeahorseEntity> SEAHORSE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "seahorse")),
            EntityType.Builder.<SeahorseEntity>of(SeahorseEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.35f, 0.6f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "seahorse")))
    );

    public static final EntityType<BubbleEyeEntity> BUBBLE_EYE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "bubble_eye")),
            EntityType.Builder.<BubbleEyeEntity>of(BubbleEyeEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.4f, 0.3f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "bubble_eye")))
    );

    public static final EntityType<AnomalocarisEntity> ANOMALOCARIS = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "anomalocaris")),
            EntityType.Builder.<AnomalocarisEntity>of(AnomalocarisEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE)
                    .sized(0.8f, 0.3f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "anomalocaris")))
    );

    public static final EntityType<AnglerfishEntity> ANGLERFISH = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "anglerfish")),
            EntityType.Builder.<AnglerfishEntity>of(AnglerfishEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.8f, 0.5f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "anglerfish")))
    );

    public static final EntityType<MahiMahiEntity> MAHI_MAHI = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "mahi_mahi")),
            EntityType.Builder.<MahiMahiEntity>of(MahiMahiEntity::new, MobCategory.WATER_CREATURE)
                    .sized(1f, 0.8f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "mahi_mahi")))
    );

    public static void init() {
        // Register attributes
        FabricDefaultAttributeRegistry.register(FRY, FryEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SUNFISH, SunfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(PELICAN, PelicanEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SEA_SLUG, SeaSlugEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CRAB, CrabEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(DONGFISH, DongfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CATFISH, CatfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SEAHORSE, SeahorseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BUBBLE_EYE, BubbleEyeEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ANOMALOCARIS, AnomalocarisEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ANGLERFISH, AnglerfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MAHI_MAHI, MahiMahiEntity.createAttributes());

        // Register spawn placements
        SpawnPlacements.register(FRY, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SUNFISH, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SEA_SLUG, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(CRAB, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
        SpawnPlacements.register(DONGFISH, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(CATFISH, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SEAHORSE, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(BUBBLE_EYE, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(ANOMALOCARIS, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AnomalocarisEntity::canSpawn);
        SpawnPlacements.register(ANGLERFISH, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(MAHI_MAHI, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);

        // Register biome spawns
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.SUNFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SUNFISH, 5, 2, 5
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.SEA_SLUG_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SEA_SLUG, 4, 1, 3
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.CATFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, CATFISH, 1, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.CRAB_SPAWN_IN),
                MobCategory.CREATURE, CRAB, 8, 3, 5
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.SEAHORSE_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SEAHORSE, 4, 3, 8
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.BUBBLE_EYE_SPAWN_IN),
                MobCategory.WATER_AMBIENT, BUBBLE_EYE, 4, 2, 3
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.ANOMALOCARIS_SPAWN_IN),
                MobCategory.UNDERGROUND_WATER_CREATURE, ANOMALOCARIS, 20, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.ANGLERFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, ANGLERFISH, 6, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(AnglingBiomeTags.MAHI_MAHI_SPAWN_IN),
                MobCategory.WATER_CREATURE, MAHI_MAHI, 4, 1, 2
        );
    }
}
