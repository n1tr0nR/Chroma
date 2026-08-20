package dev.rbn.chroma.config.option;

import dev.rbn.chroma.config.option.values.ConfigValue;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ConfigSection {
    public List<SectionChunk> values = new ArrayList<>();

    private final Identifier identifier;
    public final int color;

    public ConfigSection(Identifier identifier, int color, SectionChunk... value){
        for (SectionChunk val : value){
            addValue(val);
        }
        this.identifier = identifier;
        this.color = color;
    }

    public SectionChunk addValue(SectionChunk value) {
        values.add(value);

        if (value instanceof ConfigValue<?> configValue){
            configValue.parentSection = this;
        }

        return value;
    }

    public List<SectionChunk> getValues() {
        return values;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public String getTranslated() {
        return "value." + identifier.getNamespace() + "." + identifier.getPath();
    }
}