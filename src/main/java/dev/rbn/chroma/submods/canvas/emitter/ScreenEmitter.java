package dev.rbn.chroma.submods.canvas.emitter;

import dev.rbn.chroma.client.Chroma;
import dev.rbn.chroma.config.GlobalConfigHandler;
import dev.rbn.chroma.submods.canvas.CanvasCore;
import dev.rbn.chroma.submods.canvas.Target;
import dev.rbn.chroma.submods.canvas.particle.ScreenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class ScreenEmitter implements Emitter {
    private final UUID uuid;
    protected final Target target;

    protected final List<ScreenParticle> particles = new ArrayList<>();

    protected ScreenEmitter(Target target) {
        this.target = target;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void emit(int x, int y, int count) {
        this.emit(x, y, count, 0, 0);
    }

    @Override
    public void emit(int x, int y, int count, float xForce, float yForce) {
        for (int i = 0; i < count; i++){
            ScreenParticle particle = new ScreenParticle(this.target, this, RandomSource.create().nextInt(10, 50), x, y) {};
            particle.xVel = xForce;
            particle.yVel = yForce;
            particles.add(particle);
        }
    }

    public void tick(){
        Iterator<ScreenParticle> iterator = particles.iterator();

        while (iterator.hasNext()) {
            ScreenParticle particle = iterator.next();

            if (!particle.isRemoved()) {
                particle.tick();
            } else {
                iterator.remove();
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics) {
        boolean enabled = GlobalConfigHandler.instance.getConfigForId(Chroma.MOD_ID).get(
                Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "plasma_screen_enabled")
        );

        if (!enabled) return;

        for (ScreenParticle particle : particles) {
            float td = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);

            graphics.pose().pushMatrix();

            float age = Mth.lerp(td, particle.getAge() - 1, particle.getAge());
            float progress = age / particle.getMaxAge();

            float alpha;

            if (progress < 0.2F) {
                alpha = progress / 0.2F;
            } else if (progress > 0.8F) {
                alpha = (1.0F - progress) / 0.2F;
            } else {
                alpha = 1.0F;
            }

            int color = ((int) (alpha * 255) << 24) | particle.color;

            boolean snap = GlobalConfigHandler.instance.getConfigForId(Chroma.MOD_ID).get(
                    Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "plasma_smooth")
            );
            if (snap){
                graphics.pose().translate(particle.getX(td), particle.getY(td));
                graphics.pose().rotate(particle.getAge() * 0.1F);
                graphics.pose().scale(alpha);
            } else {
                graphics.pose().translate((int) particle.getX(td), (int) particle.getY(td));
            }

            graphics.fill(-1, -1, 1, 1, color);

            graphics.pose().popMatrix();
        }
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Target getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Emitter screenEmitter){
            return this.getUUID().equals(screenEmitter.getUUID());
        }
        return false;
    }
}
