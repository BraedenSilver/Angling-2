package net.braeden.waterlogged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * Worldgen features (configured_feature + placed_feature) are managed as
 * hand-written JSON files under src/main/resources/data/angling/worldgen/
 * because FabricDynamicRegistryProvider cannot serialize placed features that
 * reference configured features added within the same datagen session.
 */
public class WaterloggedWorldGenProvider extends FabricDynamicRegistryProvider {

    public WaterloggedWorldGenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // No-op: all worldgen JSONs live in src/main/resources/data/angling/worldgen/
    }

    @Override
    public String getName() {
        return "Waterlogged World Gen";
    }
}
