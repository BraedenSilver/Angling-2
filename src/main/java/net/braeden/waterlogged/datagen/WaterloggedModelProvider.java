package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.block.AlgaeBlock;
import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.ClamBlock;
import net.braeden.waterlogged.block.PapyrusBlock;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.CustomModelDataSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.BiConsumer;

public class WaterloggedModelProvider extends FabricModelProvider {

    private static final ModelTemplate SPAWN_EGG = new ModelTemplate(
            Optional.of(Identifier.withDefaultNamespace("item/template_spawn_egg")),
            Optional.empty());

    /** Saved from generateBlockStateModels so generateItemModels can write model geometry files. */
    private BiConsumer<Identifier, ModelInstance> modelOutput;

    public WaterloggedModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        this.modelOutput = gen.modelOutput;
        // Aquarium glass: cube_all with vanilla glass texture
        Identifier aquariumModel = ModelTemplates.CUBE_ALL.create(
                WaterloggedBlocks.AQUARIUM_GLASS,
                new TextureMapping().put(TextureSlot.ALL, Identifier.withDefaultNamespace("block/glass")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(WaterloggedBlocks.AQUARIUM_GLASS,
                        BlockModelGenerators.plainVariant(aquariumModel)));
        gen.registerSimpleItemModel(WaterloggedBlocks.AQUARIUM_GLASS, aquariumModel);

        // Wormy dirt: cube_all
        Identifier wormyDirt1 = ModelTemplates.CUBE_ALL.create(
                Identifier.fromNamespaceAndPath("angling", "block/wormy_dirt_1"),
                new TextureMapping().put(TextureSlot.ALL, Identifier.fromNamespaceAndPath("angling", "block/wormy_dirt_1")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(WaterloggedBlocks.WORMY_DIRT,
                        BlockModelGenerators.plainVariant(wormyDirt1)));
        gen.registerSimpleItemModel(WaterloggedBlocks.WORMY_DIRT, wormyDirt1);

        // Wormy mud: cube_all
        Identifier wormyMud1 = ModelTemplates.CUBE_ALL.create(
                Identifier.fromNamespaceAndPath("angling", "block/wormy_mud_1"),
                new TextureMapping().put(TextureSlot.ALL, Identifier.fromNamespaceAndPath("angling", "block/wormy_mud_1")),
                gen.modelOutput);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(WaterloggedBlocks.WORMY_MUD,
                        BlockModelGenerators.plainVariant(wormyMud1)));
        gen.registerSimpleItemModel(WaterloggedBlocks.WORMY_MUD, wormyMud1);

        // Simple single-variant blocks (hand-written block models, datagen blockstates)
        simpleBlockState(gen, WaterloggedBlocks.ANEMONE, "block/anemone");
        simpleBlockState(gen, WaterloggedBlocks.DUCKWEED, "block/duckweed");
        simpleBlockState(gen, WaterloggedBlocks.OYSTERS, "block/oysters");
        simpleBlockState(gen, WaterloggedBlocks.ROE, "block/roe");
        // items/roe.json registered with tints in generateItemModels()
        simpleBlockState(gen, WaterloggedBlocks.SARGASSUM, "block/sargassum");
        simpleBlockState(gen, WaterloggedBlocks.URCHIN, "block/urchin");

        // Sea slug eggs: random y-rotation selection
        Identifier seaSlugEggsModel = blockModel("sea_slug_eggs");
        Variant base = BlockModelGenerators.plainModel(seaSlugEggsModel);
        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(WaterloggedBlocks.SEA_SLUG_EGGS,
                        BlockModelGenerators.createRotatedVariants(base)));
        // items/sea_slug_eggs.json registered with tints in generateItemModels()

