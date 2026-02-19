package net.braeden.angling2.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class AnglingLangProvider extends FabricLanguageProvider {

    public AnglingLangProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, "en_us", registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {
        // Item group
        builder.add("itemGroup.angling", "Angling");

        // Entities
        builder.add("entity.angling.fry", "Fry");
        builder.add("entity.angling.sunfish", "Sunfish");
        builder.add("entity.angling.pelican", "Pelican");
        builder.add("entity.angling.sea_slug", "Sea Slug");
        builder.add("entity.angling.crab", "Crab");
        builder.add("entity.angling.dongfish", "Dongfish");
        builder.add("entity.angling.catfish", "Catfish");
        builder.add("entity.angling.seahorse", "Seahorse");
        builder.add("entity.angling.bubble_eye", "Bubble Eye");
        builder.add("entity.angling.anomalocaris", "Anomalocaris");
        builder.add("entity.angling.anglerfish", "Anglerfish");
        builder.add("entity.angling.mahi_mahi", "Mahi Mahi");

        // Sunfish variants
        builder.add("sunfish_variant.angling.pumpkinseed", "Pumpkinseed");
        builder.add("sunfish_variant.angling.longear", "Longear");
        builder.add("sunfish_variant.angling.bluegill", "Bluegill");
        builder.add("sunfish_variant.angling.redbreast", "Redbreast");
        builder.add("sunfish_variant.angling.green", "Green");
        builder.add("sunfish_variant.angling.warmouth", "Warmouth");
        builder.add("sunfish_variant.angling.bluegill_and_redbreast_hybrid", "Bluegill and Redbreast Hybrid");
        builder.add("sunfish_variant.angling.bluegill_and_pumpkinseed_hybrid", "Bluegill and Pumpkinseed Hybrid");
        builder.add("sunfish_variant.angling.diansus_diansur", "Diansu's Diansur");

        // Sea slug colors
        builder.add("sea_slug_color.angling.ivory", "Ivory");
        builder.add("sea_slug_color.angling.onyx", "Onyx");
        builder.add("sea_slug_color.angling.periwinkle", "Periwinkle");
        builder.add("sea_slug_color.angling.burgundy", "Burgundy");
        builder.add("sea_slug_color.angling.coquelicot", "Coquelicot");
        builder.add("sea_slug_color.angling.gamboge", "Gamboge");
        builder.add("sea_slug_color.angling.jade", "Jade");
        builder.add("sea_slug_color.angling.midnight", "Midnight");
        builder.add("sea_slug_color.angling.ultramarine", "Ultramarine");
        builder.add("sea_slug_color.angling.coffee", "Coffee");
        builder.add("sea_slug_color.angling.celeste", "Celeste");
        builder.add("sea_slug_color.angling.eggplant", "Eggplant");
        builder.add("sea_slug_color.angling.olivine", "Olivine");
        builder.add("sea_slug_color.angling.pear", "Pear");
        builder.add("sea_slug_color.angling.cyclamen", "Cyclamen");
        builder.add("sea_slug_color.angling.amber", "Amber");
        builder.add("sea_slug_color.angling.iris", "Iris");
        builder.add("sea_slug_color.angling.orchid", "Orchid");
        builder.add("sea_slug_color.angling.folly", "Folly");

        // Sea slug patterns
        builder.add("sea_slug_pattern.angling.none", "None");
        builder.add("sea_slug_pattern.angling.stripes", "Striped");
        builder.add("sea_slug_pattern.angling.squiggles", "Squiggled");
        builder.add("sea_slug_pattern.angling.spots", "Spotted");
        builder.add("sea_slug_pattern.angling.rings", "Ringed");

        // Crab variants
        builder.add("crab_variant.angling.dungeness", "Dungeness");
        builder.add("crab_variant.angling.ghost", "Ghost");
        builder.add("crab_variant.angling.blue_claw", "Blue Claw");

        // Block items (1.21.11 uses item.* keys for BlockItem.getDescriptionId())
        builder.add("item.angling.algae", "Algae");
        builder.add("item.angling.wormy_dirt", "Wormy Dirt");
        builder.add("item.angling.wormy_mud", "Wormy Mud");
        builder.add("item.angling.oysters", "Oysters");
        builder.add("item.angling.starfish", "Starfish");
        builder.add("item.angling.dead_starfish", "Dead Starfish");
        builder.add("item.angling.clam", "Clam");
        builder.add("item.angling.anemone", "Anemone");
        builder.add("item.angling.papyrus", "Papyrus");
        builder.add("item.angling.roe", "Roe");
        builder.add("item.angling.sea_slug_eggs", "Sea Slug Eggs");
        builder.add("item.angling.duckweed", "Duckweed");
        builder.add("item.angling.sargassum", "Sargassum");
        builder.add("item.angling.urchin", "Urchin");
        builder.add("block.angling.aquarium_glass", "Aquarium Glass");

        // Spawn eggs
        builder.add("item.angling.fry_spawn_egg", "Fry Spawn Egg");
        builder.add("item.angling.sunfish_spawn_egg", "Sunfish Spawn Egg");
        builder.add("item.angling.pelican_spawn_egg", "Pelican Spawn Egg");
        builder.add("item.angling.sea_slug_spawn_egg", "Sea Slug Spawn Egg");
        builder.add("item.angling.crab_spawn_egg", "Crab Spawn Egg");
        builder.add("item.angling.dongfish_spawn_egg", "Dongfish Spawn Egg");
        builder.add("item.angling.catfish_spawn_egg", "Catfish Spawn Egg");
        builder.add("item.angling.seahorse_spawn_egg", "Seahorse Spawn Egg");
        builder.add("item.angling.bubble_eye_spawn_egg", "Bubble Eye Spawn Egg");
        builder.add("item.angling.anomalocaris_spawn_egg", "Anomalocaris Spawn Egg");
        builder.add("item.angling.anglerfish_spawn_egg", "Anglerfish Spawn Egg");
        builder.add("item.angling.mahi_mahi_spawn_egg", "Mahi Mahi Spawn Egg");

        // Bucket items
        builder.add("item.angling.sunfish_bucket", "Bucket of Sunfish");
        builder.add("item.angling.sea_slug_bucket", "Bucket of Sea Slug");
        builder.add("item.angling.crab_bucket", "Bucket of Crab");
        builder.add("item.angling.dongfish_bucket", "Bucket of Dongfish");
        builder.add("item.angling.catfish_bucket", "Bucket of Catfish");
        builder.add("item.angling.seahorse_bucket", "Bucket of Seahorse");
        builder.add("item.angling.bubble_eye_bucket", "Bucket of Bubble Eye");
        builder.add("item.angling.anomalocaris_bucket", "Bucket of Anomalocaris");
        builder.add("item.angling.anglerfish_bucket", "Bucket of Anglerfish");
        builder.add("item.angling.urchin_bucket", "Bucket of Urchin");
        builder.add("item.angling.mahi_mahi_bucket", "Bucket of Mahi Mahi");
        builder.add("item.angling.sea_slug_bucket.bioluminescent", "Bioluminescent");
        builder.add("item.angling.fry_bucket", "Bucket of Fry");

        // Other items
        builder.add("item.angling.pearl", "Pearl");
        builder.add("item.angling.worm", "Worm");
        builder.add("item.angling.sunfish", "Sunfish");
        builder.add("item.angling.fried_sunfish", "Fried Sunfish");
        builder.add("item.angling.raw_mahi_mahi", "Raw Mahi Mahi");
        builder.add("item.angling.cooked_mahi_mahi", "Cooked Mahi Mahi");
        builder.add("item.angling.raw_crab_legs", "Raw Crab Legs");
        builder.add("item.angling.cooked_crab_legs", "Cooked Crab Legs");
        builder.add("item.angling.raw_catfish", "Raw Catfish");
        builder.add("item.angling.cooked_catfish", "Cooked Catfish");

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
