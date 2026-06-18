package dev.rbn.chroma.mixin.handle;

import dev.rbn.chroma.client.shader.uniform.ChromaUniforms;
import dev.rbn.chroma.client.shader.uniform.PostEffectHandle;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PostChain.class)
public class PostChainHandler implements PostEffectHandle {
    @Unique
    private ChromaUniforms chroma$uniforms;

    @Override
    public ChromaUniforms chroma$getUniforms() {
        return chroma$uniforms;
    }

    @Override
    public void chroma$setUniforms(ChromaUniforms uniforms) {
        this.chroma$uniforms = uniforms;
    }
}
