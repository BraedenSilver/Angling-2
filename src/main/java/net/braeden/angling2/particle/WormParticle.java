package net.braeden.angling2.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

public class WormParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;

    protected WormParticle(ClientLevel level, double x, double y, double z,
                           SpriteSet spriteSet, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
        this.spriteSet = spriteSet;
        this.quadSize = 0.3f;
        this.setLifetime(200);
    }

    @Override
    public void tick() {
        if (this.age <= 10) {
            this.xd = 0; this.yd = 0.023f; this.zd = 0;
        } else if (this.age >= this.lifetime - 10) {
            this.xd = 0; this.yd = -0.023f; this.zd = 0;
        } else {
            this.xd = 0; this.yd = 0; this.zd = 0;
        }
        super.tick();
        BlockPos below = new BlockPos((int)this.x, (int)(this.y - 0.5), (int)this.z);
        if (!Block.isFaceFull(this.level.getBlockState(below).getShape(this.level, below), Direction.UP)) {
            this.remove();
        }
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double vx, double vy, double vz, RandomSource random) {
            TextureAtlasSprite sprite = this.spriteSet.get(random);
            return new WormParticle(level, x, y, z, this.spriteSet, sprite);
        }
    }
}
