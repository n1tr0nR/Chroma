package dev.rbn.chroma.client.screen_particle;

import dev.rbn.chroma.client.Chroma;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

@ApiStatus.Experimental
public class ScreenParticleRenderer implements HudElement {
    @Override
    public void render(@NonNull GuiGraphics guiGraphics, @NonNull DeltaTracker deltaTracker) {
        Chroma.particle.render(guiGraphics);
    }
}
