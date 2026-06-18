package dev.rbn.chroma.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.rbn.chroma.client.screenshake.Screenshake;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @WrapOperation(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V"))
    private void chroma$applyScreenshake(Camera instance, float yaw, float pitch, Operation<Void> original){
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null){
            float intensity = Screenshake.getInstance().getCombinedStrength(minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false));
            if (intensity > 0.001F){
                float yawShake = (float) ((minecraft.level.getRandom().nextDouble() * 2 - 1) * intensity);
                float pitchShake = (float) ((minecraft.level.getRandom().nextDouble() * 2 - 1) * intensity);
                original.call(instance, yaw + yawShake, pitch + pitchShake);
                return;
            }
        }
        original.call(instance, yaw, pitch);
    }
}
