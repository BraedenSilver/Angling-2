package net.braeden.waterlogged.item;

import net.braeden.waterlogged.item.RoeBlockItem;
import net.braeden.waterlogged.item.UrchinBucketItem;
import net.braeden.waterlogged.item.WormItem;
import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.entity.WaterloggedEntities;
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

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

@SuppressWarnings("unused")
public class WaterloggedItems {

    public static final Item FRY_SPAWN_EGG = registerSpawnEgg("fry", WaterloggedEntities.FRY);
    public static final Item SUNFISH_SPAWN_EGG = registerSpawnEgg("sunfish", WaterloggedEntities.SUNFISH);
    public static final Item PELICAN_SPAWN_EGG = registerSpawnEgg("pelican", WaterloggedEntities.PELICAN);
    public static final Item SEA_SLUG_SPAWN_EGG = registerSpawnEgg("sea_slug", WaterloggedEntities.SEA_SLUG);
    public static final Item CRAB_SPAWN_EGG = registerSpawnEgg("crab", WaterloggedEntities.CRAB);
    public static final Item DONGFISH_SPAWN_EGG = registerSpawnEgg("dongfish", WaterloggedEntities.DONGFISH);
    public static final Item CATFISH_SPAWN_EGG = registerSpawnEgg("catfish", WaterloggedEntities.CATFISH);
    public static final Item SEAHORSE_SPAWN_EGG = registerSpawnEgg("seahorse", WaterloggedEntities.SEAHORSE);
    public static final Item BUBBLE_EYE_SPAWN_EGG = registerSpawnEgg("bubble_eye", WaterloggedEntities.BUBBLE_EYE);
    public static final Item ANOMALOCARIS_SPAWN_EGG = registerSpawnEgg("anomalocaris", WaterloggedEntities.ANOMALOCARIS);
    public static final Item ANGLERFISH_SPAWN_EGG = registerSpawnEgg("anglerfish", WaterloggedEntities.ANGLERFISH);
    public static final Item MAHI_MAHI_SPAWN_EGG = registerSpawnEgg("mahi_mahi", WaterloggedEntities.MAHI_MAHI);
    public static final Item ORCA_SPAWN_EGG = registerSpawnEgg("orca", WaterloggedEntities.ORCA);
    public static final Item RIGHT_WHALE_SPAWN_EGG = registerSpawnEgg("right_whale", WaterloggedEntities.RIGHT_WHALE);

    public static final Item ROE = register("roe",
            id -> new RoeBlockItem(WaterloggedBlocks.ROE,
                    new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SEA_SLUG_EGGS = register("sea_slug_eggs",
            id -> new SeaSlugEggsBlockItem(WaterloggedBlocks.SEA_SLUG_EGGS,
                    new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item STARFISH = register("starfish",
            id -> new StarfishBlockItem(WaterloggedBlocks.STARFISH,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item DEAD_STARFISH = register("dead_starfish",
            id -> new StarfishBlockItem(WaterloggedBlocks.DEAD_STARFISH,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item DUCKWEED = register("duckweed",
            id -> new PlaceOnWaterBlockItem(WaterloggedBlocks.DUCKWEED,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SARGASSUM = register("sargassum",
            id -> new PlaceOnWaterBlockItem(WaterloggedBlocks.SARGASSUM,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item SUNFISH_BUCKET = registerBucket("sunfish", WaterloggedEntities.SUNFISH);
    public static final Item FRY_BUCKET = registerBucket("fry", WaterloggedEntities.FRY);
    public static final Item SEA_SLUG_BUCKET = registerBucket("sea_slug", WaterloggedEntities.SEA_SLUG);
    public static final Item CRAB_BUCKET = registerBucket("crab", WaterloggedEntities.CRAB);
    public static final Item DONGFISH_BUCKET = registerBucket("dongfish", WaterloggedEntities.DONGFISH);
    public static final Item CATFISH_BUCKET = registerBucket("catfish", WaterloggedEntities.CATFISH);
    public static final Item SEAHORSE_BUCKET = registerBucket("seahorse", WaterloggedEntities.SEAHORSE);
    public static final Item BUBBLE_EYE_BUCKET = registerBucket("bubble_eye", WaterloggedEntities.BUBBLE_EYE);
    public static final Item ANOMALOCARIS_BUCKET = registerBucket("anomalocaris", WaterloggedEntities.ANOMALOCARIS);
    public static final Item ANGLERFISH_BUCKET = registerBucket("anglerfish", WaterloggedEntities.ANGLERFISH);
    public static final Item MAHI_MAHI_BUCKET = registerBucket("mahi_mahi", WaterloggedEntities.MAHI_MAHI);

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

    public static final Item RAW_BUBBLE_EYE = register("raw_bubble_eye",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static final Item COOKED_BUBBLE_EYE = register("cooked_bubble_eye",
            id -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.8f).build())
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

    public static final Item FISHING_NET = register("fishing_net",
            id -> new FishingNetItem(new Item.Properties()
                    .durability(131)
                    .enchantable(10)
                    .setId(ResourceKey.create(Registries.ITEM, id))));

    public static void init() {
        // Items are registered via static field initializers above
        CompostingChanceRegistry.INSTANCE.add(DUCKWEED, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(SARGASSUM, 0.3f);
        // CompostingChanceRegistry.INSTANCE.add(WORM, 1f); // Removing this so worms are not compostable
        CompostingChanceRegistry.INSTANCE.add(WaterloggedBlocks.ALGAE.asItem(), 0.2f);
        CompostingChanceRegistry.INSTANCE.add(WaterloggedBlocks.PAPYRUS.asItem(), 0.2f);
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
