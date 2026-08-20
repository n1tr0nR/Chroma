package dev.rbn.chroma.submods.plasma.particle.registration;

import dev.rbn.chroma.submods.plasma.particle.framework.WorldParticle;
import net.minecraft.resources.Identifier;

public class PlasmaType<T extends WorldParticle> {
    private final Identifier id;

    public PlasmaType(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }
}
