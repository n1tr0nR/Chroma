package dev.rbn.chroma.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.rbn.chroma.client.particle.effects.ExplosionEffect;
import dev.rbn.chroma.client.screenshake.Screenshake;
import dev.rbn.chroma.config.ChromaConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.util.EasingType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public abstract class ClientExplosionTrackerMixin {
    @Shadow
    public abstract ClientLevel getLevel();

    @WrapOperation(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void chroma$bigBoom(ClientLevel instance, ParticleOptions particleOptions, double x, double y, double z, double vx, double vy, double vz, Operation<Void> original,
                               @Local(argsOnly = true)ClientboundExplodePacket packet){
        if (ChromaConfig.EFFECTS.EXPLOSION.get()){
            ExplosionEffect.explode(new Vec3(x, y, z), this.getLevel(), packet.radius());
            Screenshake.getInstance().screenshakeAroundPoint(instance, (int) (5 * packet.radius()), packet.radius(), 5 * packet.radius(), new Vec3(x, y, z), EasingType.LINEAR);
        } else {
            original.call(instance, particleOptions, x, y, z, vx, vy, vz);
        }
    }
}
