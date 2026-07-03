package dev.rbn.chroma.client.lens_flare;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public record LensFlareContext(Minecraft minecraft, Player player, Level level) {
    public void addFlare(LensFlare flare) {
        LensFlareHandler.addFlare(flare);
    }
}