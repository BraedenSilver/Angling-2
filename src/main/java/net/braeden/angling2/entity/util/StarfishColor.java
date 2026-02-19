package net.braeden.angling2.entity.util;

import net.minecraft.util.StringRepresentable;

public enum StarfishColor implements StringRepresentable {
    RED(0,    "red",    0xFFE04040),
    BLUE(1,   "blue",   0xFF4070D0),
    ORANGE(2, "orange", 0xFFE88030),
    YELLOW(3, "yellow", 0xFFE8D840),
    PINK(4,   "pink",   0xFFE87098);

    private static final StarfishColor[] VALUES = values();

    private final int id;
    private final String name;
    private final int argb;

    StarfishColor(int id, String name, int argb) {
        this.id = id;
        this.name = name;
        this.argb = argb;
    }

    public int getId() { return this.id; }

    /** Packed ARGB color used to tint the grayscale starfish texture. */
    public int getArgb() { return this.argb; }

    public static StarfishColor byId(int id) {
        if (id < 0 || id >= VALUES.length) return RED;
        return VALUES[id];
    }

    public static StarfishColor random(net.minecraft.util.RandomSource random) {
        return VALUES[random.nextInt(VALUES.length)];
    }

    @Override
    public String getSerializedName() { return this.name; }
}
