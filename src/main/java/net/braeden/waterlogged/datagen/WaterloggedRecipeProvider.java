package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.item.WaterloggedItems;
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

public class WaterloggedRecipeProvider extends FabricRecipeProvider {

    public WaterloggedRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Waterlogged Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
        return new RecipeProvider(registries, exporter) {
            @Override
            public void buildRecipes() {
                // Fried sunfish (smelting)
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.SUNFISH),
                        RecipeCategory.FOOD,
                        WaterloggedItems.FRIED_SUNFISH,
                        0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.SUNFISH), has(WaterloggedItems.SUNFISH))
                        .save(output);

                // Fried sunfish (smoking)
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.SUNFISH),
                        RecipeCategory.FOOD,
                        WaterloggedItems.FRIED_SUNFISH,
                        0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.SUNFISH), has(WaterloggedItems.SUNFISH))
                        .save(output, "waterlogged:fried_sunfish_from_smoking");

                // Fried sunfish (campfire cooking)
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.SUNFISH),
                        RecipeCategory.FOOD,
                        WaterloggedItems.FRIED_SUNFISH,
                        0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.SUNFISH), has(WaterloggedItems.SUNFISH))
                        .save(output, "waterlogged:fried_sunfish_from_campfire_cooking");

                // Cooked mahi mahi
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_MAHI_MAHI, 0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.RAW_MAHI_MAHI), has(WaterloggedItems.RAW_MAHI_MAHI))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_MAHI_MAHI, 0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.RAW_MAHI_MAHI), has(WaterloggedItems.RAW_MAHI_MAHI))
                        .save(output, "waterlogged:cooked_mahi_mahi_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.RAW_MAHI_MAHI),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_MAHI_MAHI, 0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.RAW_MAHI_MAHI), has(WaterloggedItems.RAW_MAHI_MAHI))
                        .save(output, "waterlogged:cooked_mahi_mahi_from_campfire_cooking");

                // Cooked crab legs
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CRAB_LEGS, 0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CRAB_LEGS), has(WaterloggedItems.RAW_CRAB_LEGS))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CRAB_LEGS, 0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CRAB_LEGS), has(WaterloggedItems.RAW_CRAB_LEGS))
                        .save(output, "waterlogged:cooked_crab_legs_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.RAW_CRAB_LEGS),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CRAB_LEGS, 0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CRAB_LEGS), has(WaterloggedItems.RAW_CRAB_LEGS))
                        .save(output, "waterlogged:cooked_crab_legs_from_campfire_cooking");

                // Cooked catfish
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.RAW_CATFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CATFISH, 0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CATFISH), has(WaterloggedItems.RAW_CATFISH))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.RAW_CATFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CATFISH, 0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CATFISH), has(WaterloggedItems.RAW_CATFISH))
                        .save(output, "waterlogged:cooked_catfish_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.RAW_CATFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_CATFISH, 0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.RAW_CATFISH), has(WaterloggedItems.RAW_CATFISH))
                        .save(output, "waterlogged:cooked_catfish_from_campfire_cooking");

                // Cooked bubble eye
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.RAW_BUBBLE_EYE),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_BUBBLE_EYE, 0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.RAW_BUBBLE_EYE), has(WaterloggedItems.RAW_BUBBLE_EYE))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.RAW_BUBBLE_EYE),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_BUBBLE_EYE, 0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.RAW_BUBBLE_EYE), has(WaterloggedItems.RAW_BUBBLE_EYE))
                        .save(output, "waterlogged:cooked_bubble_eye_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.RAW_BUBBLE_EYE),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_BUBBLE_EYE, 0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.RAW_BUBBLE_EYE), has(WaterloggedItems.RAW_BUBBLE_EYE))
                        .save(output, "waterlogged:cooked_bubble_eye_from_campfire_cooking");

                // Cooked anglerfish
                SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(WaterloggedItems.RAW_ANGLERFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_ANGLERFISH, 0.35f, 150
                ).unlockedBy(getHasName(WaterloggedItems.RAW_ANGLERFISH), has(WaterloggedItems.RAW_ANGLERFISH))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(
                        Ingredient.of(WaterloggedItems.RAW_ANGLERFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_ANGLERFISH, 0.35f, 75
                ).unlockedBy(getHasName(WaterloggedItems.RAW_ANGLERFISH), has(WaterloggedItems.RAW_ANGLERFISH))
                        .save(output, "waterlogged:cooked_anglerfish_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(
                        Ingredient.of(WaterloggedItems.RAW_ANGLERFISH),
                        RecipeCategory.FOOD, WaterloggedItems.COOKED_ANGLERFISH, 0.35f, 450
                ).unlockedBy(getHasName(WaterloggedItems.RAW_ANGLERFISH), has(WaterloggedItems.RAW_ANGLERFISH))
                        .save(output, "waterlogged:cooked_anglerfish_from_campfire_cooking");

                // Paper from papyrus (shaped: 3 papyrus → 1 paper)
                shaped(RecipeCategory.MISC, Items.PAPER)
                        .pattern("###")
                        .define('#', WaterloggedBlocks.PAPYRUS)
                        .unlockedBy(getHasName(WaterloggedBlocks.PAPYRUS.asItem()), has(WaterloggedBlocks.PAPYRUS.asItem()))
                        .save(output, "waterlogged:paper_from_papyrus");
                
                // Fishing Net (sticks + string)
                shaped(RecipeCategory.TOOLS, WaterloggedItems.FISHING_NET)
                        .pattern("  S")
                        .pattern(" ST")
                        .pattern("STT")
                        .define('S', Items.STICK)
                        .define('T', Items.STRING)
                        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                        .save(output);

                // Aquarium Glass (Pearls + Glass)
                // Assuming shapeless 8 glass + 1 pearl ? Or 4 glass + 1 pearl? Or 1 glass + 1 pearl?
                // User said "you get aquarium glass with pearls and glass. only works with glass blocks."
                // Common mod pattern: 8 glass around 1 pearl -> 8 aquarium glass.
                shaped(RecipeCategory.BUILDING_BLOCKS, WaterloggedBlocks.AQUARIUM_GLASS, 8)
                        .pattern("GGG")
                        .pattern("GPG")
                        .pattern("GGG")
                        .define('G', Items.GLASS)
                        .define('P', WaterloggedItems.PEARL)
                        .unlockedBy(getHasName(WaterloggedItems.PEARL), has(WaterloggedItems.PEARL))
                        .save(output);
            }
        };
    }
}
