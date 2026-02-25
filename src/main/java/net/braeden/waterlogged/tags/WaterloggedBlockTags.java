package net.braeden.waterlogged.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedBlockTags {

    public static final TagKey<Block> FILTER_FEEDERS = create("filter_feeders");
    public static final TagKey<Block> STARFISH_FOOD = create("starfish_food");
    public static final TagKey<Block> CRAB_SPAWNABLE_ON = create("crab_spawnable_on");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, id));
    }
}
