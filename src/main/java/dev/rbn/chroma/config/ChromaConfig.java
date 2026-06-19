package dev.rbn.chroma.config;

import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.EffectSection;
import dev.rbn.chroma.config.option.ScreenshakeSection;
import dev.rbn.chroma.config.option.ShaderSection;

import java.util.List;

public class ChromaConfig {
    public static final ShaderSection SHADERS = new ShaderSection();
    public static final EffectSection EFFECTS = new EffectSection();
    public static final ScreenshakeSection SCREENSHAKE = new ScreenshakeSection();

    public static List<ConfigSection> getSections() {
        return List.of(
                SHADERS,
                EFFECTS,
                SCREENSHAKE
        );
    }
}
