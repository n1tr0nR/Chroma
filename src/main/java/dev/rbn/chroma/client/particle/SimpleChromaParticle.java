package dev.rbn.chroma.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SimpleChromaParticle extends ChromaParticle{
    protected Vec3 previousPosition;

    public SimpleChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity, int maxAge, float scale) {
        super(type, position, velocity, maxAge, scale);
        this.previousPosition = position;

        RandomSource randomSource = RandomSource.create();
        Quaternionf orientation = new Quaternionf(randomSource.nextFloat() * 180, randomSource.nextFloat() * 180, randomSource.nextFloat() * 180, randomSource.nextFloat() * 180);
        this.setOritentation(orientation);
    }

    @Override
    public void tick() {
        previousPosition = position;
        super.tick();
        if (this.getScale() <= 0){
            this.remove();
        }
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertices, float tickDelta, Camera camera) {
        Vec3 cameraPos = camera.position();

        float x = (float) Mth.lerp(tickDelta, previousPosition.x, position.x) - (float) cameraPos.x;
        float y = (float) Mth.lerp(tickDelta, previousPosition.y, position.y) - (float) cameraPos.y;
        float z = (float) Mth.lerp(tickDelta, previousPosition.z, position.z) - (float) cameraPos.z;

        Vector3f[] corners = {
                new Vector3f(-1, -1, 0),
                new Vector3f(-1,  1, 0),
                new Vector3f( 1,  1, 0),
                new Vector3f( 1, -1, 0)
        };

        Quaternionf rotation = this.oritentation;

        for (Vector3f corner : corners) {
            corner.rotate(rotation);
            corner.mul(this.getScale(tickDelta));
            corner.add(x, y, z);
        }

        Matrix4f pose = matrices.last().pose();

        vertices.addVertex(pose, corners[0].x(), corners[0].y(), corners[0].z())
                .setColor(red, green, blue, alpha)
                .setUv(getU0(), getV1())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(0, 0, 1);

        vertices.addVertex(pose, corners[1].x(), corners[1].y(), corners[1].z())
                .setColor(red, green, blue, alpha)
                .setUv(getU0(), getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(0, 0, 1);

        vertices.addVertex(pose, corners[2].x(), corners[2].y(), corners[2].z())
                .setColor(red, green, blue, alpha)
                .setUv(getU1(), getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(0, 0, 1);

        vertices.addVertex(pose, corners[3].x(), corners[3].y(), corners[3].z())
                .setColor(red, green, blue, alpha)
                .setUv(getU1(), getV1())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(0, 0, 1);
    }

    protected float getU0() {
        return 0.0F;
    }

    protected float getU1() {
        return 1.0F;
    }

    protected float getV0() {
        return 0.0F;
    }

    protected float getV1() {
        return 1.0F;
    }

    public static class Factory implements ChromaParticleFactory<SimpleChromaParticle> {
        @Override
        public SimpleChromaParticle create(ChromaParticleType<SimpleChromaParticle> type, Vec3 position, Vec3 velocity) {
            return new SimpleChromaParticle(type, position, velocity, 400, 1F);
        }
    }
}
