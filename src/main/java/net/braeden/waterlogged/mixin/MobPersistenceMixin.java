package net.braeden.waterlogged.mixin;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Ensures mobs become persistent when released from a mob bucket (EntitySpawnReason.BUCKET).
 * Note: name tag persistence is already handled by vanilla's NameTagItem.
 */
@Mixin(Mob.class)
public class MobPersistenceMixin {

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void waterlogged_persistOnBucket(
            ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason reason, @Nullable SpawnGroupData groupData,
            CallbackInfoReturnable<SpawnGroupData> cir) {
        if (reason == EntitySpawnReason.BUCKET) {
            ((Mob) (Object) this).setPersistenceRequired();
        }
    }
}
