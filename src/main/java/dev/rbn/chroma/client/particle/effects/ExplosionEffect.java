package dev.rbn.chroma.client.particle.effects;

import dev.rbn.chroma.client.ChromaParticles;
import dev.rbn.chroma.client.particle.ChromaWorld;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExplosionEffect {
    public static void explode(Vec3 position, Level level, float offset) {
        if (level instanceof ClientLevel clientLevel && clientLevel instanceof ChromaWorld chromaWorld){
            int amount = clientLevel.random.nextInt(30, 60);
            for (int i = 0; i < amount; i++){
                Vec3 size = new Vec3(
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F),
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F),
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F)
                );

                if (clientLevel.random.nextInt(3) <= 0){
                    chromaWorld.chroma$addParticle(
                            ChromaParticles.EXPLOSION,
                            position.x + size.x,
                            position.y + size.y,
                            position.z + size.z,
                            0, 0, 0
                    );
                    chromaWorld.chroma$addParticle(
                            ChromaParticles.EXPLOSION_SPARK,
                            position.x + size.x,
                            position.y + size.y,
                            position.z + size.z,
                            0, 0, 0
                    );
                }
            }
            for (int i = 0; i < amount / 2; i++){
                Vec3 size = new Vec3(
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F),
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F),
                        ((clientLevel.random.nextFloat() - 0.5F) * 2) * (offset * 0.25F)
                );

                chromaWorld.chroma$addParticle(
                        ChromaParticles.SMOKE,
                        position.x + size.x,
                        position.y + size.y,
                        position.z + size.z,
                        (clientLevel.random.nextFloat() - 0.5F) * 0.1F, 0.05F, (clientLevel.random.nextFloat() - 0.5F) * 0.1F
                );
            }
        }
    }
}