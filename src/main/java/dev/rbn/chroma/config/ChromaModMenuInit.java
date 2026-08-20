package dev.rbn.chroma.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.rbn.chroma.client.Chroma;
import dev.rbn.chroma.client.ChromaConfig;

public class ChromaModMenuInit implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GlobalConfigHandler.instance.getConfigScreenForId(Chroma.MOD_ID, new ChromaConfig());
    }
}
