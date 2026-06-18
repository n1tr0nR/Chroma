package dev.rbn.chroma.client.shader.uniform;

import java.util.HashMap;
import java.util.Map;

public class ChromaUniforms {
    private final Map<String, ChromaConfigPatch> patches = new HashMap<>();

    public ChromaConfigPatch config(String name) {
        return patches.computeIfAbsent(name, ChromaConfigPatch::new);
    }

    public ChromaConfigPatch getPatch(String name) {
        return patches.get(name);
    }

    public Map<String, ChromaConfigPatch> all() {
        return patches;
    }
}