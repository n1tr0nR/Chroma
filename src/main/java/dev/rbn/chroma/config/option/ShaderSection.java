package dev.rbn.chroma.config.option;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.config.option.values.BooleanValue;
import net.minecraft.resources.Identifier;

public class ShaderSection extends ConfigSection {
    public BooleanValue ENABLED = (BooleanValue) addValue(new BooleanValue(true, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "shader_enabled")));
}