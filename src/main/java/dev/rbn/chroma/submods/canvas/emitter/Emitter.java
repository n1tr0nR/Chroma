package dev.rbn.chroma.submods.canvas.emitter;

import dev.rbn.chroma.submods.canvas.Target;
import dev.rbn.chroma.submods.canvas.particle.ScreenParticle;
import net.minecraft.client.gui.GuiGraphics;

import java.util.UUID;

public interface Emitter {
    void emit(int x, int y, int count);
    void emit(int x, int y, int count, float xForce, float yForce);
    void render(GuiGraphics graphics);
    UUID getUUID();
    Target getTarget();
}
