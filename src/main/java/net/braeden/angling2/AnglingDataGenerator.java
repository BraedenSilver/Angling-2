package net.braeden.angling2;

import net.braeden.angling2.datagen.AnglingAdvancementProvider;
import net.braeden.angling2.datagen.AnglingBiomeTagProvider;
import net.braeden.angling2.datagen.AnglingBlockLootTableProvider;
import net.braeden.angling2.datagen.AnglingBlockTagProvider;
import net.braeden.angling2.datagen.AnglingEntityLootTableProvider;
import net.braeden.angling2.datagen.AnglingEntityTypeTagProvider;
import net.braeden.angling2.datagen.AnglingLangProvider;
import net.braeden.angling2.datagen.AnglingModelProvider;
import net.braeden.angling2.datagen.AnglingRecipeProvider;
import net.braeden.angling2.datagen.AnglingWorldGenProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AnglingDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();
        pack.addProvider(AnglingLangProvider::new);
        pack.addProvider(AnglingRecipeProvider::new);
        pack.addProvider(AnglingBlockTagProvider::new);
        pack.addProvider(AnglingEntityTypeTagProvider::new);
        pack.addProvider(AnglingBiomeTagProvider::new);
        pack.addProvider(AnglingBlockLootTableProvider::new);
        pack.addProvider(AnglingEntityLootTableProvider::new);
        pack.addProvider(AnglingWorldGenProvider::new);
        pack.addProvider(AnglingModelProvider::new);
        pack.addProvider(AnglingAdvancementProvider::new);
    }
}
