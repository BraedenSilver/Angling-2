package net.braeden.angling2.datagen;

import net.braeden.angling2.tags.AnglingBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class AnglingBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    // Angling blocks
    private static final ResourceKey<Block> ALGAE = block("algae");
    private static final ResourceKey<Block> DUCKWEED = block("duckweed");
    private static final ResourceKey<Block> ROE = block("roe");
    private static final ResourceKey<Block> WORMY_DIRT = block("wormy_dirt");
    private static final ResourceKey<Block> WORMY_MUD = block("wormy_mud");
    private static final ResourceKey<Block> OYSTERS = block("oysters");
    private static final ResourceKey<Block> CLAM = block("clam");
    private static final ResourceKey<Block> STARFISH = block("starfish");
    private static final ResourceKey<Block> DEAD_STARFISH = block("dead_starfish");
    private static final ResourceKey<Block> AQUARIUM_GLASS = block("aquarium_glass");
    // Minecraft blocks used in crab_spawnable_on
    private static final ResourceKey<Block> MC_SAND = mcBlock("sand");
    private static final ResourceKey<Block> MC_MUD = mcBlock("mud");
    private static final ResourceKey<Block> MC_MUDDY_MANGROVE_ROOTS = mcBlock("muddy_mangrove_roots");

    public AnglingBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // minecraft:mineable/hoe — algae, duckweed, roe, oysters, clam
        builder(BlockTags.MINEABLE_WITH_HOE)
                .add(ALGAE)
                .add(DUCKWEED)
                .add(ROE)
                .add(OYSTERS)
                .add(CLAM);

        // minecraft:mineable/shovel — wormy_dirt, wormy_mud
        builder(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(WORMY_DIRT)
                .add(WORMY_MUD);

        // minecraft:mineable/pickaxe — starfish, dead_starfish
        builder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(STARFISH)
                .add(DEAD_STARFISH);
        
        // Glass tags
        builder(BlockTags.IMPERMEABLE)
               .add(AQUARIUM_GLASS);

        // minecraft:dirt — wormy_dirt, wormy_mud
        builder(BlockTags.DIRT)
                .add(WORMY_DIRT)
                .add(WORMY_MUD);

        // angling:crab_spawnable_on — sand, mud, muddy_mangrove_roots, #base_stone_overworld
        builder(AnglingBlockTags.CRAB_SPAWNABLE_ON)
                .add(MC_SAND)
                .add(MC_MUD)
                .add(MC_MUDDY_MANGROVE_ROOTS)
                .forceAddTag(BlockTags.BASE_STONE_OVERWORLD);

        // angling:filter_feeders — oysters, clam, sea_pickle, #corals, #wall_corals, #coral_blocks
        builder(AnglingBlockTags.FILTER_FEEDERS)
                .add(OYSTERS)
                .add(CLAM)
                .add(mcBlock("sea_pickle"))
                .forceAddTag(BlockTags.CORALS)
                .forceAddTag(BlockTags.WALL_CORALS)
                .forceAddTag(BlockTags.CORAL_BLOCKS);

        // angling:starfish_food — oysters, clam
        builder(AnglingBlockTags.STARFISH_FOOD)
                .add(OYSTERS)
                .add(CLAM);
    }

    private static ResourceKey<Block> block(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("angling", id));
    }

    private static ResourceKey<Block> mcBlock(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(id));
    }
}
