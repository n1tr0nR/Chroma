package dev.rbn.chroma.submods.plasma.particle.framework;

import dev.rbn.chroma.submods.plasma.particle.registration.PlasmaProvider;
import dev.rbn.chroma.submods.plasma.particle.registration.PlasmaSpriteSet;

public class OrientingParticle extends WorldParticle {
    public static class Factory implements PlasmaProvider<OrientingParticle> {
        @Override
        public OrientingParticle create(PlasmaSpriteSet sprites, double x, double y, double z, double vx, double vy, double vz) {
            return new OrientingParticle();
        }
    }
}