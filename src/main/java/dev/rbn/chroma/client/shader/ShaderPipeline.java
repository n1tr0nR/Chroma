package dev.rbn.chroma.client.shader;

import dev.rbn.chroma.client.shader.uniform.ChromaConfigPatch;
import dev.rbn.chroma.client.shader.uniform.ChromaUniforms;
import net.minecraft.resources.Identifier;

public class ShaderPipeline {
    private final Identifier effectId;
    private final ChromaUniforms uniforms = new ChromaUniforms();

    public ShaderPipeline(Identifier effectId) {
        this.effectId = effectId;
    }

    /*public ChromaConfigPatch config(String name) {
        return uniforms.config(name);
    }*/

    public ChromaUniforms uniforms() {
        return uniforms;
    }

    public Identifier effectId() {
        return effectId;
    }
}