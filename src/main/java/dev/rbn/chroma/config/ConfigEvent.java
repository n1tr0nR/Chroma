package dev.rbn.chroma.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class ConfigEvent {
    public static final ConfigRegisterEvent REGISTER =
            new ConfigRegisterEvent();

    private ConfigEvent() {
    }

    public static class ConfigRegisterEvent {
        private final List<Consumer<GlobalConfigHandler>> listeners =
                new ArrayList<>();

        public void addConfig(Consumer<GlobalConfigHandler> listener) {
            listeners.add(listener);
        }

        public void fire(GlobalConfigHandler handler) {
            for (Consumer<GlobalConfigHandler> listener : listeners) {
                listener.accept(handler);
            }
        }
    }
}