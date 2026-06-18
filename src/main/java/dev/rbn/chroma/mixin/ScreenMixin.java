package dev.rbn.chroma.mixin;

import dev.rbn.chroma.config.widget.TickableWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    @Final
    private List<GuiEventListener> children;

    @Inject(method = "tick", at = @At("HEAD"))
    public void chroma$tick(CallbackInfo ci){
        for (GuiEventListener listener : this.children){
            if (listener instanceof TickableWidget widget) widget.tick();
        }
    }
}
