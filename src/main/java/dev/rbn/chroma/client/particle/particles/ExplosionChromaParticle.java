package dev.rbn.chroma.client.particle.particles;

import dev.rbn.chroma.client.particle.ChromaParticleFactory;
import dev.rbn.chroma.client.particle.ChromaParticleType;
import dev.rbn.chroma.client.particle.SimpleChromaParticle;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class ExplosionChromaParticle extends SimpleChromaParticle {
    public ExplosionChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity) {
        super(type, position, velocity, 0, 0);
        RandomSource source = RandomSource.create();

        int age = source.nextInt(10);
        float scale = source.nextFloat() * 3;

        this.setColor(1.0F, 0.5F, 0.25F, 0.5F);

        this.setScale(scale);
        this.setMaxAge(age);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 1.0F - ((float) this.getAge() / this.getMaxAge());
        this.velocity = this.velocity.multiply(0.8F, 0.8F, 0.8F);

        this.scale = this.scale * 1.2F;
    }

    public static class Factory implements ChromaParticleFactory<ExplosionChromaParticle> {
        @Override
        public ExplosionChromaParticle create(ChromaParticleType<ExplosionChromaParticle> type, Vec3 position, Vec3 velocity) {
            return new ExplosionChromaParticle(type, position, velocity);
        }
    }
}
