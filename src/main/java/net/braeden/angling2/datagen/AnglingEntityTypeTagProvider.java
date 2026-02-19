package net.braeden.angling2.datagen;

import net.braeden.angling2.tags.AnglingEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class AnglingEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    // Vanilla entities
    private static final ResourceKey<EntityType<?>> COD = mc("cod");
    private static final ResourceKey<EntityType<?>> SALMON = mc("salmon");
    private static final ResourceKey<EntityType<?>> PUFFERFISH = mc("pufferfish");
    private static final ResourceKey<EntityType<?>> TROPICAL_FISH = mc("tropical_fish");
    private static final ResourceKey<EntityType<?>> TADPOLE = mc("tadpole");
    private static final ResourceKey<EntityType<?>> FROG = mc("frog");
    private static final ResourceKey<EntityType<?>> TURTLE = mc("turtle");
    private static final ResourceKey<EntityType<?>> RABBIT = mc("rabbit");
    private static final ResourceKey<EntityType<?>> AXOLOTL = mc("axolotl");
    private static final ResourceKey<EntityType<?>> BEE = mc("bee");

    // Angling entities
    private static final ResourceKey<EntityType<?>> FRY = angling("fry");
    private static final ResourceKey<EntityType<?>> SUNFISH = angling("sunfish");
    private static final ResourceKey<EntityType<?>> SEA_SLUG = angling("sea_slug");
    private static final ResourceKey<EntityType<?>> CRAB = angling("crab");
    private static final ResourceKey<EntityType<?>> CATFISH = angling("catfish");
    private static final ResourceKey<EntityType<?>> BUBBLE_EYE = angling("bubble_eye");
    private static final ResourceKey<EntityType<?>> SEAHORSE = angling("seahorse");
    private static final ResourceKey<EntityType<?>> MAHI_MAHI = angling("mahi_mahi");
    private static final ResourceKey<EntityType<?>> DONGFISH = angling("dongfish");
    private static final ResourceKey<EntityType<?>> ANOMALOCARIS = angling("anomalocaris");
    private static final ResourceKey<EntityType<?>> ANGLERFISH = angling("anglerfish");

    public AnglingEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // angling:common_entities_in_pelican_beak
        builder(AnglingEntityTypeTags.COMMON_ENTITIES_IN_PELICAN_BEAK)
                .add(COD).add(SALMON).add(PUFFERFISH).add(TROPICAL_FISH).add(TADPOLE)
                .add(FRY).add(SUNFISH).add(SEA_SLUG).add(CRAB).add(CATFISH)
                .add(BUBBLE_EYE).add(SEAHORSE).add(MAHI_MAHI);

        // angling:hunted_by_pelican_when_baby
        builder(AnglingEntityTypeTags.HUNTED_BY_PELICAN_WHEN_BABY)
                .add(TURTLE).add(RABBIT).add(AXOLOTL).add(BEE).add(CRAB);

        // angling:uncommon_entities_in_pelican_beak
        builder(AnglingEntityTypeTags.UNCOMMON_ENTITIES_IN_PELICAN_BEAK)
                .add(FROG).add(DONGFISH).add(ANOMALOCARIS).add(ANGLERFISH)
                .forceAddTag(AnglingEntityTypeTags.HUNTED_BY_PELICAN_WHEN_BABY);

        // angling:hunted_by_pelican (references the two sub-tags)
        builder(AnglingEntityTypeTags.HUNTED_BY_PELICAN)
                .forceAddTag(AnglingEntityTypeTags.COMMON_ENTITIES_IN_PELICAN_BEAK)
                .forceAddTag(AnglingEntityTypeTags.UNCOMMON_ENTITIES_IN_PELICAN_BEAK);

        // angling:spawning_fish
        builder(AnglingEntityTypeTags.SPAWNING_FISH)
                .add(COD).add(SALMON).add(PUFFERFISH).add(TROPICAL_FISH)
                .add(SUNFISH).add(DONGFISH).add(CATFISH).add(BUBBLE_EYE)
                .add(ANOMALOCARIS).add(ANGLERFISH).add(MAHI_MAHI);

        // minecraft:axolotl_hunt_targets — add angling aquatic entities
        builder(EntityTypeTags.AXOLOTL_HUNT_TARGETS)
                .add(SUNFISH).add(FRY).add(SEA_SLUG).add(CRAB)
                .add(DONGFISH).add(CATFISH).add(SEAHORSE).add(BUBBLE_EYE);
    }

    private static ResourceKey<EntityType<?>> mc(String id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.withDefaultNamespace(id));
    }

    private static ResourceKey<EntityType<?>> angling(String id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("angling", id));
    }
}
