package dev.rbn.chroma.submods.plasma.particle.framework;

import dev.rbn.chroma.client.ChromaRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;

public enum ParticleRenderType {
    ADDITIVE(ChromaRenderTypes.ENTITY_ADDITIVE, LightTexture.FULL_BRIGHT),
    MULTIPLY(ChromaRenderTypes.ENTITY_MULTIPLY, LightTexture.FULL_BRIGHT);

    private final BiFunction<Identifier, Boolean, RenderType> renderType;
    private final int light;

    ParticleRenderType(BiFunction<Identifier, Boolean, RenderType> renderType, int light) {
        this.renderType = renderType;
        this.light = light;
    }

    public RenderType applyRenderType(Identifier texture, boolean outline) {
        return renderType.apply(texture, outline);
    }

    public int getLight() {
        return light;
    }
}