package dev.rbn.chroma.config.option.values;

import dev.rbn.chroma.config.option.SectionChunk;
import net.minecraft.network.chat.Component;

public class TextPoint implements SectionChunk {
    public final Component text;

    public TextPoint(Component text) {
        this.text = text;
    }
}
