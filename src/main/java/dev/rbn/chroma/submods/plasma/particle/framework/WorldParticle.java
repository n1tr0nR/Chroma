package dev.rbn.chroma.submods.plasma.particle.framework;

import dev.rbn.chroma.math.VectorHelper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class WorldParticle {
    protected Vec3 previousPosition = new Vec3(0, 0, 0);
    protected Vec3 position = new Vec3(0, 0, 0);

    protected Vec3 velocity = new Vec3(0, 0, 0);
    protected float velocityMultiplier = 0.9F;

    protected Quaternionf previousOrientation = new Quaternionf();
    protected Quaternionf orientation = new Quaternionf();

    protected int age = 0;
    protected int maxAge = 20;

    protected float previousScale = 1;
    protected float scale = 1;

    protected float red = 1;
    protected float green = 1;
    protected float blue = 1;
    protected float alpha = 1;

    protected boolean removed;
    protected TextureAtlasSprite sprite;
    protected boolean animated = false;

    public void tick() {
        age++;
        this.previousOrientation = this.orientation;
        this.previousPosition = this.position;
        this.previousScale = this.scale;

        if (age >= maxAge) {
            markRemoved();
            return;
        }
        if (scale <= 0){
            markRemoved();
            return;
        }

        this.position.add(velocity);
        this.velocity = this.velocity.scale(velocityMultiplier);
    }

    public ParticleRenderType renderType(){
        return ParticleRenderType.ADDITIVE;
    }

    public void buildQuads(ParticleContext context) {
        Vec3 cameraPos = context.camera().position();
        Vec3 position = getPosition(context.partialTicks()).subtract(cameraPos);
        Quaternionf orientation = getOrientation(context.partialTicks());
        Vector3f[] corners = {
                new Vector3f(-1, -1, 0),
                new Vector3f(-1,  1, 0),
                new Vector3f( 1,  1, 0),
                new Vector3f( 1, -1, 0)
        };

        for (Vector3f corner : corners) {
            corner.rotate(orientation);
            corner.mul(this.getScale(context.partialTicks()));
            corner.add((float) position.x, (float) position.y, (float) position.z);
        }

        Matrix4f pose = context.poseStack().last().pose();

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        context.vertexConsumer().addVertex(pose, corners[0].x(), corners[0].y(), corners[0].z())
                .setColor(red, green, blue, alpha)
                .setUv(u0, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(0, 0, 1);

        context.vertexConsumer().addVertex(pose, corners[1].x(), corners[1].y(), corners[1].z())
                .setColor(red, green, blue, alpha)
                .setUv(u0, v0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(0, 0, 1);

        context.vertexConsumer().addVertex(pose, corners[2].x(), corners[2].y(), corners[2].z())
                .setColor(red, green, blue, alpha)
                .setUv(u1, v0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(0, 0, 1);

        context.vertexConsumer().addVertex(pose, corners[3].x(), corners[3].y(), corners[3].z())
                .setColor(red, green, blue, alpha)
                .setUv(u1, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(0, 0, 1);
    }

    public void markRemoved() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Vec3 getPosition(float tickDelta) {
        return VectorHelper.lerpVec3(tickDelta, this.previousPosition, this.position);
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
        return Mth.lerp(tickDelta, this.previousScale, this.scale);
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

    public void setOrientation(Quaternionf orientation) {
        this.orientation = orientation;
        this.previousOrientation = orientation;
    }

    public Quaternionf getOrientation() {
        return orientation;
    }

    public Quaternionf getOrientation(float tickDelta) {
        return new Quaternionf(this.previousOrientation).slerp(new Quaternionf(this.orientation), tickDelta);
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

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setSprite(TextureAtlasSprite sprite) {
        this.sprite = sprite;
    }
}
