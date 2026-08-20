package dev.rbn.chroma.config.option.values;

import com.mojang.serialization.Codec;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.SectionChunk;
import net.minecraft.resources.Identifier;

public class ConfigValue<T> implements SectionChunk {
    private final Identifier id;
    public ConfigSection parentSection;

    private final T defaultValue;
    private final Codec<T> codec;

    private final T min;
    private final T max;

    protected T value;

    public ConfigValue(
            T defaultValue,
            Identifier id,
            Codec<T> codec
    ) {
        this(defaultValue, id, codec, null, null);
    }

    public ConfigValue(
            T defaultValue,
            Identifier id,
            Codec<T> codec,
            T min,
            T max
    ) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.codec = codec;
        this.min = min;
        this.max = max;
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

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public void reset() {
        this.value = defaultValue;
    }

    public Identifier getId() {
        if (this.parentSection != null) {
            return Identifier.fromNamespaceAndPath(
                    id.getNamespace(),
                    this.parentSection.getIdentifier().getPath() + "_" + id.getPath()
            );
        }

        return id;
    }

    public Codec<T> getCodec() {
        return codec;
    }

    @Override
    public String toString() {
        return "config." + id.getNamespace() + "." +
                this.parentSection.getIdentifier().getPath() + "." +
                id.getPath();
    }
}