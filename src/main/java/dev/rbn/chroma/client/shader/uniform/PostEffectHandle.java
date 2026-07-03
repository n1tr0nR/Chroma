package dev.rbn.chroma.client.shader.uniform;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface PostEffectHandle {
    ChromaUniforms chroma$getUniforms();
    void chroma$setUniforms(ChromaUniforms uniforms);
}