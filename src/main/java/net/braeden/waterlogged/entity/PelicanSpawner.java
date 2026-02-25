package net.braeden.waterlogged.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Spawns pelicans near players in a wandering-trader-like fashion.
 * Also has a chance to spawn pelicans near fishing bobbers.
 */
public class PelicanSpawner {
    private static final int TICK_INTERVAL = 1200;          // Check every 60 seconds
    private static final int DEFAULT_SPAWN_DELAY = 24000;   // 20 minutes between spawn attempts
    private static final int MIN_SPAWN_CHANCE = 15;
    private static final int MAX_SPAWN_CHANCE = 50;
    private static final int SPAWN_CHANCE_INCREASE = 10;
    private static final int SPAWN_ATTEMPTS = 10;
    private static final int SPAWN_RANGE = 48;
    private static final int DESPAWN_TIME = 36000;          // 30 minutes
    private static final int FISHING_CHECK_INTERVAL = 600;  // Check fishing hooks every 30 seconds
    private static final int FISHING_SPAWN_CHANCE = 15;     // 15% chance per fishing check

    private final RandomSource random = RandomSource.create();
    private int tickDelay = TICK_INTERVAL;
    private int spawnDelay = DEFAULT_SPAWN_DELAY;
    private int spawnChance = MIN_SPAWN_CHANCE;
    private int fishingTickDelay = FISHING_CHECK_INTERVAL;

    public void spawn(ServerLevel level, boolean monsterSpawning, boolean animalSpawning) {
        // Ambient spawning (wandering-trader style)
        if (--tickDelay <= 0) {
            tickDelay = TICK_INTERVAL;
            spawnDelay -= TICK_INTERVAL;

            if (spawnDelay <= 0) {
                spawnDelay = DEFAULT_SPAWN_DELAY;
                int chance = spawnChance;
                spawnChance = Mth.clamp(spawnChance + SPAWN_CHANCE_INCREASE, MIN_SPAWN_CHANCE, MAX_SPAWN_CHANCE);

                if (random.nextInt(100) < chance) {
                    if (trySpawnNearRandomPlayer(level)) {
                        spawnChance = MIN_SPAWN_CHANCE;
                    }
                }
            }
        }

        // Fishing hook spawning
        if (--fishingTickDelay <= 0) {
            fishingTickDelay = FISHING_CHECK_INTERVAL;
            trySpawnNearFishingHooks(level);
        }
    }

    private boolean trySpawnNearRandomPlayer(ServerLevel level) {
        Player player = level.getRandomPlayer();
        if (player == null) return true; // No players, consider "successful" to not accumulate chance
        if (random.nextInt(10) != 0) return false; // 1 in 10 chance per attempt

        BlockPos playerPos = player.blockPosition();
        BlockPos spawnPos = findSpawnPosition(level, playerPos, SPAWN_RANGE);

        if (spawnPos != null && hasEnoughSpace(level, spawnPos)) {
            return spawnPelican(level, spawnPos);
        }
        return false;
    }

    private void trySpawnNearFishingHooks(ServerLevel level) {
        for (Player player : level.players()) {
            if (player.fishing != null) {
                FishingHook hook = player.fishing;
                BlockPos hookPos = hook.blockPosition();

                if (random.nextInt(100) < FISHING_SPAWN_CHANCE) {
                    BlockPos spawnPos = findSpawnPosition(level, hookPos, 24);
                    if (spawnPos != null && hasEnoughSpace(level, spawnPos)) {
                        // Check no pelican already nearby
                        List<PelicanEntity> nearby = level.getEntitiesOfClass(PelicanEntity.class,
                                new AABB(hookPos).inflate(32));
                        if (nearby.isEmpty()) {
                            spawnPelican(level, spawnPos);
                        }
                    }
                }
            }
        }
    }

    private boolean spawnPelican(ServerLevel level, BlockPos pos) {
        PelicanEntity pelican = WaterloggedEntities.PELICAN.create(level, EntitySpawnReason.EVENT);
        if (pelican != null) {
            pelican.snapTo((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, random.nextFloat() * 360.0f, 0.0f);
            pelican.finalizeSpawn(level, level.getCurrentDifficultyAt(pos),
                    EntitySpawnReason.EVENT, null);
            pelican.setDespawnDelay(DESPAWN_TIME);
            level.addFreshEntity(pelican);
            return true;
        }
        return false;
    }

    private BlockPos findSpawnPosition(ServerLevel level, BlockPos center, int range) {
        for (int attempt = 0; attempt < SPAWN_ATTEMPTS; attempt++) {
            int x = center.getX() + random.nextInt(range * 2) - range;
            int z = center.getZ() + random.nextInt(range * 2) - range;
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
            BlockPos candidate = new BlockPos(x, y, z);

            // Prefer spawning near water or on beaches
            if (hasEnoughSpace(level, candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean hasEnoughSpace(BlockGetter blockGetter, BlockPos pos) {
        for (BlockPos checkPos : BlockPos.betweenClosed(pos, pos.offset(0, 2, 0))) {
            if (!blockGetter.getBlockState(checkPos).getCollisionShape(blockGetter, checkPos).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
