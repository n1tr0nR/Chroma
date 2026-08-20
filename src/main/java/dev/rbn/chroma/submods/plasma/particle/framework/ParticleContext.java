package dev.rbn.chroma.submods.plasma.particle.framework;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

public record ParticleContext(PoseStack poseStack, VertexConsumer vertexConsumer, float partialTicks, Camera camera) {
}
