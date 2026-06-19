package dev.rbn.chroma.client.particle;

import dev.rbn.chroma.client.ChromaRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class ChromaParticleType<T extends ChromaParticle> {
    private final Identifier texture;
    private final ChromaParticleFactory<T> factory;
    private final RenderType renderType;

    public ChromaParticleType(
            Identifier texture,
            ChromaParticleFactory<T> factory
    ) {
        this(texture, factory, ChromaRenderTypes.entityAdditiveNoCull(texture));
    }

    public ChromaParticleType(
            Identifier texture,
            ChromaParticleFactory<T> factory, RenderType renderType
    ) {
        this.texture = texture;
        this.factory = factory;
        this.renderType = renderType;
    }

    public T create(Vec3 position, Vec3 velocity) {
        return factory.create(this, position, velocity);
    }

    public Identifier texture() {
        return texture;
    }

    public RenderType getRenderType() {
        return renderType;
    }
}