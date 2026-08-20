package dev.rbn.chroma.client;

import com.mojang.serialization.Codec;
import dev.rbn.chroma.config.Config;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.option.values.TextPoint;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;

public class ChromaConfig extends Config {
    public ChromaConfig() {
        super(Chroma.MOD_ID);
        add(this.sections);
    }

    private void add(List<ConfigSection> sections){
        sections.add(createCanvas());
        sections.add(createPlasma());
        sections.add(createPuppet());
        sections.add(createFlare());
    }

    private ConfigSection createPlasma(){
        return new ConfigSection(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "plasma"), 0xf67ab6,
                new TextPoint(Component.literal("In-World Particles")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "world_enabled"), Codec.BOOL),
                new ConfigValue<>(350,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "max_particles"), Codec.INT, 10, 750),
                new ConfigValue<>(100,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "max_distance"), Codec.INT, 10, 500),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "fade_out"), Codec.BOOL),
                new TextPoint(Component.literal("Screen Particles")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "screen_enabled"), Codec.BOOL),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "smooth"), Codec.BOOL)
        );
    }

    private ConfigSection createCanvas(){
        return new ConfigSection(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "canvas"), 0xf6e17a,
                new TextPoint(Component.literal("General")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "enabled"), Codec.BOOL)
        );
    }

    private ConfigSection createPuppet(){
        return new ConfigSection(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "puppet"), 0x7acdf6,
                new TextPoint(Component.literal("First Person")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "first_enabled"), Codec.BOOL),
                new TextPoint(Component.literal("Other Entities")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "other_enabled"), Codec.BOOL));
    }

    private ConfigSection createFlare(){
        return new ConfigSection(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "flare"), 0x99f67a,
                new TextPoint(Component.literal("Screenshake")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "enabled"), Codec.BOOL),
                new ConfigValue<>(1.0F,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "force_multiplier"), Codec.FLOAT),
                new TextPoint(Component.literal("Lens Flare")),
                new ConfigValue<>(true,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "lens_flare_enabled"), Codec.BOOL),
                new ConfigValue<>(50,
                        Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "max_distance"), Codec.INT));
    }

    private ConfigSection createDebug(int value){
        return new ConfigSection(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "debug_" + value), 0xcccccc);
    }
}