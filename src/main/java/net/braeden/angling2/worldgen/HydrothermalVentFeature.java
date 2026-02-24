package net.braeden.angling2.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class HydrothermalVentFeature extends Feature<NoneFeatureConfiguration> {

    public HydrothermalVentFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        if (!level.getFluidState(origin).is(FluidTags.WATER)) return false;
        BlockPos floor = origin.below();
        if (!level.getBlockState(floor).isFaceSturdy(level, floor, Direction.UP)) return false;

        // Place the primary vent
        placeVent(level, origin, random);

        // Cluster 1–3 additional vents nearby, mimicking a real vent field
        int clusterCount = 1 + random.nextInt(3);
        for (int i = 0; i < clusterCount; i++) {
            int offX = random.nextInt(19) - 9;  // ±9 blocks
            int offZ = random.nextInt(19) - 9;
            // Scan vertically to find the ocean floor at this horizontal offset
            BlockPos clusterOrigin = null;
            for (int y = origin.getY() + 5; y >= origin.getY() - 10; y--) {
                BlockPos candidate = new BlockPos(origin.getX() + offX, y, origin.getZ() + offZ);
                BlockPos candidateFloor = candidate.below();
                if (level.getFluidState(candidate).is(FluidTags.WATER)
                        && level.getBlockState(candidateFloor).isFaceSturdy(level, candidateFloor, Direction.UP)) {
                    clusterOrigin = candidate;
                    break;
                }
            }
            if (clusterOrigin == null) continue;
            placeVent(level, clusterOrigin, random);
        }

        return true;
    }

    private void placeVent(WorldGenLevel level, BlockPos origin, RandomSource random) {
        ImprovedNoise warpNoise = new ImprovedNoise(random);
        ImprovedNoise materialNoise = new ImprovedNoise(random);
        boolean atDeepslate = origin.getY() < 8;

        int coneHeight;
        float baseOuterR;
        float baseInnerR;

        if (random.nextBoolean()) {
            // Don Quixote: tall and narrow
            coneHeight  = 14 + random.nextInt(8);           // 14–21 blocks tall
            baseOuterR  = 1.8f + random.nextFloat() * 0.7f; // 1.8–2.5 radius
            baseInnerR  = 0.3f + random.nextFloat() * 0.3f; // very narrow hollow
        } else {
            // Sancho Panza: short and wide
            coneHeight  = 8 + random.nextInt(6);            // 8–13 blocks tall
            baseOuterR  = 3.5f + random.nextFloat() * 1.5f; // 3.5–5.0 radius
            baseInnerR  = 1.5f + random.nextFloat() * 1.0f; // wide hollow mound
        }

        // Never let the tip break the water surface
        int maxHeight = level.getSeaLevel() - origin.getY() - 1;
        coneHeight = Math.max(2, Math.min(coneHeight, maxHeight));

        buildCone(level, origin, random, warpNoise, materialNoise, coneHeight, baseOuterR, baseInnerR, atDeepslate);
        buildFoundation(level, origin, materialNoise, baseOuterR, atDeepslate);
    }

    // For every block the cone placed at dy=0, fill straight down until solid ground,
    // preventing water from sneaking under the structure on slopes or cliff edges.
    private void buildFoundation(WorldGenLevel level, BlockPos origin, ImprovedNoise materialNoise,
                                  float baseOuterR, boolean deepslate) {
        int scan = (int) Math.ceil(baseOuterR) + 2;
        for (int dx = -scan; dx <= scan; dx++) {
            for (int dz = -scan; dz <= scan; dz++) {
                BlockPos surface = origin.offset(dx, 0, dz);
                BlockState surfaceState = level.getBlockState(surface);
                // Skip positions where the cone placed nothing (still water or air)
                if (surfaceState.isAir() || level.getFluidState(surface).is(FluidTags.WATER)) continue;
                // Descend until we hit something solid
                for (int dy = -1; dy > -64; dy--) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState existing = level.getBlockState(pos);
                    if (!existing.isAir() && !level.getFluidState(pos).is(FluidTags.WATER)) break;
                    level.setBlock(pos, selectInnerMaterial(materialNoise, pos), 2);
                }
            }
        }
    }

    private void buildCone(WorldGenLevel level, BlockPos origin, RandomSource random,
                            ImprovedNoise warpNoise, ImprovedNoise materialNoise,
                            int coneHeight, float baseOuterR, float baseInnerR, boolean deepslate) {
        for (int dy = 0; dy < coneHeight; dy++) {
            float t = dy / (float) coneHeight;
            // Linear taper: base is widest, tip narrows to a narrow spire
            float outerR = lerp(baseOuterR, 0.8f, t);
            // Inner hollow tapers to a narrow opening so the top always has a hole
            float innerR = lerp(baseInnerR, 0.7f, t);

            boolean isLavaLayer = dy == 0;
            // dy=1 is the sealed ceiling of the lava chamber: filled solid to stop water ingress.
            // The center block becomes magma to drive a bubble column up through the hollow above.
            boolean isSealLayer = dy == 1;

            double driftX = warpNoise.noise(origin.getX() * 0.08 + dy * 0.35, origin.getY() * 0.08, 0.0) * 0.4;
            double driftZ = warpNoise.noise(0.0, origin.getY() * 0.08 + dy * 0.35, origin.getZ() * 0.08) * 0.4;
            int scan = (int) Math.ceil(outerR) + 2;

            for (int dx = -scan; dx <= scan; dx++) {
                for (int dz = -scan; dz <= scan; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    double cx = dx - driftX;
                    double cz = dz - driftZ;
                    double dist = Math.sqrt(cx * cx + cz * cz);

                    // Scale warp to current radius so thin upper sections never fully disappear
                    double warpMag = Math.min(0.75, outerR * 0.25);
                    double warp = warpNoise.noise(pos.getX() * 0.21, pos.getY() * 0.28, pos.getZ() * 0.21) * warpMag;
                    double effOuter = outerR + warp;

                    // Use unwarped innerR for the lava and seal layers so coverage is guaranteed —
                    // no gaps can form between the two sealed surfaces
                    double effInner = (isLavaLayer || isSealLayer)
                            ? innerR
                            : Math.max(0.0, innerR + warp * 0.35);

                    if (dist < effInner) {
                        if (isLavaLayer) {
                            level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 2);
                        } else if (isSealLayer) {
                            // Magma at center → bubble column source; solid material elsewhere
                            level.setBlock(pos,
                                    dist < 0.5
                                            ? Blocks.MAGMA_BLOCK.defaultBlockState()
                                            : selectMaterial(materialNoise, pos, deepslate),
                                    2);
                        }
                        // dy >= 2: hollow, ocean water fills naturally
                    } else if (dist < effOuter) {
                        // Suppress decay in the upper cone (prevents floating blocks) and when the
                        // wall is thin (protects the chimney from being eaten away entirely)
                        boolean suppressDecay = t > 0.4f || (effOuter - effInner) < 1.5;
                        // Inner 50% of wall always solid; outer 50% decays probabilistically
                        double wallDepth = (effOuter - dist) / Math.max(0.01, effOuter - effInner);
                        if (!suppressDecay && wallDepth < 0.5 && random.nextDouble() > Math.pow(wallDepth * 2.0, 0.35)) continue;
                        // Innermost face is cobblestone/stone (solidified lava contact); outer body noise-driven
                        double innerDist = dist - effInner;
                        level.setBlock(pos,
                                innerDist < 0.9
                                        ? selectInnerMaterial(materialNoise, pos)
                                        : selectMaterial(materialNoise, pos, deepslate),
                                2);
                    }
                }
            }
        }
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    // Inner lining: cobblestone and stone, as if lava solidified on contact with ocean water
    private BlockState selectInnerMaterial(ImprovedNoise noise, BlockPos pos) {
        double n = noise.noise(pos.getX() * 0.18, pos.getY() * 0.18, pos.getZ() * 0.18);
        return n < 0.0 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.STONE.defaultBlockState();
    }

    private BlockState selectMaterial(ImprovedNoise noise, BlockPos pos, boolean deepslate) {
        double n = noise.noise(pos.getX() * 0.14, pos.getY() * 0.14, pos.getZ() * 0.14);
        if (n < -0.2) return deepslate ? Blocks.DEEPSLATE.defaultBlockState() : Blocks.STONE.defaultBlockState();
        return Blocks.BASALT.defaultBlockState();
    }
}
