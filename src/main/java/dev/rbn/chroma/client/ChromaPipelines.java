package dev.rbn.chroma.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import net.minecraft.client.renderer.RenderPipelines;

public class ChromaPipelines {
    public static void register(){}

    public static final BlendFunction MULTIPLY = new BlendFunction(SourceFactor.DST_COLOR, DestFactor.ONE_MINUS_SRC_ALPHA);

    public static final RenderPipeline ENTITY_ADDITIVE =
            RenderPipelines.register(
                    RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                            .withLocation("pipeline/entity_additive")
                            .withSampler("Sampler1")
                            .withCull(false)
                            .withDepthWrite(false)
                            .withBlend(BlendFunction.OVERLAY)
                            .build()
            );
    public static final RenderPipeline ENTITY_MULTIPLY =
            RenderPipelines.register(
                    RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                            .withLocation("pipeline/entity_multiply")
                            .withSampler("Sampler1")
                            .withCull(false)
                            .withDepthWrite(false)
                            .withBlend(BlendFunction.TRANSLUCENT)
                            .build()
            );
}
