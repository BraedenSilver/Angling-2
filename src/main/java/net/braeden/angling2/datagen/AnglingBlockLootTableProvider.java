package net.braeden.angling2.datagen;

import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.block.PapyrusBlock;
import net.braeden.angling2.item.AnglingItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class AnglingBlockLootTableProvider extends FabricBlockLootTableProvider {

    public AnglingBlockLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        // Simple drop-self tables
        dropSelf(AnglingBlocks.DEAD_STARFISH);
        dropSelf(AnglingBlocks.STARFISH);
        dropWhenSilkTouch(AnglingBlocks.AQUARIUM_GLASS);

        // Anemone: shears only
        add(AnglingBlocks.ANEMONE, createShearsOnlyDrop(AnglingBlocks.ANEMONE.asItem()));

        // Shears-only drops
        add(AnglingBlocks.DUCKWEED, createShearsOnlyDrop(AnglingItems.DUCKWEED));
        add(AnglingBlocks.SARGASSUM, createShearsOnlyDrop(AnglingItems.SARGASSUM));

        // Algae: shears only
        add(AnglingBlocks.ALGAE, createShearsOnlyDrop(AnglingBlocks.ALGAE.asItem()));

        // No drop
        add(AnglingBlocks.URCHIN, noDrop());

        // Silk-touch-only drops
        add(AnglingBlocks.ROE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(hasSilkTouch())
                        .add(LootItem.lootTableItem(AnglingItems.ROE))));
        add(AnglingBlocks.SEA_SLUG_EGGS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(hasSilkTouch())
                        .add(LootItem.lootTableItem(AnglingItems.SEA_SLUG_EGGS))));

        // Wormy dirt: silk touch → self, otherwise → dirt
        add(AnglingBlocks.WORMY_DIRT, createSilkTouchDispatchTable(
                AnglingBlocks.WORMY_DIRT,
                this.applyExplosionCondition(AnglingBlocks.WORMY_DIRT,
                        LootItem.lootTableItem(Items.DIRT))));

        // Wormy mud: silk touch → self, otherwise → mud
        add(AnglingBlocks.WORMY_MUD, createSilkTouchDispatchTable(
                AnglingBlocks.WORMY_MUD,
                this.applyExplosionCondition(AnglingBlocks.WORMY_MUD,
                        LootItem.lootTableItem(Items.MUD))));

        // Clam: silk touch → self, otherwise → chance for pearl
        add(AnglingBlocks.CLAM, pearlDropLoot(AnglingBlocks.CLAM, AnglingBlocks.CLAM.asItem()));

        // Oysters: same as clam
        add(AnglingBlocks.OYSTERS, pearlDropLoot(AnglingBlocks.OYSTERS, AnglingBlocks.OYSTERS.asItem()));

        // Papyrus: age-based drops, fortune bonus with hoe, basic drops without hoe
        add(AnglingBlocks.PAPYRUS, papyrusLoot());
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
                                LootItem.lootTableItem(AnglingItems.PEARL)
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
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AnglingBlocks.PAPYRUS)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PapyrusBlock.AGE, age)))
                    .add(LootItem.lootTableItem(AnglingBlocks.PAPYRUS)
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
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AnglingBlocks.PAPYRUS)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PapyrusBlock.AGE, age)))
                    .add(LootItem.lootTableItem(AnglingBlocks.PAPYRUS)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(baseCount)))
                            .apply(ApplyExplosionDecay.explosionDecay())));
        }

        return builder;
    }
}
