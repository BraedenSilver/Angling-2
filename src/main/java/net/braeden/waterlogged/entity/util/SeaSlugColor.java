package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum SeaSlugColor implements StringRepresentable {
    RED(0,    "red",    0xFFE54040),
    BLUE(1,   "blue",   0xFF4060D0),
    YELLOW(2, "yellow", 0xFFE8E040),
    GREEN(3,  "green",  0xFF40C840),
    PURPLE(4, "purple", 0xFF9040D0),
    ORANGE(5, "orange", 0xFFE07830);

    private static final SeaSlugColor[] VALUES = values();

    private final int id;
    private final String name;
    private final int argb;

    SeaSlugColor(int id, String name, int argb) {
        this.id = id;
        this.name = name;
        this.argb = argb;
    }

    public int getId() { return this.id; }

    /** Packed ARGB color to tint the sea slug body texture. */
    public int getArgb() { return this.argb; }

    public static SeaSlugColor byId(int id) {
        if (id < 0 || id >= VALUES.length) return RED;
        return VALUES[id];
    }

    @Override
    public String getSerializedName() { return this.name; }
}
