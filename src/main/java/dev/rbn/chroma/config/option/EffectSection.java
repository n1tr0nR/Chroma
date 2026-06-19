package dev.rbn.chroma.config.option;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.config.option.values.BooleanValue;
import dev.rbn.chroma.config.option.values.IntegerValue;
import net.minecraft.resources.Identifier;

public class EffectSection extends ConfigSection {
    public BooleanValue EXPLOSION = (BooleanValue) addValue(new BooleanValue(true, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_explosion")));
    public IntegerValue MAX_PARTICLES = (IntegerValue) addValue(new IntegerValue(500, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_max_particles")));
    public IntegerValue MAX_DISTANCE = (IntegerValue) addValue(new IntegerValue(100, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_max_distance")));
    //public BooleanValue FIREWORK = (BooleanValue) addValue(new BooleanValue(true, Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "effects_firework")));
}