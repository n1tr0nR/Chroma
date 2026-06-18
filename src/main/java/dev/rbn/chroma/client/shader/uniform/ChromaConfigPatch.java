package dev.rbn.chroma.client.shader.uniform;

import net.minecraft.client.renderer.UniformValue;

import java.util.HashMap;
import java.util.Map;

public class ChromaConfigPatch {
    private final String name;

    private final Map<Integer, UniformValue> indexOverrides = new HashMap<>();

    public ChromaConfigPatch(String name) {
        this.name = name;
    }

    public ChromaConfigPatch set(int index, float value) {
        indexOverrides.put(index, new UniformValue.FloatUniform(value));
        return this;
    }

    public ChromaConfigPatch set(int index, int value) {
        indexOverrides.put(index, new UniformValue.IntUniform(value));
        return this;
    }

    public ChromaConfigPatch set(int index, UniformValue value) {
        indexOverrides.put(index, value);
        return this;
    }

    public Map<Integer, UniformValue> overrides() {
        return indexOverrides;
    }
}