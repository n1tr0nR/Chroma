package dev.rbn.chroma.mixin;

import net.minecraft.client.particle.FireworkParticles;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireworkParticles.Starter.class)
public abstract class FireworkParticlesMixin {
    /*@Inject(method = "createParticle", at = @At("HEAD")) //TODO: Make this actually change the main particle instead of the (other) one?
    private void chroma$fireworkEffects(double d, double e, double f, double g, double h, double i, IntList intList, IntList intList2, boolean bl, boolean bl2, CallbackInfo ci){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level instanceof ChromaWorld chromaWorld){
            chromaWorld.chroma$addParticle(
                    new FireworkChromaType(new Color(Util.getRandom(intList, minecraft.level.random))),
                    d, e, f, g, h, i
            );
        }
    }*/
}
