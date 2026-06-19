package dev.rbn.chroma.client.screen_particle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.rbn.chroma.Chroma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStreamReader;
import java.util.Optional;

public class ScreenspaceParticle {
    private final ParticleType<?> particle;
    private int x;
    private int y;

    public ScreenspaceParticle(ParticleType<?> particle) {
        this.particle = particle;
        this.x = 100;
        this.y = 100;
    }

    public void render(GuiGraphics graphics) {
        graphics.pose().pushMatrix();
        graphics.pose().translate(this.x, this.y);

        Identifier id = BuiltInRegistries.PARTICLE_TYPE.getKey(this.particle);

        if (id != null) {
            try {
                Identifier jsonId =
                        id.withPrefix("particles/").withSuffix(".json");

                ResourceManager manager = Minecraft.getInstance().getResourceManager();

                Optional<Resource> resource = manager.getResource(jsonId);

                if (resource.isPresent()) {
                    try (var stream = resource.get().open()) {
                        JsonObject obj = JsonParser
                                .parseReader(new InputStreamReader(stream))
                                .getAsJsonObject();

                        if (obj.has("textures")) {
                            String tex = obj
                                    .getAsJsonArray("textures")
                                    .get(0)
                                    .getAsString();

                            Identifier sprite = Identifier.fromNamespaceAndPath(
                                    id.getNamespace(),
                                    "particle/" + tex
                            );

                            Chroma.LOGGER.info("ID: {}", sprite);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Particle identifier doesnt exist!" + e);
            }
        }

        graphics.pose().popMatrix();
    }
}
