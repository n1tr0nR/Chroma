package dev.rbn.chroma.config;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.config.option.ShaderSection;
import net.minecraft.network.chat.Component;

public class ShaderSectionScreen extends ChromaSectionScreen{
    public ShaderSectionScreen(ChromaConfigScreen parent) {
        super(Component.literal("Shader"), parent, ChromaConfig.SHADERS);
    }
}
