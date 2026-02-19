package net.braeden.angling2.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public final class AnglingEntityModelLayers {

    public static final ModelLayerLocation STARFISH = layer("starfish");
    public static final ModelLayerLocation ANEMONE = layer("anemone");
    public static final ModelLayerLocation FRY = layer("fry");
    public static final ModelLayerLocation SUNFISH = layer("sunfish");
    public static final ModelLayerLocation PELICAN = layer("pelican");
    public static final ModelLayerLocation SEA_SLUG = layer("sea_slug");
    public static final ModelLayerLocation CRAB = layer("crab");
    public static final ModelLayerLocation DONGFISH = layer("dongfish");
    public static final ModelLayerLocation CATFISH = layer("catfish");
    public static final ModelLayerLocation SEAHORSE = layer("seahorse");
    public static final ModelLayerLocation BUBBLE_EYE = layer("bubble_eye");
    public static final ModelLayerLocation ANOMALOCARIS = layer("anomalocaris");
    public static final ModelLayerLocation ANGLERFISH = layer("anglerfish");
    public static final ModelLayerLocation MAHI_MAHI = layer("mahi_mahi");

    private AnglingEntityModelLayers() {}

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath("angling", name), "main");
    }
}
