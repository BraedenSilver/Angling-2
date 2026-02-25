package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum CrabVariant implements StringRepresentable {
    DUNGENESS(0, "dungeness", 0xFFB5651D),
    GHOST(1,     "ghost",     0xFFF0DEB0),
    BLUE_CLAW(2, "blue_claw", 0xFF5080AA),
    MOJANG(3,    "mojang",    0xFFD74E4E);

    private static final CrabVariant[] VALUES = values();

    private final int id;
    private final String name;
    private final int argb;

    CrabVariant(int id, String name, int argb) {
        this.id = id;
        this.name = name;
        this.argb = argb;
    }

    public int getId() {
        return this.id;
    }

    /** Packed ARGB color for tinting roe/fry. */
    public int getArgb() {
        return this.argb;
    }

    public static CrabVariant byId(int id) {
        if (id < 0 || id >= VALUES.length) return DUNGENESS;
        return VALUES[id];
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
