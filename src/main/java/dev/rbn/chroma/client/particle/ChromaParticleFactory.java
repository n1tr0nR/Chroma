package dev.rbn.chroma.client.particle;

import net.minecraft.world.phys.Vec3;

public interface ChromaParticleFactory<T extends ChromaParticle> {
    T create(
            ChromaParticleType<T> type,
            Vec3 position,
            Vec3 velocity
    );
}
