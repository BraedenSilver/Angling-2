package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum SeaSlugPattern implements StringRepresentable {
    PLAIN(0,     "plain",     null),
    RINGS(1,     "rings",     "rings"),
    SPOTS(2,     "spots",     "spots"),
    SQUIGGLES(3, "squiggles", "squiggles"),
    STRIPES(4,   "stripes",   "stripes");

    private static final SeaSlugPattern[] VALUES = values();

    private final int id;
    private final String name;
    /** Texture file name under textures/entity/sea_slug/, or null for PLAIN. */
    private final String textureName;

    SeaSlugPattern(int id, String name, String textureName) {
        this.id = id;
        this.name = name;
        this.textureName = textureName;
    }

    public int getId() { return this.id; }

    /** Returns the texture file name (no path/extension), or null if PLAIN. */
    public String getTextureName() { return this.textureName; }

    public static SeaSlugPattern byId(int id) {
        if (id < 0 || id >= VALUES.length) return PLAIN;
        return VALUES[id];
    }

    @Override
    public String getSerializedName() { return this.name; }
}
