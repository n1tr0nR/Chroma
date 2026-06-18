package dev.rbn.chroma.client;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class ChromaRenderTypes {
    public static void register(){}

    private static final BiFunction<Identifier, Boolean, RenderType> ENTITY_ADDITIVE = Util.memoize((texture, outline) -> {
        RenderSetup setup = RenderSetup.builder(ChromaPipelines.ENTITY_ADDITIVE)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .setOutline(
                        outline
                                ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE
                                : RenderSetup.OutlineProperty.NONE
                )
                .createRenderSetup();

        return RenderType.create("entity_additive", setup);
    });

    public static RenderType entityAdditiveNoCull(Identifier identifier, boolean bl) {
        return ENTITY_ADDITIVE.apply(identifier, bl);
    }

    public static RenderType entityAdditiveNoCull(Identifier identifier) {
        return entityAdditiveNoCull(identifier, true);
    }
}