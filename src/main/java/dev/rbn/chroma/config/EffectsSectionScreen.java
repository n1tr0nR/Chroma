package dev.rbn.chroma.config;

import net.minecraft.network.chat.Component;

public class EffectsSectionScreen extends ChromaSectionScreen{
    public EffectsSectionScreen(ChromaConfigScreen parent) {
        super(Component.literal("Effects"), parent, ChromaConfig.EFFECTS);
    }
}
