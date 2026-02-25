package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.entity.WaterloggedEntities;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class WaterloggedEntityLootTableProvider extends FabricEntityLootTableProvider {

    public WaterloggedEntityLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        // Empty loot tables
        add(WaterloggedEntities.FRY,         LootTable.lootTable());
        add(WaterloggedEntities.SEA_SLUG,    LootTable.lootTable());

        // Dongfish: drops raw dongfish (cooked if on fire)
        add(WaterloggedEntities.DONGFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_DONGFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Seahorse: drops raw seahorse (cooked if on fire)
        add(WaterloggedEntities.SEAHORSE, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_SEAHORSE)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Bubble Eye: drops raw bubble eye (cooked if on fire)
        add(WaterloggedEntities.BUBBLE_EYE, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_BUBBLE_EYE)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Anomalocaris: no drops
        add(WaterloggedEntities.ANOMALOCARIS, LootTable.lootTable());

        // Anglerfish: drops raw anglerfish (cooked if on fire)
        add(WaterloggedEntities.ANGLERFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_ANGLERFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Crab: drops raw crab legs (cooked if on fire)
        add(WaterloggedEntities.CRAB, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_CRAB_LEGS)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Catfish: drops raw catfish (cooked if on fire)
        add(WaterloggedEntities.CATFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_CATFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Mahi Mahi: drops raw mahi mahi (cooked if on fire)
        add(WaterloggedEntities.MAHI_MAHI, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.RAW_MAHI_MAHI)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Pelican: 1-2 feathers with looting bonus
        add(WaterloggedEntities.PELICAN, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                        registries, UniformGenerator.between(0, 1))))));

        // Sunfish: drops sunfish (smelted if on fire), 5% chance of bone_meal
        add(WaterloggedEntities.SUNFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WaterloggedItems.SUNFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot()))))
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))));
    }
}
