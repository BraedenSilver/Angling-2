package net.braeden.angling2.item;

import net.braeden.angling2.block.AnglingBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static net.braeden.angling2.AnglingMod.MOD_ID;

public class AnglingItemGroup {

    public static final ResourceKey<CreativeModeTab> ANGLING_TAB_KEY =
            ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(),
                    Identifier.fromNamespaceAndPath(MOD_ID, "angling"));

    public static final CreativeModeTab ANGLING_TAB = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.angling"))
            .icon(() -> new ItemStack(AnglingItems.SUNFISH_BUCKET))
            .displayItems((params, output) -> {
                // Blocks
                output.accept(AnglingItems.DUCKWEED);
                output.accept(AnglingBlocks.ALGAE);
                output.accept(AnglingBlocks.WORMY_DIRT);
                output.accept(AnglingBlocks.WORMY_MUD);
                output.accept(AnglingBlocks.OYSTERS);
                output.accept(AnglingBlocks.STARFISH);
                output.accept(AnglingBlocks.DEAD_STARFISH);
                output.accept(AnglingBlocks.CLAM);
                output.accept(AnglingBlocks.ANEMONE);
                output.accept(AnglingBlocks.PAPYRUS);
                output.accept(AnglingItems.SARGASSUM);

                // Block items with no block item (registered separately in AnglingItems)
                output.accept(AnglingItems.ROE);
                output.accept(AnglingItems.SEA_SLUG_EGGS);

                // Food items
                output.accept(AnglingItems.SUNFISH);
                output.accept(AnglingItems.FRIED_SUNFISH);
                output.accept(AnglingItems.RAW_MAHI_MAHI);
                output.accept(AnglingItems.COOKED_MAHI_MAHI);
                output.accept(AnglingItems.RAW_CRAB_LEGS);
                output.accept(AnglingItems.COOKED_CRAB_LEGS);
                output.accept(AnglingItems.RAW_CATFISH);
                output.accept(AnglingItems.COOKED_CATFISH);
                output.accept(AnglingItems.RAW_DONGFISH);
                output.accept(AnglingItems.COOKED_DONGFISH);
                output.accept(AnglingItems.RAW_SEAHORSE);
                output.accept(AnglingItems.COOKED_SEAHORSE);
                output.accept(AnglingItems.RAW_BUBBLE_EYE);
                output.accept(AnglingItems.COOKED_BUBBLE_EYE);
                output.accept(AnglingItems.RAW_ANOMALOCARIS);
                output.accept(AnglingItems.COOKED_ANOMALOCARIS);
                output.accept(AnglingItems.RAW_ANGLERFISH);
                output.accept(AnglingItems.COOKED_ANGLERFISH);

                // Misc items
                output.accept(AnglingItems.PEARL);
                output.accept(AnglingItems.WORM);

                // Buckets
                output.accept(AnglingItems.FRY_BUCKET);
                output.accept(AnglingItems.SUNFISH_BUCKET);
                output.accept(AnglingItems.SEA_SLUG_BUCKET);
                output.accept(AnglingItems.CRAB_BUCKET);
                output.accept(AnglingItems.DONGFISH_BUCKET);
                output.accept(AnglingItems.CATFISH_BUCKET);
                output.accept(AnglingItems.SEAHORSE_BUCKET);
                output.accept(AnglingItems.BUBBLE_EYE_BUCKET);
                output.accept(AnglingItems.ANOMALOCARIS_BUCKET);
                output.accept(AnglingItems.ANGLERFISH_BUCKET);
                output.accept(AnglingItems.MAHI_MAHI_BUCKET);
                output.accept(AnglingItems.URCHIN_BUCKET);

                // Spawn eggs
                output.accept(AnglingItems.FRY_SPAWN_EGG);
                output.accept(AnglingItems.SUNFISH_SPAWN_EGG);
                output.accept(AnglingItems.PELICAN_SPAWN_EGG);
                output.accept(AnglingItems.SEA_SLUG_SPAWN_EGG);
                output.accept(AnglingItems.CRAB_SPAWN_EGG);
                output.accept(AnglingItems.DONGFISH_SPAWN_EGG);
                output.accept(AnglingItems.CATFISH_SPAWN_EGG);
                output.accept(AnglingItems.SEAHORSE_SPAWN_EGG);
                output.accept(AnglingItems.BUBBLE_EYE_SPAWN_EGG);
                output.accept(AnglingItems.ANOMALOCARIS_SPAWN_EGG);
                output.accept(AnglingItems.ANGLERFISH_SPAWN_EGG);
                output.accept(AnglingItems.MAHI_MAHI_SPAWN_EGG);
                output.accept(AnglingItems.ORCA_SPAWN_EGG);
            })
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ANGLING_TAB_KEY, ANGLING_TAB);
    }
}
