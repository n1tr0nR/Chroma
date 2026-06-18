package dev.rbn.chroma.client.particle.particles;

import dev.rbn.chroma.client.particle.SimpleChromaParticle;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class FireworkParticle extends SimpleChromaParticle {
    public FireworkParticle(FireworkChromaType type, Vec3 position, Vec3 velocity, Color color) {
        super(type, position, velocity, 100, 1);

        this.setColor((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 1);
    }
}