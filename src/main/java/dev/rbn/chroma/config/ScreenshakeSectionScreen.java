package dev.rbn.chroma.config;

import net.minecraft.network.chat.Component;

public class ScreenshakeSectionScreen extends ChromaSectionScreen{
    public ScreenshakeSectionScreen(ChromaConfigScreen parent) {
        super(Component.literal("Screenshake"), parent, ChromaConfig.SCREENSHAKE);
    }
}
