package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.block.PapyrusBlock;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class WaterloggedBlockLootTableProvider extends FabricBlockLootTableProvider {

    public WaterloggedBlockLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        // Starfish/Dead starfish: drop self, copying CUSTOM_DATA (color) from block entity to item
        add(WaterloggedBlocks.STARFISH, createDropWithCustomData(WaterloggedBlocks.STARFISH));
        add(WaterloggedBlocks.DEAD_STARFISH, createDropWithCustomData(WaterloggedBlocks.DEAD_STARFISH));
        dropWhenSilkTouch(WaterloggedBlocks.AQUARIUM_GLASS);

        // Anemone: shears only
        add(WaterloggedBlocks.ANEMONE, createShearsOnlyDrop(WaterloggedBlocks.ANEMONE.asItem()));

        // Shears-only drops
        add(WaterloggedBlocks.DUCKWEED, createShearsOnlyDrop(WaterloggedItems.DUCKWEED));
        add(WaterloggedBlocks.SARGASSUM, createShearsOnlyDrop(WaterloggedItems.SARGASSUM));

        // Algae: shears only
        add(WaterloggedBlocks.ALGAE, createShearsOnlyDrop(WaterloggedBlocks.ALGAE.asItem()));

        // Urchin: no drop
        add(WaterloggedBlocks.URCHIN, noDrop());

        // Silk-touch-only drops, copying CUSTOM_DATA (color/parent data) from block entity to item
        add(WaterloggedBlocks.ROE, createSilkTouchDropWithCustomData(WaterloggedBlocks.ROE));
        add(WaterloggedBlocks.SEA_SLUG_EGGS, createSilkTouchDropWithCustomData(WaterloggedBlocks.SEA_SLUG_EGGS));

        // Wormy dirt: silk touch → self, otherwise → dirt
        add(WaterloggedBlocks.WORMY_DIRT, createSilkTouchDispatchTable(
                WaterloggedBlocks.WORMY_DIRT,
                this.applyExplosionCondition(WaterloggedBlocks.WORMY_DIRT,
                        LootItem.lootTableItem(Items.DIRT))));

        // Wormy mud: silk touch → self, otherwise → mud
        add(WaterloggedBlocks.WORMY_MUD, createSilkTouchDispatchTable(
                WaterloggedBlocks.WORMY_MUD,
                this.applyExplosionCondition(WaterloggedBlocks.WORMY_MUD,
                        LootItem.lootTableItem(Items.MUD))));

        // Clam: silk touch → self, otherwise → chance for pearl
        add(WaterloggedBlocks.CLAM, pearlDropLoot(WaterloggedBlocks.CLAM, WaterloggedBlocks.CLAM.asItem()));

        // Oysters: same as clam
        add(WaterloggedBlocks.OYSTERS, pearlDropLoot(WaterloggedBlocks.OYSTERS, WaterloggedBlocks.OYSTERS.asItem()));

        // Papyrus: age-based drops, fortune bonus with hoe, basic drops without hoe
        add(WaterloggedBlocks.PAPYRUS, papyrusLoot());
    }

    /** Drops the block always, copying color components from the block entity to the item. */
    private LootTable.Builder createDropWithCustomData(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyComponentsFunction
                                        .copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                        .include(DataComponents.CUSTOM_DATA)
                                        .include(DataComponents.CUSTOM_MODEL_DATA))));
    }

    /** Drops the block only with silk touch, copying color components from the block entity to the item. */
    private LootTable.Builder createSilkTouchDropWithCustomData(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .when(hasSilkTouch())
                                .apply(CopyComponentsFunction
                                        .copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                        .include(DataComponents.CUSTOM_DATA)
                                        .include(DataComponents.CUSTOM_MODEL_DATA))));
    }

    private LootTable.Builder pearlDropLoot(Block block, net.minecraft.world.level.ItemLike selfItem) {
        // Silk touch → drop self, otherwise → random chance for pearl (boosted by looting)
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                                // Silk touch → drop self
                                LootItem.lootTableItem(selfItem)
                                        .when(hasSilkTouch()),
                                // Otherwise → fortune-boosted pearl chance
                                LootItem.lootTableItem(WaterloggedItems.PEARL)
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries, 0.1f, 0.2f)))));
    }

    private LootTable.Builder papyrusLoot() {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        HolderLookup.RegistryLookup<net.minecraft.world.item.Item> items = this.registries.lookupOrThrow(Registries.ITEM);
        LootTable.Builder builder = LootTable.lootTable();

        // For each age, add a pool: with hoe → fortune bonus, without hoe → base drops
        for (int age = 0; age <= PapyrusBlock.MAX_AGE; age++) {
            int baseCount = age + 1;

            // Pool: hoe + this age → papyrus with fortune bonus
            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(MatchTool.toolMatches(
                            ItemPredicate.Builder.item()
                                    .of(items, ItemTags.HOES)))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(WaterloggedBlocks.PAPYRUS)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PapyrusBlock.AGE, age)))
                    .add(LootItem.lootTableItem(WaterloggedBlocks.PAPYRUS)
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                    enchantments.getOrThrow(Enchantments.FORTUNE), 0.5f, baseCount))
                            .apply(ApplyExplosionDecay.explosionDecay())));

            // Pool: no hoe + this age → base papyrus drops
            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(MatchTool.toolMatches(
                            ItemPredicate.Builder.item()
                                    .of(items, ItemTags.HOES))
                            .invert())
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(WaterloggedBlocks.PAPYRUS)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PapyrusBlock.AGE, age)))
                    .add(LootItem.lootTableItem(WaterloggedBlocks.PAPYRUS)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(baseCount)))
                            .apply(ApplyExplosionDecay.explosionDecay())));
        }

        return builder;
    }
}
