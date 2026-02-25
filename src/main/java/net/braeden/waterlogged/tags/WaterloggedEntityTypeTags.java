package net.braeden.waterlogged.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedEntityTypeTags {

    public static final TagKey<EntityType<?>> SPAWNING_FISH = create("spawning_fish");
    public static final TagKey<EntityType<?>> COMMON_ENTITIES_IN_PELICAN_BEAK = create("common_entities_in_pelican_beak");
    public static final TagKey<EntityType<?>> UNCOMMON_ENTITIES_IN_PELICAN_BEAK = create("uncommon_entities_in_pelican_beak");
    public static final TagKey<EntityType<?>> HUNTED_BY_PELICAN = create("hunted_by_pelican");
    public static final TagKey<EntityType<?>> HUNTED_BY_PELICAN_WHEN_BABY = create("hunted_by_pelican_when_baby");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, id));
    }
}
