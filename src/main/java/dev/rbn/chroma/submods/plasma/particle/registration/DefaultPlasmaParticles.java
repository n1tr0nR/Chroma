package dev.rbn.chroma.submods.plasma.particle.registration;

import dev.rbn.chroma.submods.plasma.Plasma;
import dev.rbn.chroma.submods.plasma.particle.framework.OrientingParticle;

public class DefaultPlasmaParticles {
    public static void register(){

    }

    public static final PlasmaType<OrientingParticle> EXPLOSION = PlasmaRegistry.register(Plasma.id("explosion"), new OrientingParticle.Factory());
}