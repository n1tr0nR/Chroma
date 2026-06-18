package dev.rbn.chroma.config;

import dev.rbn.chroma.config.option.EffectSection;
import dev.rbn.chroma.config.option.ShaderSection;
import net.minecraft.network.chat.Component;

public class EffectsSectionScreen extends ChromaSectionScreen{
    public EffectsSectionScreen(ChromaConfigScreen parent) {
        super(Component.literal("Effects"), parent, ChromaConfig.EFFECTS);
    }
}
