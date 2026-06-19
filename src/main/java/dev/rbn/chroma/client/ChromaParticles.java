package dev.rbn.chroma.client;

import dev.rbn.chroma.client.particle.ChromaParticleType;
import dev.rbn.chroma.client.particle.SimpleChromaParticle;
import dev.rbn.chroma.client.particle.particles.ExplosionChromaParticle;
import dev.rbn.chroma.client.particle.particles.ExplosionSparkChromaParticle;
import dev.rbn.chroma.client.particle.particles.SmokeChromaParticle;
import net.minecraft.resources.Identifier;

public class ChromaParticles {
    public static void register(){}

    public static final ChromaParticleType<SimpleChromaParticle> SPARK =
            new ChromaParticleType<>(
                    Identifier.fromNamespaceAndPath("chroma", "textures/particle/spark.png"),
                    new SimpleChromaParticle.Factory()
            );
    public static final ChromaParticleType<ExplosionChromaParticle> EXPLOSION =
            new ChromaParticleType<>(
                    Identifier.fromNamespaceAndPath("chroma", "textures/particle/ball.png"),
                    new ExplosionChromaParticle.Factory()
            );
    public static final ChromaParticleType<ExplosionSparkChromaParticle> EXPLOSION_SPARK =
            new ChromaParticleType<>(
                    Identifier.fromNamespaceAndPath("chroma", "textures/particle/spark.png"),
                    new ExplosionSparkChromaParticle.Factory()
            );
    public static final ChromaParticleType<SmokeChromaParticle> SMOKE =
            new ChromaParticleType<>(
                    Identifier.fromNamespaceAndPath("chroma", "textures/particle/smoke.png"),
                    new SmokeChromaParticle.Factory(),
                    ChromaRenderTypes.entityMultiplyNoCull(Identifier.fromNamespaceAndPath("chroma", "textures/particle/smoke.png"))
            );
}
