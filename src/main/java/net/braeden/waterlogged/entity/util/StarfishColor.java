package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum StarfishColor implements StringRepresentable {
    RED(0,    "red",    0xFFE04040),
    BLUE(1,   "blue",   0xFF4070D0),
    ORANGE(2, "orange", 0xFFE88030),
    YELLOW(3, "yellow", 0xFFE8D840),
    PINK(4,   "pink",   0xFFE87098),
    RAINBOW(5, "rainbow", 0xFFFFFFFF); // Special cycling variant (like jeb_ sheep)

    private static final StarfishColor[] VALUES = values();
    // Non-rainbow colors used for rainbow cycling
    private static final StarfishColor[] RAINBOW_COLORS = {RED, BLUE, ORANGE, YELLOW, PINK};
    private static final int CYCLE_TICKS = 40; // Ticks per color in rainbow cycle

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
        // 1/100 chance for rainbow, otherwise normal colors
        if (random.nextInt(100) == 0) {
            return RAINBOW;
        }
        // Pick from non-rainbow colors
        return RAINBOW_COLORS[random.nextInt(RAINBOW_COLORS.length)];
    }

    /**
     * For RAINBOW starfish, get the current color based on game ticks.
     * For other colors, just return the static color.
     */
    public int getArgbForTicks(long gameTicks) {
        if (this == RAINBOW) {
            int colorIndex = (int) ((gameTicks / CYCLE_TICKS) % RAINBOW_COLORS.length);
            return RAINBOW_COLORS[colorIndex].argb;
        }
        return this.argb;
    }

    @Override
    public String getSerializedName() { return this.name; }
}
