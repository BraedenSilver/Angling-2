![Banner](banner.png)

# Waterlogged

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-62b47a?style=flat&logo=creeper&logoColor=white)
![Loader](https://img.shields.io/badge/Loader-Fabric-dbb68a?style=flat)
![Version](https://img.shields.io/badge/Version-1.0.1-6fa8dc?style=flat)
![Java](https://img.shields.io/badge/Java-21+-f6a623?style=flat&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-CC0--1.0-lightgrey?style=flat)

A Fabric mod for Minecraft 1.21.11 that expands and deepens the aquatic experience. Vanilla's oceans, rivers, and wetlands are a great foundation — but they're sparse. Waterlogged fills them with new life: creatures that behave differently, plants that grow naturally, food worth catching, and structures worth exploring.

---

## Mobs

### Fry

Small baby fish. Fry aren't found in the wild — they hatch from **Roe** (fish eggs) that are laid when two fish of the same species are both fed a **Worm**. After hatching, fry swim around freely and grow into the adult species of their parents after about 5 minutes. Their coloring and variant match their parents. All fry can be scooped up with a water bucket.

---

### Sunfish

*Spawns in:* Swamp

A small freshwater panfish that comes in **9 color variants**: Bluegill, Pumpkinseed, Redbreast, Longear, Warmouth, Green, Bluegill × Pumpkinseed hybrid, Bluegill × Redbreast hybrid, and Diansus Diansur. Feed two sunfish a **Worm** each to breed — they'll lay Roe that hatches into fry inheriting the parent's variant (with a 5% chance of mutation to a random variant). The **Diansus Diansur** variant never spawns naturally — name a sunfish "Diansu" with a name tag to reveal it.

**Drops:** Raw Sunfish | **Bucketable:** Yes (right-click with a Water Bucket)

---

### Catfish

*Spawns in:* Swamp

A bottom-feeding fish that actively seeks out and **eats nearby Algae blocks** as it swims. Feed two catfish a **Worm** each to breed.

**Drops:** Raw Catfish | **Bucketable:** Yes

---

### Dongfish

*Spawns in:* Underground water in most overworld biomes (Plains, Forest, Taiga, Swamp, Savanna, Desert, River, and more)

A cave fish found in subterranean pools and aquifers throughout most of the overworld. Spawns in small groups. Has a distinctive feature called a "horngus" — right-click with **Shears** to trim it off. The sheared state is permanent and is saved into its bucket. Feed two dongfish a **Worm** each to breed.

**Bucketable:** Yes

---

### Seahorse

*Spawns in:* Lukewarm Ocean, Deep Lukewarm Ocean

A slow, delicate fish that spawns in small groups in warm coastal waters. Feed two seahorses a **Worm** each to breed.

**Bucketable:** Yes

---

### Bubble Eye

*Spawns in:* River, Frozen River, Forest, Birch Forest, Old Growth Birch Forest, Dark Forest

A small freshwater fish found in rivers and forested inland biomes. Spawns in small groups. Feed two bubble eyes a **Worm** each to breed.

**Drops:** Raw Bubble Eye | **Bucketable:** Yes

---

### Mahi Mahi

*Spawns in:* Warm Ocean, Lukewarm Ocean, Deep Lukewarm Ocean

A large, fast-swimming open-water fish found in warm and temperate seas. Feed two mahi mahi a **Worm** each to breed.

**Drops:** Raw Mahi Mahi | **Bucketable:** Yes

---

### Anglerfish

*Spawns in:* Deep Ocean, Deep Cold Ocean, Deep Frozen Ocean, Deep Lukewarm Ocean

A deep-sea predator with a glowing lure. Found in all deep ocean variants. Feed two anglerfish a **Worm** each to breed.

**Drops:** Raw Anglerfish | **Bucketable:** Yes

---

### Anomalocaris

*Spawns in:* Mushroom Fields (underground water)

An ancient Cambrian arthropod that lives in underground water beneath mushroom island biomes. Feed two anomalocaris a **Worm** each to breed.

**Bucketable:** Yes

---

### Sea Slug

*Spawns in:* Warm Ocean

A vibrant nudibranch with a highly varied appearance. Each sea slug has a randomly assigned **color** (from 20 options) and **pattern** (from 5 options), for up to 100 possible combinations. On top of that, there's a 30% chance of bioluminescence — body glow, pattern glow, or both. Offspring inherit the parent's color and pattern. Feed two sea slugs a **Worm** each to breed. Sea slugs that hatch from eggs will never despawn.

**Bucketable:** Yes

---

### Crab

*Spawns in:* Beaches and coastal biomes (variant-dependent)

A scuttling crustacean that walks on land. Comes in **4 variants** tied to specific biomes:

- **Dungeness** — Beach
- **Ghost** — Swamp, Mangrove Swamp (rare, 5% chance)
- **Blue Claw** — Stony Shore
- **Mojang** — Swamp, Mangrove Swamp (rare, 5% chance)

**Drops:** Raw Crab Legs | **Bucketable:** Yes

---

### Orca

*Spawns in:* Cold Ocean, Deep Cold Ocean, Frozen Ocean, Deep Frozen Ocean

An apex predator. Orcas form **pods** — they swim together and alert each other when attacked. Adults actively **defend nearby calves**. **25% of orcas spawn as calves** that follow adults. Hostile toward players who threaten their pod.

**Bucketable:** No

---

### Right Whale

*Spawns in:* Deep Cold Ocean, Deep Frozen Ocean

A massive, peaceful filter feeder. Right whales can remain submerged for about 4 minutes before surfacing. They swim in loose groups and calves follow adults. **30% of right whales spawn as calves.**

Right whales spawn with varying levels of **barnacle coverage** (none, some, or full). Use **Shears** to remove one level per use, dropping **1–3 Nautilus Shells** each time.

**Bucketable:** No

---

### Pelican

*Spawns:* Coastal areas (custom spawner, despawns like a Wandering Trader)

A coastal seabird that actively **hunts and catches fish**, carrying them in its beak. While a pelican is holding a fish, you can give it a fish item to trade — releasing the caught fish. This triggers the *Paying the Bill* advancement.

---

## Breeding & the Worm System

Most fish in the mod can be bred using **Worms**. Right-click a fish with a worm to put it in breeding mode (heart particles appear). Do the same with a second fish of the same species nearby. Both parents must be fed separately. After breeding, they lay **Roe** — a small glowing block of fish eggs.

Roe hatches into **2–4 Fry** after about 5 minutes. The fry inherit the appearance of their parents. Each fry then grows into an adult of the parent species after another ~5 minutes.

To get worms, mine **Wormy Dirt** or **Wormy Mud** — see the Blocks section.

---

## Blocks

### Algae

Grows underwater on solid surfaces, similar to glow lichen. Spreads naturally via random ticks to adjacent water blocks and can be accelerated with **Bonemeal**. Breaks with any tool or bare hand.

Algae is the foundation of a small aquatic food chain: **Clams and Oysters eat algae to spread. Starfish eat clams and oysters to spread.** These blocks will naturally colonize the seafloor wherever algae grows.

### Clam

Found on sandy ocean floors. Clams feed on adjacent algae blocks — consuming them and spreading to a new nearby position. Breaking without Silk Touch has a **10% chance to drop a Pearl** (increases with **Fortune** — 30% at Fortune I, 50% at Fortune II, 70% at Fortune III). Use **Silk Touch** to collect the Clam block itself instead. Breaking also has a small chance to drop bonus XP, also scaled by Fortune.

### Oysters

Found in cold and temperate ocean biomes (Ocean, Deep Ocean, Cold Ocean, Deep Cold Ocean, Frozen Ocean, Deep Frozen Ocean). Same behavior as clams — feeds on adjacent algae to spread. Breaking without Silk Touch has a **10% chance to drop a Pearl** (increases with **Fortune**). Use **Silk Touch** to collect the Oysters block itself. Breaking also has a small chance to drop bonus XP, scaled by Fortune.

### Starfish

Found scattered on the seafloor. Starfish feed on adjacent clams and oysters to spread. Each starfish has a **color** assigned on placement, which is passed on when it spreads naturally. Drops itself when broken with anything.

### Dead Starfish

A bleached, inert version of starfish. No spreading behavior. Purely decorative.

### Roe

Fish eggs laid during breeding. Emits a soft glow and hatches into Fry after ~5 minutes. Requires **Silk Touch** to collect as an item — otherwise it is destroyed when broken. Collecting with Silk Touch unlocks the *Uncured Caviar* advancement.

### Sea Slug Eggs

Glowing egg clusters laid by breeding sea slugs. Requires **Silk Touch** to collect. Collecting them unlocks the *Forbidden Cinnamon Roll* advancement.

### Anemone

A sea anemone found on the ocean floor. Generates naturally. Decorative.

### Urchin

A spiny sea urchin on the seafloor. **Walking through an urchin deals 1 damage** (like a cactus) — only when moving horizontally. Pick up by right-clicking with an empty **Bucket** to get an Urchin Bucket. Generates in ocean biomes.

### Duckweed

A floating plant that rests on still freshwater surfaces. Breaks instantly. Can only be placed on water.

### Sargassum

Floating ocean seaweed. Breaks instantly. Can only be placed on water.

### Papyrus

A tall water-adjacent plant that generates in swamps and along riverbanks. Breaks with anything.

### Aquarium Glass

Clean, frameless glass for building aquariums. Mobs **cannot spawn** inside enclosed aquarium glass structures. Does not conduct redstone.

### Wormy Dirt

A dirt variant that generates underground in most common overworld biomes (Plains, Sunflower Plains, Forest, Birch Forest, Dark Forest, Taiga, Snowy Taiga, Meadow, Swamp). Mining it has a chance to drop a **Worm**. The primary way to obtain worms without a composter.

### Wormy Mud

A mud variant that generates underground in **Mangrove Swamps**. Mining it can yield a **Worm**. Same function as Wormy Dirt.

---

## Items

### Food

| Item | How to Get |
|------|------------|
| Sunfish | Dropped by Sunfish; chance from fishing |
| Fried Sunfish | Cook Sunfish in a furnace |
| Raw Catfish | Dropped by Catfish; chance from fishing |
| Cooked Catfish | Cook Raw Catfish |
| Raw Bubble Eye | Dropped by Bubble Eye; chance from fishing |
| Cooked Bubble Eye | Cook Raw Bubble Eye |
| Raw Mahi Mahi | Dropped by Mahi Mahi; chance from fishing |
| Cooked Mahi Mahi | Cook Raw Mahi Mahi |
| Raw Anglerfish | Dropped by Anglerfish; chance from fishing |
| Cooked Anglerfish | Cook Raw Anglerfish |
| Raw Crab Legs | Dropped by Crab |
| Cooked Crab Legs | Cook Raw Crab Legs |
| Worm | Mine Wormy Dirt or Wormy Mud — causes nausea if eaten |

All raw modded fish (except crab legs) have a **25% chance** to appear when fishing with a vanilla fishing rod.

### Tools

**Fishing Net** — An alternative to fishing rods. Left-click a fish to capture it and receive its raw drop directly. Durability: 131. Enchantable.

### Materials

**Pearl** — A crafting ingredient.

### Buckets

Right-click most fish with an empty **Water Bucket** to capture them. The resulting mob bucket preserves their color, variant, and state (a sheared Dongfish stays sheared; Fry keep their tint color). Place them back down from the bucket to release them. Urchin uses an empty Bucket instead of a Water Bucket. Mobs released from a bucket will **never despawn**. Giving any mob a name tag also prevents it from despawning.

Bucketable: Fry, Sunfish, Catfish, Dongfish, Seahorse, Bubble Eye, Anglerfish, Mahi Mahi, Anomalocaris, Sea Slug, Crab, Urchin.

---

## World Generation

| Feature | Biomes |
|---------|--------|
| Algae | Most water biomes |
| Duckweed | Freshwater biomes |
| Sargassum | Ocean biomes |
| Papyrus | Swamp, Mangrove Swamp, River |
| Clams | Shallow coastal biomes |
| Oysters | Ocean reef biomes |
| Starfish | Ocean floor biomes |
| Anemone | Ocean biomes |
| Urchin | Ocean biomes |
| Wormy Dirt | Common overworld biomes (underground) |
| Wormy Mud | Mangrove Swamp (underground) |
| Hydrothermal Vents | Deep ocean (rare) |

**Hydrothermal Vents** are rare structures that generate only in the deepest ocean water.

---

## Advancements

| Advancement | How to Get |
|-------------|------------|
| **A (Blocky) Can of Worms** | Place a Worm in Wormy Dirt or Wormy Mud |
| **Uncured Caviar** | Collect Roe with Silk Touch |
| **Forbidden Cinnamon Roll** | Collect Sea Slug Eggs with Silk Touch |
| **Paying the Bill** | Trade a fish with a Pelican while it's holding one |

---

## Credits

Made by **Braeden Silver**, **EightSidedSquare**, **Diansu**, and **Sillvia**.

Licensed under [CC0-1.0](https://creativecommons.org/publicdomain/zero/1.0/).
