package dev.rbn.chroma.client.lens_flare;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Experimental
public record LensFlare(
        Vec3 position,
        float intensity,
        float scale,
        float sizeFalloff,
        List<Flare> flares
) {
    public record Flare(
            Identifier texture,
            float offset,
            float size,
            float alpha
    ) {}
}