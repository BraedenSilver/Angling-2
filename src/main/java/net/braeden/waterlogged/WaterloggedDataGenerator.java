package net.braeden.waterlogged;

import net.braeden.waterlogged.datagen.WaterloggedAdvancementProvider;
import net.braeden.waterlogged.datagen.WaterloggedBiomeTagProvider;
import net.braeden.waterlogged.datagen.WaterloggedBlockLootTableProvider;
import net.braeden.waterlogged.datagen.WaterloggedBlockTagProvider;
import net.braeden.waterlogged.datagen.WaterloggedEntityLootTableProvider;
import net.braeden.waterlogged.datagen.WaterloggedEntityTypeTagProvider;
import net.braeden.waterlogged.datagen.WaterloggedLangProvider;
import net.braeden.waterlogged.datagen.WaterloggedModelProvider;
import net.braeden.waterlogged.datagen.WaterloggedRecipeProvider;
import net.braeden.waterlogged.datagen.WaterloggedWorldGenProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WaterloggedDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();
        pack.addProvider(WaterloggedLangProvider::new);
        pack.addProvider(WaterloggedRecipeProvider::new);
        pack.addProvider(WaterloggedBlockTagProvider::new);
        pack.addProvider(WaterloggedEntityTypeTagProvider::new);
        pack.addProvider(WaterloggedBiomeTagProvider::new);
        pack.addProvider(WaterloggedBlockLootTableProvider::new);
        pack.addProvider(WaterloggedEntityLootTableProvider::new);
        pack.addProvider(WaterloggedWorldGenProvider::new);
        pack.addProvider(WaterloggedModelProvider::new);
        pack.addProvider(WaterloggedAdvancementProvider::new);
    }
}
