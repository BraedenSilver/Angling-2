package net.braeden.angling2.datagen;

import net.braeden.angling2.block.AnglingBlocks;
import net.braeden.angling2.item.AnglingItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class AnglingRecipeProvider extends FabricRecipeProvider {

    public AnglingRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Angling Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
        return new RecipeProvider(registries, exporter) {
            @Override
            public void buildRecipes() {
                // Fried sunfish (smelting)
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(AnglingItems.SUNFISH),
                        RecipeCategory.FOOD,
                        AnglingItems.FRIED_SUNFISH,
                        0.35f, 150
                ).unlockedBy(getHasName(AnglingItems.SUNFISH), has(AnglingItems.SUNFISH))
                        .save(output);

                // Fried sunfish (smoking)
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(AnglingItems.SUNFISH),
                        RecipeCategory.FOOD,
                        AnglingItems.FRIED_SUNFISH,
                        0.35f, 75
                ).unlockedBy(getHasName(AnglingItems.SUNFISH), has(AnglingItems.SUNFISH))
                        .save(output, "angling:fried_sunfish_from_smoking");

                // Fried sunfish (campfire cooking)
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(AnglingItems.SUNFISH),
                        RecipeCategory.FOOD,
                        AnglingItems.FRIED_SUNFISH,
                        0.35f, 450
                ).unlockedBy(getHasName(AnglingItems.SUNFISH), has(AnglingItems.SUNFISH))
                        .save(output, "angling:fried_sunfish_from_campfire_cooking");

                // Cooked mahi mahi
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(AnglingItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, AnglingItems.COOKED_MAHI_MAHI, 0.35f, 150
                ).unlockedBy(getHasName(AnglingItems.RAW_MAHI_MAHI), has(AnglingItems.RAW_MAHI_MAHI))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(AnglingItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, AnglingItems.COOKED_MAHI_MAHI, 0.35f, 75
                ).unlockedBy(getHasName(AnglingItems.RAW_MAHI_MAHI), has(AnglingItems.RAW_MAHI_MAHI))
                        .save(output, "angling:cooked_mahi_mahi_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(AnglingItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, AnglingItems.COOKED_MAHI_MAHI, 0.35f, 450
                ).unlockedBy(getHasName(AnglingItems.RAW_MAHI_MAHI), has(AnglingItems.RAW_MAHI_MAHI))
                        .save(output, "angling:cooked_mahi_mahi_from_campfire_cooking");

                // Cooked crab legs
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(AnglingItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CRAB_LEGS, 0.35f, 150
                ).unlockedBy(getHasName(AnglingItems.RAW_CRAB_LEGS), has(AnglingItems.RAW_CRAB_LEGS))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(AnglingItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CRAB_LEGS, 0.35f, 75
                ).unlockedBy(getHasName(AnglingItems.RAW_CRAB_LEGS), has(AnglingItems.RAW_CRAB_LEGS))
                        .save(output, "angling:cooked_crab_legs_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(AnglingItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CRAB_LEGS, 0.35f, 450
                ).unlockedBy(getHasName(AnglingItems.RAW_CRAB_LEGS), has(AnglingItems.RAW_CRAB_LEGS))
                        .save(output, "angling:cooked_crab_legs_from_campfire_cooking");

                // Cooked catfish
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(AnglingItems.RAW_CATFISH),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CATFISH, 0.35f, 150
                ).unlockedBy(getHasName(AnglingItems.RAW_CATFISH), has(AnglingItems.RAW_CATFISH))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(AnglingItems.RAW_CATFISH),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CATFISH, 0.35f, 75
                ).unlockedBy(getHasName(AnglingItems.RAW_CATFISH), has(AnglingItems.RAW_CATFISH))
                        .save(output, "angling:cooked_catfish_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(AnglingItems.RAW_CATFISH),
                        RecipeCategory.FOOD, AnglingItems.COOKED_CATFISH, 0.35f, 450
                ).unlockedBy(getHasName(AnglingItems.RAW_CATFISH), has(AnglingItems.RAW_CATFISH))
                        .save(output, "angling:cooked_catfish_from_campfire_cooking");

                // Paper from papyrus (shaped: 3 papyrus → 1 paper)
                shaped(RecipeCategory.MISC, Items.PAPER)
                        .pattern("###")
                        .define('#', AnglingBlocks.PAPYRUS)
                        .unlockedBy(getHasName(AnglingBlocks.PAPYRUS.asItem()), has(AnglingBlocks.PAPYRUS.asItem()))
                        .save(output, "angling:paper_from_papyrus");
                
                // Aquarium Glass (Pearls + Glass)
                // Assuming shapeless 8 glass + 1 pearl ? Or 4 glass + 1 pearl? Or 1 glass + 1 pearl?
                // User said "you get aquarium glass with pearls and glass. only works with glass blocks."
                // Common mod pattern: 8 glass around 1 pearl -> 8 aquarium glass.
                shaped(RecipeCategory.BUILDING_BLOCKS, AnglingBlocks.AQUARIUM_GLASS, 8)
                        .pattern("GGG")
                        .pattern("GPG")
                        .pattern("GGG")
                        .define('G', Items.GLASS)
                        .define('P', AnglingItems.PEARL)
                        .unlockedBy(getHasName(AnglingItems.PEARL), has(AnglingItems.PEARL))
                        .save(output);
            }
        };
    }
}
