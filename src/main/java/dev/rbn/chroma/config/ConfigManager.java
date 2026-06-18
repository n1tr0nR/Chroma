package dev.rbn.chroma.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.values.BooleanValue;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.option.values.IntegerValue;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path FILE =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("chroma.json");

    public static void save(List<ConfigSection> sections) {
        JsonObject root = new JsonObject();

        for (ConfigSection section : sections) {
            for (ConfigValue<?> value : section.getValues()) {

                if (value instanceof BooleanValue bool) {
                    root.addProperty(
                            bool.getId().toString(),
                            bool.get()
                    );
                }

                if (value instanceof IntegerValue integer) {
                    root.addProperty(
                            integer.getId().toString(),
                            integer.get()
                    );
                }
            }
        }

        try {
            Files.writeString(FILE, GSON.toJson(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(List<ConfigSection> sections) {
        if (!Files.exists(FILE)) {
            save(sections);
            return;
        }

        try {
            JsonObject root = GSON.fromJson(
                    Files.readString(FILE),
                    JsonObject.class
            );

            for (ConfigSection section : sections) {
                for (ConfigValue<?> value : section.getValues()) {

                    String key = value.getId().toString();

                    if (!root.has(key))
                        continue;

                    if (value instanceof BooleanValue bool) {
                        bool.set(root.get(key).getAsBoolean());
                    }

                    if (value instanceof IntegerValue integer) {
                        integer.set(root.get(key).getAsInt());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
