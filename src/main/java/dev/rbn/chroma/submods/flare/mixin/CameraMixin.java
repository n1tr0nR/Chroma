package dev.rbn.chroma.submods.flare.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.rbn.chroma.client.Chroma;
import dev.rbn.chroma.config.Config;
import dev.rbn.chroma.config.GlobalConfigHandler;
import dev.rbn.chroma.submods.flare.screenshake.Screenshake;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @WrapOperation(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V"))
    private void chroma$applyScreenshake(Camera instance, float yaw, float pitch, Operation<Void> original){
        Minecraft minecraft = Minecraft.getInstance();
        Config config = GlobalConfigHandler.instance.getConfigForId(Chroma.MOD_ID);
        if (config == null) {
            original.call(instance, yaw, pitch);
            return;
        }

        boolean enabled = config.get(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "flare_enabled"));
        float mult = config.get(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "force_multiplier"));

        if (minecraft.level != null && enabled){
            float intensity = Screenshake.getInstance().getCombinedStrength(minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false)) * mult;
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
