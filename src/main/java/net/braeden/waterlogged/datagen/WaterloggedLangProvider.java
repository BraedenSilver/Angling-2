package net.braeden.waterlogged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class WaterloggedLangProvider extends FabricLanguageProvider {

    public WaterloggedLangProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, "en_us", registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {
        // Item group
        builder.add("itemGroup.waterlogged", "Waterlogged");

        // Entities
        builder.add("entity.waterlogged.fry", "Fry");
        builder.add("entity.waterlogged.sunfish", "Sunfish");
        builder.add("entity.waterlogged.pelican", "Pelican");
        builder.add("entity.waterlogged.sea_slug", "Sea Slug");
        builder.add("entity.waterlogged.crab", "Crab");
        builder.add("entity.waterlogged.dongfish", "Dongfish");
        builder.add("entity.waterlogged.catfish", "Catfish");
        builder.add("entity.waterlogged.seahorse", "Seahorse");
        builder.add("entity.waterlogged.bubble_eye", "Bubble Eye");
        builder.add("entity.waterlogged.anomalocaris", "Anomalocaris");
        builder.add("entity.waterlogged.anglerfish", "Anglerfish");
        builder.add("entity.waterlogged.mahi_mahi", "Mahi Mahi");
        builder.add("entity.waterlogged.orca", "Orca");
        builder.add("entity.waterlogged.right_whale", "Right Whale");

        // Sunfish variants
        builder.add("sunfish_variant.waterlogged.pumpkinseed", "Pumpkinseed");
        builder.add("sunfish_variant.waterlogged.longear", "Longear");
        builder.add("sunfish_variant.waterlogged.bluegill", "Bluegill");
        builder.add("sunfish_variant.waterlogged.redbreast", "Redbreast");
        builder.add("sunfish_variant.waterlogged.green", "Green");
        builder.add("sunfish_variant.waterlogged.warmouth", "Warmouth");
        builder.add("sunfish_variant.waterlogged.bluegill_and_redbreast_hybrid", "Bluegill and Redbreast Hybrid");
        builder.add("sunfish_variant.waterlogged.bluegill_and_pumpkinseed_hybrid", "Bluegill and Pumpkinseed Hybrid");
        builder.add("sunfish_variant.waterlogged.diansus_diansur", "Diansu's Diansur");

        // Sea slug colors
        builder.add("sea_slug_color.waterlogged.ivory", "Ivory");
        builder.add("sea_slug_color.waterlogged.onyx", "Onyx");
        builder.add("sea_slug_color.waterlogged.periwinkle", "Periwinkle");
        builder.add("sea_slug_color.waterlogged.burgundy", "Burgundy");
        builder.add("sea_slug_color.waterlogged.coquelicot", "Coquelicot");
        builder.add("sea_slug_color.waterlogged.gamboge", "Gamboge");
        builder.add("sea_slug_color.waterlogged.jade", "Jade");
        builder.add("sea_slug_color.waterlogged.midnight", "Midnight");
        builder.add("sea_slug_color.waterlogged.ultramarine", "Ultramarine");
        builder.add("sea_slug_color.waterlogged.coffee", "Coffee");
        builder.add("sea_slug_color.waterlogged.celeste", "Celeste");
        builder.add("sea_slug_color.waterlogged.eggplant", "Eggplant");
        builder.add("sea_slug_color.waterlogged.olivine", "Olivine");
        builder.add("sea_slug_color.waterlogged.pear", "Pear");
        builder.add("sea_slug_color.waterlogged.cyclamen", "Cyclamen");
        builder.add("sea_slug_color.waterlogged.amber", "Amber");
        builder.add("sea_slug_color.waterlogged.iris", "Iris");
        builder.add("sea_slug_color.waterlogged.orchid", "Orchid");
        builder.add("sea_slug_color.waterlogged.folly", "Folly");

        // Sea slug patterns
        builder.add("sea_slug_pattern.waterlogged.none", "None");
        builder.add("sea_slug_pattern.waterlogged.stripes", "Striped");
        builder.add("sea_slug_pattern.waterlogged.squiggles", "Squiggled");
        builder.add("sea_slug_pattern.waterlogged.spots", "Spotted");
        builder.add("sea_slug_pattern.waterlogged.rings", "Ringed");

        // Crab variants
        builder.add("crab_variant.waterlogged.dungeness", "Dungeness");
        builder.add("crab_variant.waterlogged.ghost", "Ghost");
        builder.add("crab_variant.waterlogged.blue_claw", "Blue Claw");
        builder.add("crab_variant.waterlogged.mojang", "Mojang");

        // Blocks and their items (block.* for the placed block, item.* for the BlockItem in inventory)
        builder.add("block.waterlogged.algae", "Algae");
        builder.add("item.waterlogged.algae", "Algae");
        builder.add("block.waterlogged.wormy_dirt", "Wormy Dirt");
        builder.add("item.waterlogged.wormy_dirt", "Wormy Dirt");
        builder.add("block.waterlogged.wormy_mud", "Wormy Mud");
        builder.add("item.waterlogged.wormy_mud", "Wormy Mud");
        builder.add("block.waterlogged.oysters", "Oysters");
        builder.add("item.waterlogged.oysters", "Oysters");
        builder.add("block.waterlogged.starfish", "Starfish");
        builder.add("item.waterlogged.starfish", "Starfish");
        builder.add("block.waterlogged.dead_starfish", "Dead Starfish");
        builder.add("item.waterlogged.dead_starfish", "Dead Starfish");
        builder.add("block.waterlogged.clam", "Clam");
        builder.add("item.waterlogged.clam", "Clam");
        builder.add("block.waterlogged.anemone", "Anemone");
        builder.add("item.waterlogged.anemone", "Anemone");
        builder.add("block.waterlogged.papyrus", "Papyrus");
        builder.add("item.waterlogged.papyrus", "Papyrus");
        builder.add("block.waterlogged.roe", "Roe");
        builder.add("item.waterlogged.roe", "Roe");
        builder.add("block.waterlogged.sea_slug_eggs", "Sea Slug Eggs");
        builder.add("item.waterlogged.sea_slug_eggs", "Sea Slug Eggs");
        builder.add("block.waterlogged.duckweed", "Duckweed");
        builder.add("item.waterlogged.duckweed", "Duckweed");
        builder.add("block.waterlogged.sargassum", "Sargassum");
        builder.add("item.waterlogged.sargassum", "Sargassum");
        builder.add("block.waterlogged.urchin", "Urchin");
        builder.add("item.waterlogged.urchin", "Urchin");
        builder.add("block.waterlogged.aquarium_glass", "Aquarium Glass");
        builder.add("item.waterlogged.aquarium_glass", "Aquarium Glass");

        // Spawn eggs
        builder.add("item.waterlogged.fry_spawn_egg", "Fry Spawn Egg");
        builder.add("item.waterlogged.sunfish_spawn_egg", "Sunfish Spawn Egg");
        builder.add("item.waterlogged.pelican_spawn_egg", "Pelican Spawn Egg");
        builder.add("item.waterlogged.sea_slug_spawn_egg", "Sea Slug Spawn Egg");
        builder.add("item.waterlogged.crab_spawn_egg", "Crab Spawn Egg");
        builder.add("item.waterlogged.dongfish_spawn_egg", "Dongfish Spawn Egg");
        builder.add("item.waterlogged.catfish_spawn_egg", "Catfish Spawn Egg");
        builder.add("item.waterlogged.seahorse_spawn_egg", "Seahorse Spawn Egg");
        builder.add("item.waterlogged.bubble_eye_spawn_egg", "Bubble Eye Spawn Egg");
        builder.add("item.waterlogged.anomalocaris_spawn_egg", "Anomalocaris Spawn Egg");
        builder.add("item.waterlogged.anglerfish_spawn_egg", "Anglerfish Spawn Egg");
        builder.add("item.waterlogged.mahi_mahi_spawn_egg", "Mahi Mahi Spawn Egg");
        builder.add("item.waterlogged.orca_spawn_egg", "Orca Spawn Egg");
        builder.add("item.waterlogged.right_whale_spawn_egg", "Right Whale Spawn Egg");

        // Bucket items
        builder.add("item.waterlogged.sunfish_bucket", "Bucket of Sunfish");
        builder.add("item.waterlogged.sea_slug_bucket", "Bucket of Sea Slug");
        builder.add("item.waterlogged.crab_bucket", "Bucket of Crab");
        builder.add("item.waterlogged.dongfish_bucket", "Bucket of Dongfish");
        builder.add("item.waterlogged.catfish_bucket", "Bucket of Catfish");
        builder.add("item.waterlogged.seahorse_bucket", "Bucket of Seahorse");
        builder.add("item.waterlogged.bubble_eye_bucket", "Bucket of Bubble Eye");
        builder.add("item.waterlogged.anomalocaris_bucket", "Bucket of Anomalocaris");
        builder.add("item.waterlogged.anglerfish_bucket", "Bucket of Anglerfish");
        builder.add("item.waterlogged.urchin_bucket", "Bucket of Urchin");
        builder.add("item.waterlogged.mahi_mahi_bucket", "Bucket of Mahi Mahi");
        builder.add("item.waterlogged.sea_slug_bucket.bioluminescent", "Bioluminescent");
        builder.add("item.waterlogged.fry_bucket", "Bucket of Fry");

        // Other items
        builder.add("item.waterlogged.fishing_net", "Fishing Net");
        builder.add("item.waterlogged.pearl", "Pearl");
        builder.add("item.waterlogged.worm", "Worm");
        builder.add("item.waterlogged.sunfish", "Sunfish");
        builder.add("item.waterlogged.fried_sunfish", "Fried Sunfish");
        builder.add("item.waterlogged.raw_mahi_mahi", "Raw Mahi Mahi");
        builder.add("item.waterlogged.cooked_mahi_mahi", "Cooked Mahi Mahi");
        builder.add("item.waterlogged.raw_crab_legs", "Raw Crab Legs");
        builder.add("item.waterlogged.cooked_crab_legs", "Cooked Crab Legs");
        builder.add("item.waterlogged.raw_catfish", "Raw Catfish");
        builder.add("item.waterlogged.cooked_catfish", "Cooked Catfish");
        builder.add("item.waterlogged.raw_bubble_eye", "Raw Bubble Eye");
        builder.add("item.waterlogged.cooked_bubble_eye", "Cooked Bubble Eye");
        builder.add("item.waterlogged.raw_anglerfish", "Raw Anglerfish");
        builder.add("item.waterlogged.cooked_anglerfish", "Cooked Anglerfish");

        // Sound subtitles
        builder.add("subtitles.item.worm.use", "Worm squirms");
        builder.add("subtitles.block.roe.hatch", "Fry hatches");
        builder.add("subtitles.block.sea_slug_eggs.hatch", "Sea Slug hatches");
        builder.add("subtitles.entity.sunfish.death", "Sunfish dies");
        builder.add("subtitles.entity.sunfish.flop", "Sunfish flops");
        builder.add("subtitles.entity.sunfish.hurt", "Sunfish hurts");
        builder.add("subtitles.entity.fry.death", "Fry dies");
        builder.add("subtitles.entity.fry.flop", "Fry flops");
        builder.add("subtitles.entity.fry.hurt", "Fry hurts");
        builder.add("subtitles.entity.pelican.death", "Pelican dies");
        builder.add("subtitles.entity.pelican.hurt", "Pelican hurts");
        builder.add("subtitles.entity.pelican.ambient", "Pelican squawks");
        builder.add("subtitles.entity.sea_slug.death", "Sea Slug dies");
        builder.add("subtitles.entity.sea_slug.hurt", "Sea Slug hurts");
        builder.add("subtitles.entity.crab.death", "Crab dies");
        builder.add("subtitles.entity.crab.hurt", "Crab hurts");
        builder.add("subtitles.entity.dongfish.death", "Dongfish dies");
        builder.add("subtitles.entity.dongfish.flop", "Dongfish flops");
        builder.add("subtitles.entity.dongfish.hurt", "Dongfish hurts");
        builder.add("subtitles.entity.dongfish.shear", "Dongfish has its horngus removed");
        builder.add("subtitles.entity.catfish.death", "Catfish dies");
        builder.add("subtitles.entity.catfish.flop", "Catfish flops");
        builder.add("subtitles.entity.catfish.hurt", "Catfish hurts");
        builder.add("subtitles.entity.seahorse.death", "Seahorse dies");
        builder.add("subtitles.entity.seahorse.flop", "Seahorse flops");
        builder.add("subtitles.entity.seahorse.hurt", "Seahorse hurts");
        builder.add("subtitles.entity.bubble_eye.death", "Bubble Eye dies");
        builder.add("subtitles.entity.bubble_eye.flop", "Bubble Eye flops");
        builder.add("subtitles.entity.bubble_eye.hurt", "Bubble Eye hurts");
        builder.add("subtitles.entity.anomalocaris.death", "Anomalocaris dies");
        builder.add("subtitles.entity.anomalocaris.flop", "Anomalocaris flops");
        builder.add("subtitles.entity.anomalocaris.hurt", "Anomalocaris hurts");
        builder.add("subtitles.entity.anglerfish.death", "Anglerfish dies");
        builder.add("subtitles.entity.anglerfish.flop", "Anglerfish flops");
        builder.add("subtitles.entity.anglerfish.hurt", "Anglerfish hurts");
        builder.add("subtitles.entity.mahi_mahi.death", "Mahi Mahi dies");
        builder.add("subtitles.entity.mahi_mahi.flop", "Mahi Mahi flops");
        builder.add("subtitles.entity.mahi_mahi.hurt", "Mahi Mahi hurts");
        builder.add("subtitles.entity.orca.death", "Orca dies");
        builder.add("subtitles.entity.orca.hurt", "Orca hurts");

        // Advancements
        builder.add("advancements.husbandry.put_worm_in_block.title", "A (Blocky) Can of Worms");
        builder.add("advancements.husbandry.put_worm_in_block.description", "Stick a Worm into some Dirt or Mud, where it can reproduce if buried");
        builder.add("advancements.husbandry.obtain_roe.title", "Uncured Caviar");
        builder.add("advancements.husbandry.obtain_roe.description", "Pick up Fish Roe, using Silk Touch");
        builder.add("advancements.husbandry.obtain_sea_slug_eggs.title", "Forbidden Cinnamon Roll");
        builder.add("advancements.husbandry.obtain_sea_slug_eggs.description", "Pick up Sea Slug Eggs, using Silk Touch");
        builder.add("advancements.husbandry.traded_with_pelican.title", "Paying the Bill");
        builder.add("advancements.husbandry.traded_with_pelican.description", "Trade the Fish inside a Pelican's Beak for another Fish");
    }
}
