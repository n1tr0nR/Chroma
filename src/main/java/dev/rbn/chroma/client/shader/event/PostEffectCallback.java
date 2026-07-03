package dev.rbn.chroma.client.shader.event;

import dev.rbn.chroma.client.shader.ChromaPostManager;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
@FunctionalInterface
public interface PostEffectCallback {
    void apply(ChromaPostManager manager, Minecraft minecraft);
}
