package dev.rbn.chroma.config.option.values;

import net.minecraft.resources.Identifier;

public class IntegerValue extends ConfigValue<Integer> {
    public IntegerValue(int value, Identifier identifier) {
        super(value, identifier);
    }
}