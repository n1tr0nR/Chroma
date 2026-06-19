package dev.rbn.chroma.client.screen_particle;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleType;

import java.util.ArrayList;
import java.util.List;

public class ScreenParticleManager {
    private final List<ScreenspaceParticle> particles = new ArrayList<>();

    public void addParticle(int x, int y, ParticleType<?> type){
        this.particles.add(new ScreenspaceParticle(type));
    }

    public void render(GuiGraphics graphics){
        this.particles.forEach(screenspaceParticle -> screenspaceParticle.render(graphics));
    }
}
