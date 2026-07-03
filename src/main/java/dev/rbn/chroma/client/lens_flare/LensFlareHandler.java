package dev.rbn.chroma.client.lens_flare;

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Experimental
public class LensFlareHandler {

    private static final List<LensFlare> ACTIVE_FLARES = new ArrayList<>();

    public static void addFlare(LensFlare flare) {
        ACTIVE_FLARES.add(flare);
    }

    public static List<LensFlare> getFlares() {
        return ACTIVE_FLARES;
    }

    public static void clear() {
        ACTIVE_FLARES.clear();
    }
}