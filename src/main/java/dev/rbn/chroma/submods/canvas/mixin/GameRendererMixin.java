package dev.rbn.chroma.submods.canvas.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.rbn.chroma.submods.canvas.CanvasCore;
import dev.rbn.chroma.submods.canvas.Target;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void canvas$renderParticles(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci, @Local GuiGraphics graphics){
        CanvasCore.getInstance().render(Target.ALL, graphics);
    }
}