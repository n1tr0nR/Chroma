package dev.rbn.chroma.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.util.NullScreenFactory;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GlobalConfigHandler {
    public static final GlobalConfigHandler instance =
            new GlobalConfigHandler();

    private final Map<String, Config> configs = new HashMap<>();

    private GlobalConfigHandler() {
    }

    public void registerNewConfig(String modId) {
        this.registerNewConfig(modId, new Config(modId));
    }

    public void registerNewConfig(String modId, Config config) {
        configs.put(modId, config);
    }

    public @Nullable Config getConfigForId(String modId) {
        return configs.get(modId);
    }

    public @Nullable ConfigScreenFactory<?> getConfigScreenForId(String modId, Config config) {
        registerNewConfig(modId, config);
        return parent -> new ChromaConfigScreen(parent, modId);
    }
}