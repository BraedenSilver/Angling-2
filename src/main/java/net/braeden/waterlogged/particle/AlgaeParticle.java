package net.braeden.waterlogged.particle;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class AlgaeParticle extends SingleQuadParticle {
    protected AlgaeParticle(ClientLevel level, double x, double y, double z,
                             double vx, double vy, double vz, TextureAtlasSprite sprite) {
        super(level, x, y, z, vx, vy, vz, sprite);
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.setLifetime(this.random.nextInt(61) + 60);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed && !this.level.getFluidState(new BlockPos((int)this.x, (int)this.y, (int)this.z)).is(FluidTags.WATER)) {
            this.remove();
        }
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double vx, double vy, double vz, RandomSource random) {
            TextureAtlasSprite sprite = this.sprites.get(random);
            AlgaeParticle particle = new AlgaeParticle(level, x, y, z, vx, vy, vz, sprite);
            int color = Util.getRandom(ImmutableList.of(0x79ab25, 0x5d9621, 0x41801c, 0x246a17), random);
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;
            particle.setColor(r, g, b);
            particle.setAlpha(0.5f);
            return particle;
        }
    }
}
