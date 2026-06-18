package dev.rbn.chroma.client.particle;

import java.util.List;

public interface ChromaWorld {
    List<ChromaParticle> chroma$getParticles();
    void chroma$removeParticle(int id);
    void chroma$addParticle(ChromaParticleType<?> type, double x, double y, double z, double xv, double yv, double zv);
    void chroma$clear();
}
