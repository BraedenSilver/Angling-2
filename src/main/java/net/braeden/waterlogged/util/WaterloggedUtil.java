package net.braeden.waterlogged.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.List;

public class WaterloggedUtil {

    public static <T> List<T> getTagValues(Level level, TagKey<T> tagKey) {
        return level.registryAccess().lookupOrThrow(tagKey.registry()).get(tagKey).map(entries -> entries.stream().map(Holder::value).toList()).orElse(List.of());
    }

    public static <T> T getRandomTagValue(Level level, TagKey<T> tagKey, RandomSource random) {
        return Util.getRandom(getTagValues(level, tagKey), random);
    }

    public static boolean runningSodium() {
        return FabricLoader.getInstance().isModLoaded("sodium");
    }

    public static void stripEntityNbt(CompoundTag nbt) {
        // Yes, this is cursed. No, I'm not sorry.
        nbt.remove("AbsorptionAmount");
        nbt.remove("Air");
        nbt.remove("ArmorDropChances");
        nbt.remove("ArmorItems");
        nbt.remove("Attributes");
        nbt.remove("CanPickUpLoot");
        nbt.remove("DeathTime");
        nbt.remove("FallDistance");
        nbt.remove("FallFlying");
        nbt.remove("Fire");
        nbt.remove("FromBucket");
        nbt.remove("HandDropChances");
        nbt.remove("HandItems");
        nbt.remove("Health");
        nbt.remove("HurtByTimestamp");
        nbt.remove("HurtTime");
        nbt.remove("Invulnerable");
        nbt.remove("LeftHanded");
        nbt.remove("Motion");
        nbt.remove("OnGround");
        nbt.remove("PersistenceRequired");
        nbt.remove("PortalCooldown");
        nbt.remove("Pos");
        nbt.remove("Rotation");
        nbt.remove("cardinal_components");
        nbt.remove("UUID");
    }

    public static boolean pairsAreEqual(Object a1, Object a2, Object b1, Object b2) {
        return (a1.equals(b1) && a2.equals(b2)) || (a1.equals(b2) && a2.equals(b1));
    }

}
