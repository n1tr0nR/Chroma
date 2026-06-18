package dev.rbn.chroma.client;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.client.particle.ChromaParticleRenderer;
import dev.rbn.chroma.client.particle.ChromaWorld;
import dev.rbn.chroma.client.screenshake.Screenshake;
import dev.rbn.chroma.client.shader.ChromaPostManager;
import dev.rbn.chroma.client.shader.ShaderPipeline;
import dev.rbn.chroma.config.ChromaConfig;
import dev.rbn.chroma.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.EasingType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;

public class ChromaClient implements ClientModInitializer {
    public ChromaParticleRenderer renderer;

    @Override
    public void onInitializeClient() {
        ConfigManager.load(ChromaConfig.getSections());
        ChromaPostManager post = new ChromaPostManager();
        post.initialize();
        Screenshake screenshake = new Screenshake();
        screenshake.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(screenshake::tick);

        ChromaParticles.register();
        ChromaPipelines.register();
        ChromaRenderTypes.register();

        ChromaPostManager.POST_EFFECTS.register(((manager, minecraft) -> {
            ShaderPipeline pipeline = new ShaderPipeline(Identifier.withDefaultNamespace("spider"));
            manager.applyEffect(pipeline);
        }));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (client.level instanceof ChromaWorld chromaWorld){
                chromaWorld.chroma$clear();
            }
            renderer = null;
        });

        WorldRenderEvents.END_MAIN.register(worldRenderContext -> {
            Minecraft minecraft = Minecraft.getInstance();
            Vec3 cameraPosition = minecraft.gameRenderer.getMainCamera().position();
            worldRenderContext.matrices().pushPose();
            worldRenderContext.matrices().translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

            if (minecraft.level != null){
                if (renderer == null && minecraft.level instanceof ChromaWorld chromaWorld){
                    Chroma.LOGGER.info("Initializing Chroma Particle Renderer");
                    renderer = new ChromaParticleRenderer(chromaWorld);
                }
                if (renderer == null) return;
                renderer.render(worldRenderContext.matrices(), worldRenderContext.commandQueue(), minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), minecraft.gameRenderer.getMainCamera());
            }

            worldRenderContext.matrices().popPose();
        });
    }

    public static float test(){
        return 1.0F;
    }
}