package net.braeden.angling2.item;

import net.braeden.angling2.item.RoeBlockItem;
import net.braeden.angling2.item.UrchinBucketItem;
import net.braeden.angling2.item.WormItem;
import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.entity.AnglingEntities;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

import static net.braeden.angling2.AnglingMod.MOD_ID;

@SuppressWarnings("unused")
public class AnglingItems {

    public static final Item FRY_SPAWN_EGG = registerSpawnEgg("fry", AnglingEntities.FRY);
    public static final Item SUNFISH_SPAWN_EGG = registerSpawnEgg("sunfish", AnglingEntities.SUNFISH);
    public static final Item PELICAN_SPAWN_EGG = registerSpawnEgg("pelican", AnglingEntities.PELICAN);
    public static final Item SEA_SLUG_SPAWN_EGG = registerSpawnEgg("sea_slug", AnglingEntities.SEA_SLUG);
    public static final Item CRAB_SPAWN_EGG = registerSpawnEgg("crab", AnglingEntities.CRAB);
    public static final Item DONGFISH_SPAWN_EGG = registerSpawnEgg("dongfish", AnglingEntities.DONGFISH);
    public static final Item CATFISH_SPAWN_EGG = registerSpawnEgg("catfish", AnglingEntities.CATFISH);
    public static final Item SEAHORSE_SPAWN_EGG = registerSpawnEgg("seahorse", AnglingEntities.SEAHORSE);
    public static final Item BUBBLE_EYE_SPAWN_EGG = registerSpawnEgg("bubble_eye", AnglingEntities.BUBBLE_EYE);
    public static final Item ANOMALOCARIS_SPAWN_EGG = registerSpawnEgg("anomalocaris", AnglingEntities.ANOMALOCARIS);
    public static final Item ANGLERFISH_SPAWN_EGG = registerSpawnEgg("anglerfish", AnglingEntities.ANGLERFISH);
    public static final Item MAHI_MAHI_SPAWN_EGG = registerSpawnEgg("mahi_mahi", AnglingEntities.MAHI_MAHI);
    public static final Item ORCA_SPAWN_EGG = registerSpawnEgg("orca", AnglingEntities.ORCA);

    public static final Item ROE = register("roe",
            id -> new RoeBlockItem(AnglingBlocks.ROE,
                    new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SEA_SLUG_EGGS = register("sea_slug_eggs",
            id -> new BlockItem(AnglingBlocks.SEA_SLUG_EGGS,
                    new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item DUCKWEED = register("duckweed",
            id -> new PlaceOnWaterBlockItem(AnglingBlocks.DUCKWEED,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SARGASSUM = register("sargassum",
            id -> new PlaceOnWaterBlockItem(AnglingBlocks.SARGASSUM,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SUNFISH_BUCKET = registerBucket("sunfish", AnglingEntities.SUNFISH);
    public static final Item FRY_BUCKET = registerBucket("fry", AnglingEntities.FRY);
    public static final Item SEA_SLUG_BUCKET = registerBucket("sea_slug", AnglingEntities.SEA_SLUG);
    public static final Item CRAB_BUCKET = registerBucket("crab", AnglingEntities.CRAB);
    public static final Item DONGFISH_BUCKET = registerBucket("dongfish", AnglingEntities.DONGFISH);
    public static final Item CATFISH_BUCKET = registerBucket("catfish", AnglingEntities.CATFISH);
    public static final Item SEAHORSE_BUCKET = registerBucket("seahorse", AnglingEntities.SEAHORSE);
    public static final Item BUBBLE_EYE_BUCKET = registerBucket("bubble_eye", AnglingEntities.BUBBLE_EYE);
    public static final Item ANOMALOCARIS_BUCKET = registerBucket("anomalocaris", AnglingEntities.ANOMALOCARIS);
    public static final Item ANGLERFISH_BUCKET = registerBucket("anglerfish", AnglingEntities.ANGLERFISH);
    public static final Item MAHI_MAHI_BUCKET = registerBucket("mahi_mahi", AnglingEntities.MAHI_MAHI);

    public static final Item URCHIN_BUCKET = register("urchin_bucket",
            id -> new UrchinBucketItem(
                    new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item PEARL = register("pearl",
            id -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item WORM = register("worm",
            id -> new WormItem(
                    new Item.Properties()
                            .food(
                                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build(),
                                    Consumables.defaultFood()
                                            .onConsume(new ApplyStatusEffectsConsumeEffect(
                                                    new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f))
                                            .build()
                            )
                            .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_MAHI_MAHI = register("raw_mahi_mahi",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_MAHI_MAHI = register("cooked_mahi_mahi",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_CRAB_LEGS = register("raw_crab_legs",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_CRAB_LEGS = register("cooked_crab_legs",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_CATFISH = register("raw_catfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_CATFISH = register("cooked_catfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_DONGFISH = register("raw_dongfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_DONGFISH = register("cooked_dongfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.8f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_SEAHORSE = register("raw_seahorse",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_SEAHORSE = register("cooked_seahorse",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_BUBBLE_EYE = register("raw_bubble_eye",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_BUBBLE_EYE = register("cooked_bubble_eye",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.8f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_ANOMALOCARIS = register("raw_anomalocaris",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_ANOMALOCARIS = register("cooked_anomalocaris",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item RAW_ANGLERFISH = register("raw_anglerfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_ANGLERFISH = register("cooked_anglerfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SUNFISH = register("sunfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item FRIED_SUNFISH = register("fried_sunfish",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.9f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static void init() {
        // Items are registered via static field initializers above
        CompostingChanceRegistry.INSTANCE.add(DUCKWEED, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SARGASSUM, 0.3f);
        // CompostingChanceRegistry.INSTANCE.add(WORM, 1f); // Removing this so worms are not compostable
        CompostingChanceRegistry.INSTANCE.add(AnglingBlocks.ALGAE.asItem(), 0.2f);
        CompostingChanceRegistry.INSTANCE.add(AnglingBlocks.PAPYRUS.asItem(), 0.2f);
    }

    private static <T extends Item> T register(String name, java.util.function.Function<Identifier, T> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
        T item = factory.apply(id);
        Registry.register(BuiltInRegistries.ITEM, id, item);
        return item;
    }

    private static Item registerSpawnEgg(String name, EntityType<?> type) {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name + "_spawn_egg");
        Item.Properties props = new Item.Properties()
                .stacksTo(64)
                .spawnEgg(type)
                .setId(ResourceKey.create(Registries.ITEM, id));
        return Registry.register(BuiltInRegistries.ITEM, id, new SpawnEggItem(props));
    }

    private static Item registerBucket(String name, EntityType<? extends Mob> type) {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name + "_bucket");
        Item.Properties props = new Item.Properties()
                .stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM, id));
        return Registry.register(BuiltInRegistries.ITEM, id,
                new MobBucketItem(type, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, props));
    }
}
