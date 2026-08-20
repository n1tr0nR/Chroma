package dev.rbn.chroma.submods.plasma.particle.registration;

import dev.rbn.chroma.submods.plasma.particle.framework.WorldParticle;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PlasmaRegistry {
    private static final Map<PlasmaType<?>, PlasmaProvider<?>> FACTORIES = new HashMap<>();

    public static <T extends WorldParticle> PlasmaType<T> register(
            Identifier id,
            PlasmaProvider<T> provider
    ) {
        PlasmaType<T> type = new PlasmaType<>(id);
        FACTORIES.put(type, provider);
        return type;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WorldParticle> PlasmaProvider<T> getFactory(PlasmaType<T> type) {
        return (PlasmaProvider<T>) FACTORIES.get(type);
    }
}