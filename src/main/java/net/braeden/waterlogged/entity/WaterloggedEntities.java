package net.braeden.waterlogged.entity;

import net.braeden.waterlogged.block.entity.*;
import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.tags.WaterloggedBiomeTags;
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

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedEntities {

    // Block Entity Types
    public static final BlockEntityType<RoeBlockEntity> ROE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "roe"),
            FabricBlockEntityTypeBuilder.create(RoeBlockEntity::new, WaterloggedBlocks.ROE).build()
    );

    public static final BlockEntityType<StarfishBlockEntity> STARFISH = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "starfish"),
            FabricBlockEntityTypeBuilder.create(StarfishBlockEntity::new, WaterloggedBlocks.STARFISH, WaterloggedBlocks.DEAD_STARFISH).build()
    );

    public static final BlockEntityType<SeaSlugEggsBlockEntity> SEA_SLUG_EGGS = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "sea_slug_eggs"),
            FabricBlockEntityTypeBuilder.create(SeaSlugEggsBlockEntity::new, WaterloggedBlocks.SEA_SLUG_EGGS).build()
    );

    public static final BlockEntityType<AnemoneBlockEntity> ANEMONE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "anemone"),
            FabricBlockEntityTypeBuilder.create(AnemoneBlockEntity::new, WaterloggedBlocks.ANEMONE).build()
    );

    public static final BlockEntityType<UrchinBlockEntity> URCHIN = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "urchin"),
            FabricBlockEntityTypeBuilder.create(UrchinBlockEntity::new, WaterloggedBlocks.URCHIN).build()
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

    public static final EntityType<OrcaEntity> ORCA = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "orca")),
            EntityType.Builder.<OrcaEntity>of(OrcaEntity::new, MobCategory.WATER_CREATURE)
                    .sized(3.5f, 2.5f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "orca")))
    );

    public static final EntityType<RightWhaleEntity> RIGHT_WHALE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "right_whale")),
            EntityType.Builder.<RightWhaleEntity>of(RightWhaleEntity::new, MobCategory.WATER_CREATURE)
                    .sized(4.2f, 3.0f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "right_whale")))
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
        FabricDefaultAttributeRegistry.register(ORCA, OrcaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(RIGHT_WHALE, RightWhaleEntity.createAttributes());

        // Register spawn placements
        SpawnPlacements.register(FRY, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SUNFISH, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SEA_SLUG, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeaSlugEntity::canSpawn);
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
        SpawnPlacements.register(ORCA, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(RIGHT_WHALE, SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);

        // Register biome spawns
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.SUNFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SUNFISH, 5, 2, 5
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.SEA_SLUG_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SEA_SLUG, 15, 2, 6
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.CATFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, CATFISH, 3, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.CRAB_SPAWN_IN),
                MobCategory.CREATURE, CRAB, 8, 3, 5
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.SEAHORSE_SPAWN_IN),
                MobCategory.WATER_AMBIENT, SEAHORSE, 4, 3, 8
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.BUBBLE_EYE_SPAWN_IN),
                MobCategory.WATER_AMBIENT, BUBBLE_EYE, 2, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ANOMALOCARIS_SPAWN_IN),
                MobCategory.UNDERGROUND_WATER_CREATURE, ANOMALOCARIS, 20, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.DONGFISH_SPAWN_IN),
                MobCategory.UNDERGROUND_WATER_CREATURE, DONGFISH, 4, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ANGLERFISH_SPAWN_IN),
                MobCategory.WATER_AMBIENT, ANGLERFISH, 6, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.MAHI_MAHI_SPAWN_IN),
                MobCategory.WATER_CREATURE, MAHI_MAHI, 4, 1, 2
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.ORCA_SPAWN_IN),
                MobCategory.WATER_CREATURE, ORCA, 1, 1, 1
        );
        BiomeModifications.addSpawn(
                biome -> biome.getBiomeRegistryEntry().is(WaterloggedBiomeTags.RIGHT_WHALE_SPAWN_IN),
                MobCategory.WATER_CREATURE, RIGHT_WHALE, 2, 1, 3
        );
    }
}
