# Angling

A Fabric mod for Minecraft 1.21.11 that expands aquatic content with new fish, critters, ocean flora, and a worm-based breeding system. Everything from humble sunfish in a river to orcas patrolling the deep ocean.

---

## Mobs

### Fry
A tiny juvenile fish hatched from a Roe block. Fry drift around in water and grow into their parent species after about 5 minutes. They inherit their parent's color, variant, and other traits.

### Sunfish
A small freshwater fish with 9 variants (Bluegill, Pumpkinseed, Redbreast, Longear, Warmouth, Green, and a few hybrids). Found in warm rivers and lakes. Can be caught in a water bucket. Can be bred with worms.

### Catfish
A medium-sized fish found in rivers and swamps. Can be caught in a bucket. Drops raw catfish. Breeds with worms.

### Dongfish
A small, rare freshwater fish. Spawns only 1–2 at a time in freshwater biomes. Can be bucketed. Breeds with worms. Can be sheared with shears to permanently remove its dong — the fish will remain dongless for the rest of its days.

### Seahorse
A small aquatic creature found in shallow warm water. Can be bucketed. Drops raw seahorse. Breeds with worms.

### Bubble Eye
A round, droopy-eyed fish found in deep water. Can be bucketed. Drops raw bubble eye. Breeds with worms.

### Anglerfish
A large deep-sea predator found in deep ocean biomes. Aggressive. Can be bucketed. Drops raw anglerfish. Breeds with worms.

### Anomalocaris
An ancient predator found in underground water at very low Y-levels. Has a long follow range and will come after you. Can be bucketed. Drops raw anomalocaris. Breeds with worms.

### Mahi-Mahi
A fast, large game fish found in warm open oceans. The fastest swimmer in the mod. Drops raw mahi-mahi. Breeds with worms.

### Sea Slug
A colorful aquatic critter found near coastlines. Comes in 6 colors (Red, Blue, Yellow, Green, Purple, Orange) and 5 patterns (Plain, Rings, Spots, Squiggles, Stripes). Can be bucketed. Breeds with worms and lays Sea Slug Eggs with inherited colors and patterns.

### Crab
A ground-dwelling crustacean found near water. 4 variants: Dungeness (most common), Ghost (cold biomes), Blue Claw (ocean biomes), and the rare Mojang crab (5% chance in swamps). Bred with worms like any vanilla animal — babies inherit the parent variant with a 5% chance to mutate. Drops raw crab legs.

### Pelican
A large flying bird that spawns periodically like a Wandering Trader. Always arrives holding a random mob in its beak — 80% chance of something common, 20% uncommon. Give it any fish item (vanilla or modded) and it will release the creature, show some hearts, then fly away and despawn after about 10 seconds. Cannot be bred.

### Orca
A massive apex predator found in deep ocean biomes. 30 HP, attacks for 6 damage, and never naturally despawns. Spawns in pods — adults stay within 20 blocks of each other, and calves follow the nearest adult. Adults will attack any player that gets within 8 blocks of a calf, and the whole pod retaliates if any member is hurt. Has a realistic breathing mechanic: orcas need to surface for air and will suffocate if kept underwater for too long (~2.5 minutes). Cannot be bucketed or bred.

---

## Blocks

### Wormy Dirt / Wormy Mud
Naturally generated in the world as a replacement for some dirt and mud patches. Mine with any tool to collect worms, which are used to breed fish and crabs.

### Roe
Spawned by breeding worm-compatible fish. Glows faintly. After about 5 minutes, it hatches into 2–4 Fry that grow into the parent species. Not craftable or placeable by hand — it appears when fish breed.

### Sea Slug Eggs
Spawned when two Sea Slugs breed. Hatches into Sea Slugs that inherit the parents' color and pattern. Like Roe, not craftable — only created through breeding.

### Duckweed
A small floating water plant. Spawns naturally on the surface of freshwater. No collision. Place it on water using the item.

### Sargassum
A yellowish floating seaweed found on ocean surfaces. Similar to duckweed but for saltwater. Place it on water using the item.

