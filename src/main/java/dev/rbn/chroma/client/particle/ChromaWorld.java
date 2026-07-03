package dev.rbn.chroma.client.particle;

import java.util.List;

public interface ChromaWorld {
    default List<ChromaParticle> chroma$getParticles() {
        throw new RuntimeException("Implemented via mixin");
    }
    default void chroma$removeParticle(int id) {
        throw new RuntimeException("Implemented via mixin");
    }
    default void chroma$addParticle(ChromaParticleType<?> type, double x, double y, double z, double xv, double yv, double zv) {
        throw new RuntimeException("Implemented via mixin");
    }
    default void chroma$clear() {
        throw new RuntimeException("Implemented via mixin");
    }
}
