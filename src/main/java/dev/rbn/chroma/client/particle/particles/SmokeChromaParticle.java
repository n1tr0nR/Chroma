package dev.rbn.chroma.client.particle.particles;

import dev.rbn.chroma.client.particle.BillboardingChromaParticle;
import dev.rbn.chroma.client.particle.ChromaParticleFactory;
import dev.rbn.chroma.client.particle.ChromaParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class SmokeChromaParticle extends BillboardingChromaParticle {
    public SmokeChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity) {
        super(type, position, velocity, 0, 0);
        RandomSource source = RandomSource.create();

        int age = 100 + source.nextInt(50);
        float scale = source.nextFloat() * 3;

        this.setColor(source.nextFloat() * 0.1F, source.nextFloat() * 0.1F, source.nextFloat() * 0.1F, source.nextFloat());

        this.setScale(scale);
        this.setMaxAge(age);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = (1.0F - ((float) this.getAge() / this.getMaxAge())) * 0.1F;

        this.scale = this.scale * 1.01F;
    }

    public static class Factory implements ChromaParticleFactory<SmokeChromaParticle> {
        @Override
        public SmokeChromaParticle create(ChromaParticleType<SmokeChromaParticle> type, Vec3 position, Vec3 velocity) {
            return new SmokeChromaParticle(type, position, velocity);
        }
    }
}
