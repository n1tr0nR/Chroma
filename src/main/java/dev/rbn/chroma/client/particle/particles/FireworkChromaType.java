package dev.rbn.chroma.client.particle.particles;

import dev.rbn.chroma.client.particle.ChromaParticleFactory;
import dev.rbn.chroma.client.particle.ChromaParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class FireworkChromaType extends ChromaParticleType<FireworkParticle> {
    public FireworkChromaType(Color color) {
        super(Identifier.fromNamespaceAndPath("chroma", "textures/particle/dot.png"), new FireworkFactory(color));
    }

    private record FireworkFactory(Color color) implements ChromaParticleFactory<FireworkParticle> {
        @Override
            public FireworkParticle create(ChromaParticleType<FireworkParticle> type, Vec3 position, Vec3 velocity) {
                return new FireworkParticle((FireworkChromaType) type, position, velocity, this.color);
            }
        }
}