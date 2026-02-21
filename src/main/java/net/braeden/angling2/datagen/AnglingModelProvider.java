package net.braeden.angling2.datagen;

import net.braeden.angling2.block.AlgaeBlock;
import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.block.ClamBlock;
import net.braeden.angling2.block.PapyrusBlock;
import net.braeden.angling2.item.AnglingItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class AnglingModelProvider extends FabricModelProvider {

    private static final ModelTemplate SPAWN_EGG = new ModelTemplate(
            Optional.of(Identifier.withDefaultNamespace("item/template_spawn_egg")),
            Optional.empty());

    public AnglingModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        // Aquarium glass: cube_all with vanilla glass texture
        Identifier aquariumModel = ModelTemplates.CUBE_ALL.create(
                AnglingBlocks.AQUARIUM_GLASS,
                new TextureMapping().put(TextureSlot.ALL, Identifier.withDefaultNamespace("block/glass")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(AnglingBlocks.AQUARIUM_GLASS,
                        BlockModelGenerators.plainVariant(aquariumModel)));
        gen.registerSimpleItemModel(AnglingBlocks.AQUARIUM_GLASS, aquariumModel);

        // Wormy dirt: cube_all
        Identifier wormyDirt1 = ModelTemplates.CUBE_ALL.create(
                Identifier.fromNamespaceAndPath("angling", "block/wormy_dirt_1"),
                new TextureMapping().put(TextureSlot.ALL, Identifier.fromNamespaceAndPath("angling", "block/wormy_dirt_1")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(AnglingBlocks.WORMY_DIRT,
                        BlockModelGenerators.plainVariant(wormyDirt1)));
        gen.registerSimpleItemModel(AnglingBlocks.WORMY_DIRT, wormyDirt1);

        // Wormy mud: cube_all
        Identifier wormyMud1 = ModelTemplates.CUBE_ALL.create(
                Identifier.fromNamespaceAndPath("angling", "block/wormy_mud_1"),
                new TextureMapping().put(TextureSlot.ALL, Identifier.fromNamespaceAndPath("angling", "block/wormy_mud_1")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(AnglingBlocks.WORMY_MUD,
                        BlockModelGenerators.plainVariant(wormyMud1)));
        gen.registerSimpleItemModel(AnglingBlocks.WORMY_MUD, wormyMud1);

        // Simple single-variant blocks (hand-written block models, datagen blockstates)
        simpleBlockState(gen, AnglingBlocks.ANEMONE, "block/anemone");
        simpleBlockState(gen, AnglingBlocks.DUCKWEED, "block/duckweed");
        simpleBlockState(gen, AnglingBlocks.OYSTERS, "block/oysters");
        simpleBlockState(gen, AnglingBlocks.ROE, "block/roe");
        gen.registerSimpleItemModel(AnglingBlocks.ROE.asItem(), Identifier.fromNamespaceAndPath("angling", "item/roe"));
        simpleBlockState(gen, AnglingBlocks.SARGASSUM, "block/sargassum");
        simpleBlockState(gen, AnglingBlocks.URCHIN, "block/urchin");

        // Sea slug eggs: random y-rotation selection
        Identifier seaSlugEggsModel = blockModel("sea_slug_eggs");
        Variant base = BlockModelGenerators.plainModel(seaSlugEggsModel);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(AnglingBlocks.SEA_SLUG_EGGS,
                        BlockModelGenerators.createRotatedVariants(base)));
        gen.registerSimpleItemModel(AnglingBlocks.SEA_SLUG_EGGS.asItem(), seaSlugEggsModel);

        // Clam: facing variants
        Identifier clamModel = blockModel("clam");
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(AnglingBlocks.CLAM)
                        .with(PropertyDispatch.<Direction>initial(ClamBlock.FACING)
                                .select(Direction.NORTH, BlockModelGenerators.plainVariant(clamModel))
                                .select(Direction.EAST, BlockModelGenerators.variant(
                                        BlockModelGenerators.plainModel(clamModel).with(BlockModelGenerators.Y_ROT_90)))
                                .select(Direction.SOUTH, BlockModelGenerators.variant(
                                        BlockModelGenerators.plainModel(clamModel).with(BlockModelGenerators.Y_ROT_180)))
                                .select(Direction.WEST, BlockModelGenerators.variant(
                                        BlockModelGenerators.plainModel(clamModel).with(BlockModelGenerators.Y_ROT_270)))));

        // Papyrus: variant by age (waterlogged is irrelevant to model)
        Identifier papyrusAge0 = blockModel("papyrus_age0");
        Identifier papyrusAge1 = blockModel("papyrus_age1");
        Identifier papyrusAge2 = blockModel("papyrus_age2");
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(AnglingBlocks.PAPYRUS)
                        .with(PropertyDispatch.<Integer>initial(PapyrusBlock.AGE)
                                .select(0, BlockModelGenerators.plainVariant(papyrusAge0))
                                .select(1, BlockModelGenerators.plainVariant(papyrusAge1))
                                .select(2, BlockModelGenerators.plainVariant(papyrusAge2))));

        // Algae: multipart with directional faces
        Identifier algaeModel = blockModel("algae");
        Variant algaeBase = BlockModelGenerators.plainModel(algaeModel);
        MultiPartGenerator algaeMultipart = MultiPartGenerator.multiPart(AnglingBlocks.ALGAE);
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.NORTH, true),
                BlockModelGenerators.variant(algaeBase));
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.SOUTH, true),
                BlockModelGenerators.variant(algaeBase.with(BlockModelGenerators.Y_ROT_180)));
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.EAST, true),
                BlockModelGenerators.variant(algaeBase.with(BlockModelGenerators.Y_ROT_90)));
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.WEST, true),
                BlockModelGenerators.variant(algaeBase.with(BlockModelGenerators.Y_ROT_270)));
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.UP, true),
                BlockModelGenerators.variant(algaeBase.with(BlockModelGenerators.X_ROT_270)));
        algaeMultipart.with(
                BlockModelGenerators.condition().term(AlgaeBlock.DOWN, true),
                BlockModelGenerators.variant(algaeBase.with(BlockModelGenerators.X_ROT_90)));
        gen.blockStateOutput.accept(algaeMultipart);

        // Starfish: multipart unconditional
        Identifier starfishModel = blockModel("starfish");
        MultiPartGenerator starfishMultipart = MultiPartGenerator.multiPart(AnglingBlocks.STARFISH);
        starfishMultipart.with(BlockModelGenerators.plainVariant(starfishModel));
        gen.blockStateOutput.accept(starfishMultipart);

        // Dead starfish: multipart unconditional
        Identifier deadStarfishModel = blockModel("dead_starfish");
        MultiPartGenerator deadStarfishMultipart = MultiPartGenerator.multiPart(AnglingBlocks.DEAD_STARFISH);
        deadStarfishMultipart.with(BlockModelGenerators.plainVariant(deadStarfishModel));
        gen.blockStateOutput.accept(deadStarfishMultipart);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModels) {
        // Spawn eggs
        itemModels.generateFlatItem(AnglingItems.FRY_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.SUNFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.PELICAN_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.SEA_SLUG_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.CRAB_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.DONGFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.CATFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.SEAHORSE_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.BUBBLE_EYE_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.ANOMALOCARIS_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.ANGLERFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.MAHI_MAHI_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(AnglingItems.ORCA_SPAWN_EGG, SPAWN_EGG);

        // Standard flat items (item/ textures)
        itemModels.generateFlatItem(AnglingItems.WORM, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.PEARL, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.SUNFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.FRIED_SUNFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_MAHI_MAHI, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_MAHI_MAHI, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_CRAB_LEGS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_CRAB_LEGS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_CATFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_CATFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_DONGFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_DONGFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_SEAHORSE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_SEAHORSE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_BUBBLE_EYE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_BUBBLE_EYE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_ANOMALOCARIS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_ANOMALOCARIS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.RAW_ANGLERFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.COOKED_ANGLERFISH, ModelTemplates.FLAT_ITEM);

        // Buckets (standard flat items, item/ textures)
        itemModels.generateFlatItem(AnglingItems.SUNFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.FRY_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.CRAB_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.CATFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.SEAHORSE_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.BUBBLE_EYE_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.ANOMALOCARIS_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.ANGLERFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.MAHI_MAHI_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingItems.URCHIN_BUCKET, ModelTemplates.FLAT_ITEM);

        // Block items with item/ textures (flat)
        itemModels.generateFlatItem(AnglingBlocks.ANEMONE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingBlocks.CLAM.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingBlocks.DEAD_STARFISH.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingBlocks.DUCKWEED.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingBlocks.PAPYRUS.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AnglingBlocks.STARFISH.asItem(), ModelTemplates.FLAT_ITEM);

        // Items that reference block textures or need custom handling:
        // algae, sargassum use block/ textures not item/ textures.
        // roe has two texture layers, sea_slug_bucket has three, oysters has display transforms,
        // dongfish_bucket has overrides. These are kept as hand-written models/item/ JSONs.
    }

    private static void simpleBlockState(BlockModelGenerators gen, Block block, String modelPath) {
        Identifier modelId = Identifier.fromNamespaceAndPath("angling", modelPath);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(modelId)));
    }

    private static Identifier blockModel(String name) {
        return Identifier.fromNamespaceAndPath("angling", "block/" + name);
    }
}
