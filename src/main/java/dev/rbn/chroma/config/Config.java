package dev.rbn.chroma.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.SectionChunk;
import dev.rbn.chroma.config.option.values.ConfigValue;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private final String modId;
    private final Path file;

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    protected final List<ConfigSection> sections = new ArrayList<>();

    public Config(String modId) {
        this.modId = modId;
        this.file = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(modId + ".json");
    }

    public <T> T get(Identifier id) {
        for (ConfigSection section : sections) {
            for (SectionChunk chunk : section.getValues()) {
                if (chunk instanceof ConfigValue<?> value && value.getId().equals(id)) {
                    @SuppressWarnings("unchecked")
                    ConfigValue<T> typedValue = (ConfigValue<T>) value;

                    return typedValue.get();
                }
            }
        }

        throw new IllegalArgumentException("Unknown config value: " + id);
    }

    public void save() {
        JsonObject root = new JsonObject();

        for (ConfigSection section : sections) {
            for (SectionChunk chunk : section.getValues().stream()
                    .filter(value -> value instanceof ConfigValue<?>)
                    .toList()) {

                ConfigValue<?> value = (ConfigValue<?>) chunk;
                encodeValue(root, value);
            }
        }

        try {
            Files.writeString(
                    file,
                    GSON.toJson(root)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void encodeValue(
            JsonObject root,
            ConfigValue<T> value
    ) {
        JsonElement encoded = value.getCodec()
                .encodeStart(
                        JsonOps.INSTANCE,
                        value.get()
                )
                .getOrThrow();

        root.add(
                value.getId().toString(),
                encoded
        );
    }

    public void load() {
        if (!Files.exists(file)) {
            save();
            return;
        }

        try {
            JsonObject root = GSON.fromJson(
                    Files.readString(file),
                    JsonObject.class
            );

            for (ConfigSection section : sections) {
                for (SectionChunk chunk : section.getValues().stream()
                        .filter(value -> value instanceof ConfigValue<?>)
                        .toList()) {
                    ConfigValue<?> value = (ConfigValue<?>) chunk;
                    decodeValue(root, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void decodeValue(
            JsonObject root,
            ConfigValue<T> value
    ) {
        String key = value.getId().toString();

        if (!root.has(key))
            return;

        T decoded = value.getCodec()
                .parse(
                        JsonOps.INSTANCE,
                        root.get(key)
                )
                .getOrThrow();

        value.set(decoded);
    }
}