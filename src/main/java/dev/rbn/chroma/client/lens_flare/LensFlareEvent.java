package dev.rbn.chroma.client.lens_flare;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Experimental
public class LensFlareEvent {
    private static final List<Consumer<LensFlareContext>> LISTENERS = new ArrayList<>();

    public static void register(Consumer<LensFlareContext> listener) {
        LISTENERS.add(listener);
    }

    public static void fire(Minecraft mc) {
        if (mc.player == null || mc.level == null) return;

        LensFlareContext ctx = new LensFlareContext(mc, mc.player, mc.level);

        for (var l : LISTENERS) {
            l.accept(ctx);
        }
    }
}