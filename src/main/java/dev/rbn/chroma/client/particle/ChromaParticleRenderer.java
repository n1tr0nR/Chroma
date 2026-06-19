package dev.rbn.chroma.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.rbn.chroma.client.ChromaRenderTypes;
import dev.rbn.chroma.config.ChromaConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.phys.Vec3;

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

            Vec3 particlePosition = particle.getPosition();
            double distance = particlePosition.distanceTo(camera.position());
            if (distance > ChromaConfig.EFFECTS.MAX_DISTANCE.get()) continue;

            submit.order(i).submitCustomGeometry(
                    stack,
                    particle.getType().getRenderType(),
                    (pose, vertices) -> particle.render(stack, vertices, tickDelta, camera)
            );
        }
    }
}