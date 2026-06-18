package dev.rbn.chroma.client.particle;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class ChromaParticleType<T extends ChromaParticle> {
    private final Identifier texture;
    private final ChromaParticleFactory<T> factory;

    public ChromaParticleType(
            Identifier texture,
            ChromaParticleFactory<T> factory
    ) {
        this.texture = texture;
        this.factory = factory;
    }

    public T create(Vec3 position, Vec3 velocity) {
        return factory.create(this, position, velocity);
    }

    public Identifier texture() {
        return texture;
    }
}