package dev.rbn.chroma.config.option;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.config.option.values.BooleanValue;
import net.minecraft.resources.Identifier;

public class EffectSection extends ConfigSection {
    public BooleanValue EXPLOSION = (BooleanValue) addValue(new BooleanValue(true, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_explosion")));
    //public BooleanValue FIREWORK = (BooleanValue) addValue(new BooleanValue(true, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_firework")));
}