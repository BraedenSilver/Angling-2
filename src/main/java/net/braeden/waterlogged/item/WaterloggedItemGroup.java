package net.braeden.waterlogged.item;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedItemGroup {

    public static final ResourceKey<CreativeModeTab> ANGLING_TAB_KEY =
            ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(),
                    Identifier.fromNamespaceAndPath(MOD_ID, "angling"));

    public static final CreativeModeTab ANGLING_TAB = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.angling"))
            .icon(() -> new ItemStack(WaterloggedItems.SUNFISH_BUCKET))
            .displayItems((params, output) -> {
                // Blocks
                output.accept(WaterloggedItems.DUCKWEED);
                output.accept(WaterloggedBlocks.ALGAE);
                output.accept(WaterloggedBlocks.WORMY_DIRT);
                output.accept(WaterloggedBlocks.WORMY_MUD);
                output.accept(WaterloggedBlocks.OYSTERS);
                output.accept(WaterloggedBlocks.STARFISH);
                output.accept(WaterloggedBlocks.DEAD_STARFISH);
                output.accept(WaterloggedBlocks.CLAM);
                output.accept(WaterloggedBlocks.ANEMONE);
                output.accept(WaterloggedBlocks.PAPYRUS);
                output.accept(WaterloggedItems.SARGASSUM);

                // Block items with no block item (registered separately in WaterloggedItems)
                output.accept(WaterloggedItems.ROE);
                output.accept(WaterloggedItems.SEA_SLUG_EGGS);

                // Food items
                output.accept(WaterloggedItems.SUNFISH);
                output.accept(WaterloggedItems.FRIED_SUNFISH);
                output.accept(WaterloggedItems.RAW_MAHI_MAHI);
                output.accept(WaterloggedItems.COOKED_MAHI_MAHI);
                output.accept(WaterloggedItems.RAW_CRAB_LEGS);
                output.accept(WaterloggedItems.COOKED_CRAB_LEGS);
                output.accept(WaterloggedItems.RAW_CATFISH);
                output.accept(WaterloggedItems.COOKED_CATFISH);
                output.accept(WaterloggedItems.RAW_DONGFISH);
                output.accept(WaterloggedItems.COOKED_DONGFISH);
                output.accept(WaterloggedItems.RAW_SEAHORSE);
                output.accept(WaterloggedItems.COOKED_SEAHORSE);
                output.accept(WaterloggedItems.RAW_BUBBLE_EYE);
                output.accept(WaterloggedItems.COOKED_BUBBLE_EYE);
                output.accept(WaterloggedItems.RAW_ANOMALOCARIS);
                output.accept(WaterloggedItems.COOKED_ANOMALOCARIS);
                output.accept(WaterloggedItems.RAW_ANGLERFISH);
                output.accept(WaterloggedItems.COOKED_ANGLERFISH);

                // Misc items
                output.accept(WaterloggedItems.PEARL);
                output.accept(WaterloggedItems.WORM);

                // Buckets
                output.accept(WaterloggedItems.FRY_BUCKET);
                output.accept(WaterloggedItems.SUNFISH_BUCKET);
                output.accept(WaterloggedItems.SEA_SLUG_BUCKET);
                output.accept(WaterloggedItems.CRAB_BUCKET);
                output.accept(WaterloggedItems.DONGFISH_BUCKET);
                output.accept(WaterloggedItems.CATFISH_BUCKET);
                output.accept(WaterloggedItems.SEAHORSE_BUCKET);
                output.accept(WaterloggedItems.BUBBLE_EYE_BUCKET);
                output.accept(WaterloggedItems.ANOMALOCARIS_BUCKET);
                output.accept(WaterloggedItems.ANGLERFISH_BUCKET);
                output.accept(WaterloggedItems.MAHI_MAHI_BUCKET);
                output.accept(WaterloggedItems.URCHIN_BUCKET);

                // Spawn eggs
                output.accept(WaterloggedItems.FRY_SPAWN_EGG);
                output.accept(WaterloggedItems.SUNFISH_SPAWN_EGG);
                output.accept(WaterloggedItems.PELICAN_SPAWN_EGG);
                output.accept(WaterloggedItems.SEA_SLUG_SPAWN_EGG);
                output.accept(WaterloggedItems.CRAB_SPAWN_EGG);
                output.accept(WaterloggedItems.DONGFISH_SPAWN_EGG);
                output.accept(WaterloggedItems.CATFISH_SPAWN_EGG);
                output.accept(WaterloggedItems.SEAHORSE_SPAWN_EGG);
                output.accept(WaterloggedItems.BUBBLE_EYE_SPAWN_EGG);
                output.accept(WaterloggedItems.ANOMALOCARIS_SPAWN_EGG);
                output.accept(WaterloggedItems.ANGLERFISH_SPAWN_EGG);
                output.accept(WaterloggedItems.MAHI_MAHI_SPAWN_EGG);
                output.accept(WaterloggedItems.ORCA_SPAWN_EGG);
                output.accept(WaterloggedItems.RIGHT_WHALE_SPAWN_EGG);
            })
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ANGLING_TAB_KEY, ANGLING_TAB);
    }
}
