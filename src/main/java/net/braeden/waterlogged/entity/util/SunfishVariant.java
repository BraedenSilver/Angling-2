package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum SunfishVariant implements StringRepresentable {
    BLUEGILL(0,                 "bluegill"),
    PUMPKINSEED(1,              "pumpkinseed"),
    REDBREAST(2,                "redbreast"),
    LONGEAR(3,                  "longear"),
    WARMOUTH(4,                 "warmouth"),
    GREEN(5,                    "green"),
    DIANSUS_DIANSUR(6,          "diansus_diansur"),
    BLUEGILL_PUMPKINSEED(7,     "bluegill_and_pumpkinseed_hybrid"),
    BLUEGILL_REDBREAST(8,       "bluegill_and_redbreast_hybrid");

    private static final SunfishVariant[] VALUES = values();

    /** Variants that can appear in the wild (excludes the nametag-only DIANSUS_DIANSUR). */
    public static final SunfishVariant[] WILD_VARIANTS = java.util.Arrays.stream(VALUES)
            .filter(v -> v != DIANSUS_DIANSUR)
            .toArray(SunfishVariant[]::new);

    private final int id;
    private final String textureName;

    SunfishVariant(int id, String textureName) {
        this.id = id;
        this.textureName = textureName;
    }

    public int getId() { return this.id; }

    public String getTextureName() { return this.textureName; }

    public static SunfishVariant byId(int id) {
        if (id < 0 || id >= VALUES.length) return BLUEGILL;
        return VALUES[id];
    }

    @Override
    public String getSerializedName() { return this.textureName; }
}
