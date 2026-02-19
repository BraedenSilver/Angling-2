package net.braeden.angling2.datagen;

import net.braeden.angling2.entity.AnglingEntities;
import net.braeden.angling2.item.AnglingItems;
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

public class AnglingEntityLootTableProvider extends FabricEntityLootTableProvider {

    public AnglingEntityLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        // Empty loot tables
        add(AnglingEntities.FRY,         LootTable.lootTable());
        add(AnglingEntities.SEA_SLUG,    LootTable.lootTable());
        add(AnglingEntities.SEAHORSE,    LootTable.lootTable());
        add(AnglingEntities.BUBBLE_EYE,  LootTable.lootTable());
        add(AnglingEntities.ANOMALOCARIS, LootTable.lootTable());
        add(AnglingEntities.ANGLERFISH,  LootTable.lootTable());
        add(AnglingEntities.DONGFISH,    LootTable.lootTable());

        // Crab: drops raw crab legs (cooked if on fire)
        add(AnglingEntities.CRAB, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AnglingItems.RAW_CRAB_LEGS)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Catfish: drops raw catfish (cooked if on fire)
        add(AnglingEntities.CATFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AnglingItems.RAW_CATFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Mahi Mahi: drops raw mahi mahi (cooked if on fire)
        add(AnglingEntities.MAHI_MAHI, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AnglingItems.RAW_MAHI_MAHI)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot())))));

        // Pelican: 1-2 feathers with looting bonus
        add(AnglingEntities.PELICAN, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                        registries, UniformGenerator.between(0, 1))))));

        // Sunfish: drops sunfish (smelted if on fire), 5% chance of bone_meal
        add(AnglingEntities.SUNFISH, LootTable.lootTable()
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AnglingItems.SUNFISH)
                                .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot()))))
                .withPool(new LootPool.Builder()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))));
    }
}
