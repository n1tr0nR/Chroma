package dev.rbn.chroma.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.rbn.chroma.client.shader.ChromaPostManager;
import dev.rbn.chroma.client.shader.Shaders;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.creaking.Creaking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    protected abstract void setPostEffect(Identifier identifier);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void chroma$applyShaders(DeltaTracker deltaTracker, boolean tick, CallbackInfo ci){
        ChromaPostManager.getInstance().applyShaders();
    }

    @Inject(method = "checkEntityPostEffect", at = @At("HEAD"), cancellable = true)
    private void chroma$creakingGrayscale(Entity entity, CallbackInfo ci){
        if (entity instanceof Creaking){
            this.setPostEffect(Shaders.GRAYSCALE);
            ci.cancel();
        }
    }
}
