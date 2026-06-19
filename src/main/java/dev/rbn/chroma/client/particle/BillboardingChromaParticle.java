package dev.rbn.chroma.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;

public class BillboardingChromaParticle extends SimpleChromaParticle{
    public BillboardingChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity, int maxAge, float scale) {
        super(type, position, velocity, maxAge, scale);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertices, float tickDelta, Camera camera) {
        this.setOritentation(camera.rotation());
        super.render(matrices, vertices, tickDelta, camera);
    }
}
