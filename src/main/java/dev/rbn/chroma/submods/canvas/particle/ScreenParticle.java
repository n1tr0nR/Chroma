package dev.rbn.chroma.submods.canvas.particle;

import dev.rbn.chroma.submods.canvas.Target;
import dev.rbn.chroma.submods.canvas.emitter.Emitter;
import net.minecraft.util.Mth;

public abstract class ScreenParticle {
    private final Target target;
    private final Emitter parentEmitter;
    private final int maxAge;
    private float prevX;
    private float prevY;
    private float x;
    private float y;
    public float xVel = 0;
    public float yVel = 0;

    private int age;
    public int color = 0xFFFFFF;
    private boolean removed = false;

    protected ScreenParticle(Target target, Emitter parentEmitter, int maxAge, float x, float y) {
        this.target = target;
        this.parentEmitter = parentEmitter;
        this.maxAge = maxAge;
        this.x = x;
        this.y = y;
    }

    public void tick(){
        this.prevX = this.x;
        this.prevY = this.y;

        this.age++;
        if (this.age >= maxAge) {
            markRemoved();
            return;
        }

        this.x += this.xVel;
        this.y += this.yVel;
    }

    public void markRemoved(){
        this.removed = true;
    }

    public boolean isRemoved(){
        return this.removed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getX(float tickDelta) {
        return Mth.lerp(tickDelta, this.prevX, this.x);
    }

    public float getY(float tickDelta) {
        return Mth.lerp(tickDelta, this.prevY, this.y);
    }

    public Target getTarget() {
        return target;
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Emitter getParentEmitter() {
        return parentEmitter;
    }
}
