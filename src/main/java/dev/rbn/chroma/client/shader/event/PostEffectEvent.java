package dev.rbn.chroma.client.shader.event;

import java.util.ArrayList;
import java.util.List;

public class PostEffectEvent {
    private final List<PostEffectCallback> listeners =
            new ArrayList<>();

    public void register(PostEffectCallback callback) {
        listeners.add(callback);
    }

    public PostEffectCallback invoker() {
        return (manager, minecraft) -> {
            for (PostEffectCallback listener : listeners) {
                listener.apply(manager, minecraft);
            }
        };
    }
}