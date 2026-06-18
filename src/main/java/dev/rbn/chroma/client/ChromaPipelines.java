package dev.rbn.chroma.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.minecraft.client.renderer.RenderPipelines;

public class ChromaPipelines {
    public static void register(){}

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
}
