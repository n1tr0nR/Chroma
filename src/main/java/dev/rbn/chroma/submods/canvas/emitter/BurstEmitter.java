package dev.rbn.chroma.submods.canvas.emitter;

import dev.rbn.chroma.submods.canvas.Target;
import dev.rbn.chroma.submods.canvas.particle.ScreenParticle;
import net.minecraft.util.RandomSource;

public class BurstEmitter extends ScreenEmitter{
    public BurstEmitter(Target target) {
        super(target);
    }

    public void emit(int x, int y, int count, float xForce, float yForce, int color){
        for (int i = 0; i < count; i++){
            ScreenParticle particle = new ScreenParticle(this.target, this, RandomSource.create().nextInt(10, 50), x, y) {};
            particle.xVel = xForce;
            particle.yVel = yForce;
            particle.color = color;
            particles.add(particle);
        }
    }
}
