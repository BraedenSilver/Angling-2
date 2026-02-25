package net.braeden.waterlogged.entity.util;

import net.minecraft.util.StringRepresentable;

public enum SeaSlugBioluminescence implements StringRepresentable {
    NONE(0, "none"),
    BODY(1, "body"),
    PATTERN(2, "pattern"),
    BOTH(3, "both");

    private final int id;
    private final String name;

    private static final SeaSlugBioluminescence[] BY_ID = values();

    SeaSlugBioluminescence(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public static SeaSlugBioluminescence byId(int id) {
        if (id < 0 || id >= BY_ID.length) return NONE;
        return BY_ID[id];
    }

    @Override
    public String getSerializedName() { return name; }
}