        // Clam: facing variants
        Identifier clamModel = blockModel("clam");
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(WaterloggedBlocks.CLAM)
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
                MultiVariantGenerator.dispatch(WaterloggedBlocks.PAPYRUS)
                        .with(PropertyDispatch.<Integer>initial(PapyrusBlock.AGE)
                                .select(0, BlockModelGenerators.plainVariant(papyrusAge0))
                                .select(1, BlockModelGenerators.plainVariant(papyrusAge1))
                                .select(2, BlockModelGenerators.plainVariant(papyrusAge2))));

        // Algae: multipart with directional faces
        Identifier algaeModel = blockModel("algae");
        Variant algaeBase = BlockModelGenerators.plainModel(algaeModel);
        MultiPartGenerator algaeMultipart = MultiPartGenerator.multiPart(WaterloggedBlocks.ALGAE);
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
        MultiPartGenerator starfishMultipart = MultiPartGenerator.multiPart(WaterloggedBlocks.STARFISH);
        starfishMultipart.with(BlockModelGenerators.plainVariant(starfishModel));
        gen.blockStateOutput.accept(starfishMultipart);

        // Dead starfish: multipart unconditional
        Identifier deadStarfishModel = blockModel("dead_starfish");
        MultiPartGenerator deadStarfishMultipart = MultiPartGenerator.multiPart(WaterloggedBlocks.DEAD_STARFISH);
        deadStarfishMultipart.with(BlockModelGenerators.plainVariant(deadStarfishModel));
        gen.blockStateOutput.accept(deadStarfishMultipart);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModels) {
        // Spawn eggs
        itemModels.generateFlatItem(WaterloggedItems.FRY_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.SUNFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.PELICAN_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.SEA_SLUG_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.CRAB_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.DONGFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.CATFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.SEAHORSE_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.BUBBLE_EYE_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.ANOMALOCARIS_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.ANGLERFISH_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.MAHI_MAHI_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.ORCA_SPAWN_EGG, SPAWN_EGG);
        itemModels.generateFlatItem(WaterloggedItems.RIGHT_WHALE_SPAWN_EGG, SPAWN_EGG);

        // Standard flat items (item/ textures)
        itemModels.generateFlatItem(WaterloggedItems.WORM, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.PEARL, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.SUNFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.FRIED_SUNFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_MAHI_MAHI, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_MAHI_MAHI, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_CRAB_LEGS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_CRAB_LEGS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_CATFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_CATFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_DONGFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_DONGFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_SEAHORSE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_SEAHORSE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_BUBBLE_EYE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_BUBBLE_EYE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_ANOMALOCARIS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_ANOMALOCARIS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.RAW_ANGLERFISH, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.COOKED_ANGLERFISH, ModelTemplates.FLAT_ITEM);

        // Buckets (standard flat items, item/ textures)
        itemModels.generateFlatItem(WaterloggedItems.SUNFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        // Fry bucket: two-layer (base + color overlay tinted by CustomModelData.colors[0])
        Identifier fryBucketModelId = ModelTemplates.TWO_LAYERED_ITEM.create(
                ModelLocationUtils.getModelLocation(WaterloggedItems.FRY_BUCKET),
                TextureMapping.layered(
                        TextureMapping.getItemTexture(WaterloggedItems.FRY_BUCKET),
                        TextureMapping.getItemTexture(WaterloggedItems.FRY_BUCKET, "_overlay")),
                this.modelOutput);
        itemModels.itemModelOutput.accept(WaterloggedItems.FRY_BUCKET,
                ItemModelUtils.tintedModel(fryBucketModelId,
                        new Constant(-1),
                        new CustomModelDataSource(0, 0xFFFFFF)));
        itemModels.generateFlatItem(WaterloggedItems.CRAB_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.CATFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.SEAHORSE_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.BUBBLE_EYE_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.ANOMALOCARIS_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.ANGLERFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.MAHI_MAHI_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedItems.URCHIN_BUCKET, ModelTemplates.FLAT_ITEM);

        // Block items with item/ textures (flat)
        itemModels.generateFlatItem(WaterloggedBlocks.ANEMONE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedBlocks.CLAM.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedBlocks.DEAD_STARFISH.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedBlocks.DUCKWEED.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(WaterloggedBlocks.PAPYRUS.asItem(), ModelTemplates.FLAT_ITEM);
        // Starfish: flat item tinted by CustomModelData.colors[0]
        Identifier starfishModelId = itemModels.createFlatItemModel(WaterloggedBlocks.STARFISH.asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(WaterloggedBlocks.STARFISH.asItem(),
                ItemModelUtils.tintedModel(starfishModelId, new CustomModelDataSource(0, 0xFFFFFF)));
        // Roe: two-layer geometry (roe.png tinted by colors[0], roe_overlay.png tinted by colors[1])
        Identifier roeModelId = ModelTemplates.TWO_LAYERED_ITEM.create(
                ModelLocationUtils.getModelLocation(WaterloggedBlocks.ROE.asItem()),
                TextureMapping.layered(
                        TextureMapping.getItemTexture(WaterloggedBlocks.ROE.asItem()),
                        TextureMapping.getItemTexture(WaterloggedBlocks.ROE.asItem(), "_overlay")),
                this.modelOutput);
        itemModels.itemModelOutput.accept(WaterloggedBlocks.ROE.asItem(),
                ItemModelUtils.tintedModel(roeModelId,
                        new CustomModelDataSource(0, 0xFFFFFF),
                        new CustomModelDataSource(1, 0xFFFFFF)));
        // Sea slug eggs: block model tinted by CustomModelData.colors[0]
        itemModels.itemModelOutput.accept(WaterloggedBlocks.SEA_SLUG_EGGS.asItem(),
                ItemModelUtils.tintedModel(blockModel("sea_slug_eggs"), new CustomModelDataSource(0, 0xFFFFFF)));

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
