package dev.rbn.chroma.client.particle.particles;

import dev.rbn.chroma.client.particle.ChromaParticleFactory;
import dev.rbn.chroma.client.particle.ChromaParticleType;
import dev.rbn.chroma.client.particle.SimpleChromaParticle;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class ExplosionSparkChromaParticle extends SimpleChromaParticle {
    public ExplosionSparkChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity) {
        super(type, position, velocity, 0, 0);
        RandomSource source = RandomSource.create();

        int age = source.nextInt(20);
        float scale = source.nextFloat() * 3;

        this.setColor(1.0F, 0.5F, 0F, 0.1F);

        this.setScale(scale);
        this.setMaxAge(age);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 1.0F - ((float) this.getAge() / this.getMaxAge());
        this.velocity = this.velocity.multiply(0.8F, 0.8F, 0.8F);

        this.scale = this.scale * 1.1F;
    }

    public static class Factory implements ChromaParticleFactory<ExplosionSparkChromaParticle> {
        @Override
        public ExplosionSparkChromaParticle create(ChromaParticleType<ExplosionSparkChromaParticle> type, Vec3 position, Vec3 velocity) {
            return new ExplosionSparkChromaParticle(type, position, velocity);
        }
    }
}
