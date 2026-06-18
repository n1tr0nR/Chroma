package dev.rbn.chroma.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.rbn.chroma.client.ChromaRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.SubmitNodeCollector;

public class ChromaParticleRenderer {
    private final ChromaWorld chromaWorld;

    public ChromaParticleRenderer(ChromaWorld chromaWorld) {
        this.chromaWorld = chromaWorld;
    }

    public void render(PoseStack stack, SubmitNodeCollector submit, float tickDelta, Camera camera) {

        var particles = this.chromaWorld.chroma$getParticles();

        for (int i = 0; i < particles.size(); i++) {
            ChromaParticle particle = particles.get(i);

            if (particle.isRemoved()) {
                this.chromaWorld.chroma$removeParticle(i);
                i--;
                continue;
            }

            submit.order(i).submitCustomGeometry(
                    stack,
                    ChromaRenderTypes.entityAdditiveNoCull(particle.getType().texture()),
                    (pose, vertices) -> particle.render(stack, vertices, tickDelta, camera)
            );
        }
    }
}