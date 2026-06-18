package dev.rbn.chroma.config.option.values;

import net.minecraft.resources.Identifier;

public class BooleanValue extends ConfigValue<Boolean> {
    public BooleanValue(boolean value, Identifier identifier) {
        super(value, identifier);
    }
}