### Algae
A green plant that grows in water. Has random tick behavior — can flip between alive and dead states depending on conditions.

### Oysters
Rounded shell blocks found underwater. Decorative, with a satisfying shell sound. Has a block item.

### Starfish / Dead Starfish
Found on the ocean floor. Two variants: alive (animated) and dead. Both have block items and use shell sounds.

### Clam
A small, fragile shell block. Decorative. Found near water. Has a block item.

### Anemone
A soft, wiggly block found underwater. Uses a squishy slime sound. Has animated tentacles. Has a block item.

### Urchin
A spiky blue block found on the ocean floor. Has an animated block entity. Can only be placed using an Urchin Bucket — there is no standalone block item.

### Papyrus
A tall reed-like plant found near water. Uses an azalea leaf sound. Has a block item and decorative offset rendering.

### Aquarium Glass
A transparent glass block that does not suffocate entities or conduct redstone. Useful for building fish tanks or underwater viewing windows. Craftable like glass.

---

## Mechanics

### Worm Breeding
Most fish in this mod can be bred using worms. Worms are obtained by mining Wormy Dirt or Wormy Mud.

1. Right-click a compatible fish with a worm. Hearts will appear.
2. Right-click a second fish of the same species with a worm.
3. The two fish will swim toward each other and descend to the nearest solid surface below them.
4. A Roe block (or Sea Slug Eggs, for Sea Slugs) is placed at that location.
5. The Roe hatches after ~5 minutes into 2–4 Fry.
6. Fry grow into adults after another ~5 minutes, inheriting parent traits.

Fish that support worm breeding: Sunfish, Catfish, Dongfish, Seahorse, Bubble Eye, Anglerfish, Anomalocaris, Mahi-Mahi, Sea Slug.

Each fish has a ~5-minute cooldown after being fed a worm before it can breed again.

### Crab Breeding
Crabs use vanilla animal breeding. Feed two crabs worms and they will produce a baby crab directly (not through a Roe block). The baby inherits one parent's variant, with a 5% chance of a random mutation. Variant selection is also influenced by the biome.

### Pelican Trading
Pelicans spawn periodically in the world, similar to Wandering Traders. Each pelican spawns holding a random mob — common mobs 80% of the time, uncommon ones 20%. To trade, hand the pelican any fish item (including vanilla fish). It will release the held creature (at 50% scale and initially passive), play a sound, show hearts, and then fly away and despawn roughly 10 seconds later.

### Sunfish Variants
Sunfish come in 9 variants and can produce hybrid offspring. Fry inherit the parent's variant and maintain it into adulthood. Variants are data-driven and can be extended via data packs.

### Sea Slug Color Genetics
Sea Slugs have independent color (6 options) and pattern (5 options) traits. When two Sea Slugs breed, their Sea Slug Eggs store both parents' color and pattern. The hatched young inherit these traits.

### Orca Pod Behavior
Orcas are social. Adults stay in formation and calves trail behind. If a calf is threatened (player within 8 blocks), nearby adults attack. If any orca in a group is struck, all others in the pod turn hostile. Orcas also need to breathe — they drain air ticks while submerged and must surface to recover.

### Shearing a Dongfish
Right-click a Dongfish with shears to remove its dong. The change is permanent — it saves with the entity and persists across sessions. A sheared Dongfish can still breed normally, it just lacks its characteristic protrusion.

### Worm (Food Item)
You can eat worms. They restore 1 hunger but inflict 10 seconds of Nausea. Not recommended.

---

## Original README & License

### Original README

> Feel free to make expansions upon this mod! A lot of stuff is data driven, such as fish that can be bred, mobs that pelicans spawn with, etc.

### License

This mod is released under **CC0 1.0 Universal** (Creative Commons Zero). In plain terms: the author has waived all copyright and related rights to this work worldwide. You are free to copy, modify, distribute, and use it for any purpose — commercial or otherwise — without asking permission or providing attribution. No warranties are made. See the `LICENSE` file for the full legal text.
