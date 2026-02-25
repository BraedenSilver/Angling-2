package net.braeden.waterlogged.block;

import net.braeden.waterlogged.sound.WaterloggedSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedBlocks {

    public static final Block ROE = register("roe",
            props -> new RoeBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .noCollision()
                    .noOcclusion()
                    .destroyTime(0f)
                    .sound(SoundType.FROGSPAWN)
                    .lightLevel(state -> 2),
            false);

    public static final Block SEA_SLUG_EGGS = register("sea_slug_eggs",
            props -> new SeaSlugEggsBlock(props),
            BlockBehaviour.Properties.ofFullCopy(ROE)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .dynamicShape()
                    .lightLevel(state -> state.getValue(SeaSlugEggsBlock.GLOWING) ? 3 : 0),
            false);

    public static final Block DUCKWEED = register("duckweed",
            props -> new WaterFloatingPlant(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .destroyTime(0f)
                    .noOcclusion()
                    .noCollision()
                    .sound(SoundType.WET_GRASS),
            false);

    public static final Block ALGAE = register("algae",
            props -> new AlgaeBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .sound(SoundType.FROGSPAWN)
                    .noCollision()
                    .noOcclusion()
                    .strength(0.1f)
                    .randomTicks(),
            true);

    public static final Block WORMY_DIRT = register("wormy_dirt",
            props -> new WormyDirtBlock(props),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT),
            true);

    public static final Block WORMY_MUD = register("wormy_mud",
            props -> new WormyMudBlock(props),
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD),
            true);

    public static final Block OYSTERS = register("oysters",
            props -> new OystersBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.5f)
                    .noOcclusion()
                    .sound(WaterloggedSounds.SHELL_SOUND_GROUP)
                    .randomTicks(),
            true);

    public static final Block STARFISH = register("starfish",
            props -> new StarfishBlock(props, false),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.1f)
                    .noOcclusion()
                    .noCollision()
                    .sound(WaterloggedSounds.SHELL_SOUND_GROUP)
                    .randomTicks(),
            false);

    public static final Block DEAD_STARFISH = register("dead_starfish",
            props -> new StarfishBlock(props, true),
            BlockBehaviour.Properties.ofFullCopy(STARFISH),
            false);

    public static final Block CLAM = register("clam",
            props -> new ClamBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .sound(WaterloggedSounds.SHELL_SOUND_GROUP)
                    .strength(0.05f)
                    .noOcclusion()
                    .randomTicks(),
            true);

    public static final Block ANEMONE = register("anemone",
            props -> new AnemoneBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.1f)
                    .noOcclusion()
                    .noCollision()
                    .sound(SoundType.SLIME_BLOCK)
                    .randomTicks(),
            true);

    public static final Block URCHIN = register("urchin",
            props -> new UrchinBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BLUE)
                    .strength(0.1f)
                    .noOcclusion()
                    .noCollision()
                    .sound(WaterloggedSounds.SHELL_SOUND_GROUP),
            false);

    public static final Block SARGASSUM = register("sargassum",
            props -> new WaterFloatingPlant(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .destroyTime(0f)
                    .noOcclusion()
                    .noCollision()
                    .sound(SoundType.WET_GRASS),
            false);

    public static final Block PAPYRUS = register("papyrus",
            props -> new PapyrusBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0)
                    .destroyTime(0f)
                    .noOcclusion()
                    .noCollision()
                    .sound(SoundType.AZALEA_LEAVES)
                    .randomTicks()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .dynamicShape(),
            true);

    public static final Block AQUARIUM_GLASS = register("aquarium_glass",
            props -> new AquariumGlassBlock(props),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn((state, world, pos, type) -> false)
                    .isRedstoneConductor((state, world, pos) -> false)
                    .isSuffocating((state, world, pos) -> false)
                    .isViewBlocking((state, world, pos) -> false),
            true);

    private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory,
                                                  BlockBehaviour.Properties baseProps, boolean hasBlockItem) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, name));
        T block = factory.apply(baseProps.setId(key));
        Registry.register(BuiltInRegistries.BLOCK, key, block);
        if (hasBlockItem) {
            Identifier itemId = Identifier.fromNamespaceAndPath(MOD_ID, name);
            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, itemId);
            Item.Properties itemProps = new Item.Properties().setId(itemKey);
            Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, itemProps));
        }
        return block;
    }

    public static void init() {
        // Blocks are registered via static field initializers above
    }
}
