package dev.rbn.chroma.submods.canvas;

import dev.rbn.chroma.submods.canvas.particle.ScreenParticle;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CanvasCore {
    private static final CanvasCore instance = new CanvasCore();
    private final List<ScreenParticle> particles = new ArrayList<>();

    public static CanvasCore getInstance() {
        return instance;
    }

    public List<ScreenParticle> getParticles() {
        return particles;
    }

    public void addParticle(ScreenParticle particle){
        particles.add(particle);
    }

    public void tick() {
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

    public void render(Target target, GuiGraphics graphics) {

    }
}
