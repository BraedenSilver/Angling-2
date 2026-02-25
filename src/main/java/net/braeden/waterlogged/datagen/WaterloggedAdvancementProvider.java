package net.braeden.waterlogged.datagen;

import net.braeden.waterlogged.block.WaterloggedBlocks;
import net.braeden.waterlogged.item.WaterloggedItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WaterloggedAdvancementProvider extends FabricAdvancementProvider {

    public WaterloggedAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> exporter) {
        var blocks = registries.lookupOrThrow(Registries.BLOCK);
        var items = registries.lookupOrThrow(Registries.ITEM);

        // Root advancement: put worm in block
        AdvancementHolder putWormInBlock = Advancement.Builder.advancement()
                .display(
                        WaterloggedItems.WORM,
                        Component.translatable("advancements.husbandry.put_worm_in_block.title"),
                        Component.translatable("advancements.husbandry.put_worm_in_block.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("put_worm_in_block",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location().setBlock(
                                        net.minecraft.advancements.criterion.BlockPredicate.Builder.block()
                                                .of(blocks, Blocks.DIRT, Blocks.MUD, WaterloggedBlocks.WORMY_DIRT, WaterloggedBlocks.WORMY_MUD)),
                                net.minecraft.advancements.criterion.ItemPredicate.Builder.item()
                                        .of(items, WaterloggedItems.WORM)))
                .build(Identifier.fromNamespaceAndPath("angling", "husbandry/put_worm_in_block"));
        exporter.accept(putWormInBlock);

        // Obtain roe (parent: put worm in block)
        AdvancementHolder obtainRoe = Advancement.Builder.advancement()
                .parent(putWormInBlock)
                .display(
                        WaterloggedItems.ROE,
                        Component.translatable("advancements.husbandry.obtain_roe.title"),
                        Component.translatable("advancements.husbandry.obtain_roe.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("roe", InventoryChangeTrigger.TriggerInstance.hasItems(WaterloggedItems.ROE))
                .build(Identifier.fromNamespaceAndPath("angling", "husbandry/obtain_roe"));
        exporter.accept(obtainRoe);

        // Obtain sea slug eggs (parent: obtain roe)
        AdvancementHolder obtainSeaSlugEggs = Advancement.Builder.advancement()
                .parent(obtainRoe)
                .display(
                        WaterloggedItems.SEA_SLUG_EGGS,
                        Component.translatable("advancements.husbandry.obtain_sea_slug_eggs.title"),
                        Component.translatable("advancements.husbandry.obtain_sea_slug_eggs.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("sea_slug_eggs", InventoryChangeTrigger.TriggerInstance.hasItems(WaterloggedItems.SEA_SLUG_EGGS))
                .build(Identifier.fromNamespaceAndPath("angling", "husbandry/obtain_sea_slug_eggs"));
        exporter.accept(obtainSeaSlugEggs);

        // traded_with_pelican is kept as hand-written JSON because it uses
        // a custom criterion trigger (angling:traded_with_pelican) that is
        // not yet registered as a Java CriterionTrigger.
    }
}
