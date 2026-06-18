package dev.rbn.chroma.config.option.values;

import net.minecraft.resources.Identifier;

public abstract class ConfigValue<T> {
    protected final T defaultValue;
    private final Identifier id;

    protected T value;

    public ConfigValue(T defaultValue, Identifier id) {
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.id = id;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public String toString() {
        return "config." + id.getNamespace() + "." + id.getPath();
    }

    public Identifier getId() {
        return id;
    }
}