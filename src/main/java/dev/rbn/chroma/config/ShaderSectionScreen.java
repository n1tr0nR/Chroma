package dev.rbn.chroma.config;

import net.minecraft.network.chat.Component;

public class ShaderSectionScreen extends ChromaSectionScreen{
    public ShaderSectionScreen(ChromaConfigScreen parent) {
        super(Component.literal("Shader"), parent, ChromaConfig.SHADERS);
    }
}
