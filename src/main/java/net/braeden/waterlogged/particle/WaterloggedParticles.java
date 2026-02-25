package net.braeden.waterlogged.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import static net.braeden.waterlogged.WaterloggedMod.MOD_ID;

public class WaterloggedParticles {

    public static SimpleParticleType ALGAE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "algae"), FabricParticleTypes.simple(true));
    public static SimpleParticleType WORM = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "worm"), FabricParticleTypes.simple(true));

    public static void init() {

    }

}
