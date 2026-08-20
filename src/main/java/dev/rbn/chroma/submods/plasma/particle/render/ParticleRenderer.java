package dev.rbn.chroma.submods.plasma.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.rbn.chroma.submods.plasma.particle.framework.WorldParticle;
import dev.rbn.chroma.submods.plasma.particle.registration.PlasmaRegistry;
import dev.rbn.chroma.submods.plasma.particle.registration.PlasmaSpriteSet;
import dev.rbn.chroma.submods.plasma.particle.registration.PlasmaType;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.SubmitNodeCollector;

import java.io.FileNotFoundException;

public class ParticleRenderer<T extends WorldParticle> {
    private final WorldParticle particle;

    public ParticleRenderer(PlasmaType<T> particle, SpriteGetter getter) throws FileNotFoundException {
        this.particle = PlasmaRegistry.getFactory(particle).create(new PlasmaSpriteSet(getter.getSprites()), 0, 0, 0, 0, 0, 0);
    }

    public void render(PoseStack stack, SubmitNodeCollector collector, float tickDelta, Camera camera){

    }
}