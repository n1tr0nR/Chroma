package dev.rbn.chroma.client.shader.event;

import dev.rbn.chroma.client.shader.ChromaPostManager;
import net.minecraft.client.Minecraft;

@FunctionalInterface
public interface PostEffectCallback {
    void apply(ChromaPostManager manager, Minecraft minecraft);
}
