package dev.rbn.chroma.submods.plasma.particle.registration;

import dev.rbn.chroma.submods.plasma.particle.framework.WorldParticle;

public interface PlasmaProvider<T extends WorldParticle> {
    T create(PlasmaSpriteSet sprites, double x, double y, double z, double vx, double vy, double vz);
}