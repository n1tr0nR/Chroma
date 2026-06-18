package dev.rbn.chroma.config.option;

import dev.rbn.chroma.config.option.values.BooleanValue;
import dev.rbn.chroma.config.option.values.ConfigValue;

import java.util.ArrayList;
import java.util.List;

public class ConfigSection {
    public List<ConfigValue<?>> values = new ArrayList<>();

    public ConfigValue<?> addValue(ConfigValue<?> value) {
        values.add(value);
        return value;
    }

    public List<ConfigValue<?>> getValues() {
        return values;
    }
}