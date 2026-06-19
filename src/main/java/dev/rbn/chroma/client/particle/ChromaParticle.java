package dev.rbn.chroma.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public abstract class ChromaParticle {
    protected final ChromaParticleType<?> type;

    protected Vec3 position;
    protected Vec3 velocity;

    protected Quaternionf prevOritentation = new Quaternionf();
    protected Quaternionf oritentation = new Quaternionf();

    protected int age;
    protected int maxAge;

    protected float prevScale;
    protected float scale;

    protected float red;
    protected float green;
    protected float blue;
    protected float alpha;

    protected boolean removed;

    public ChromaParticle(ChromaParticleType<?> type, Vec3 position, Vec3 velocity, int maxAge, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.maxAge = maxAge;
        this.scale = scale;

        this.red = 1.0F;
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;

        this.type = type;
    }

    public void tick() {
        age++;

        this.prevScale = this.scale;
        this.prevOritentation = this.oritentation;

        if (age >= maxAge) {
            remove();
            return;
        }

        position = position.add(velocity);
    }

    public abstract void render(
            PoseStack matrices,
            VertexConsumer vertices,
            float tickDelta,
            Camera camera
    );

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public float getLifeProgress() {
        return maxAge == 0 ? 1.0F : (float) age / maxAge;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Vec3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3 velocity) {
        this.velocity = velocity;
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public float getScale() {
        return scale;
    }

    public float getScale(float tickDelta) {
        return Mth.lerp(tickDelta, this.prevScale, this.scale);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public void setOritentation(Quaternionf oritentation) {
        this.oritentation = oritentation;
        this.prevOritentation = oritentation;
    }

    public Quaternionf getOritentation() {
        return oritentation;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public ChromaParticleType<?> getType() {
        return type;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
