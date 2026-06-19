package dev.rbn.chroma.client.screen_particle;

import dev.rbn.chroma.client.ChromaClient;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import org.jspecify.annotations.NonNull;

public class ScreenParticleRenderer implements HudElement {
    @Override
    public void render(@NonNull GuiGraphics guiGraphics, @NonNull DeltaTracker deltaTracker) {
        ChromaClient.particle.render(guiGraphics);
    }
}
