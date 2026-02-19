package net.braeden.angling2.entity.util;

import net.minecraft.util.StringRepresentable;

public enum CrabVariant implements StringRepresentable {
    DUNGENESS(0, "dungeness"),
    GHOST(1, "ghost"),
    BLUE_CLAW(2, "blue_claw");

    private static final CrabVariant[] VALUES = values();

    private final int id;
    private final String name;

    CrabVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
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
