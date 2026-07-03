package dev.rbn.chroma.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.client.lens_flare.*;
import dev.rbn.chroma.client.particle.ChromaParticleRenderer;
import dev.rbn.chroma.client.screen_particle.ScreenParticleManager;
import dev.rbn.chroma.client.screenshake.Screenshake;
import dev.rbn.chroma.client.shader.ChromaPostManager;
import dev.rbn.chroma.config.ChromaConfig;
import dev.rbn.chroma.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.impl.client.rendering.hud.HudElementRegistryImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;

public class ChromaClient implements ClientModInitializer {
    public ChromaParticleRenderer renderer;
    public static ScreenParticleManager particle;

    @Override
    public void onInitializeClient() {
        particle = new ScreenParticleManager();

        ConfigManager.load(ChromaConfig.getSections());
        ChromaPostManager post = new ChromaPostManager();
        post.initialize();
        Screenshake screenshake = new Screenshake();
        screenshake.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(screenshake::tick);

        WorldRenderEvents.BEFORE_TRANSLUCENT.register(context -> {
            LFHelper.WorldRenderState.VIEW = RenderSystem.getModelViewMatrix();
            LFHelper.WorldRenderState.PROJECTION = Minecraft.getInstance().gameRenderer.getProjectionMatrix(0);
        });

        //TODO: Fix this, make it work again
        //HudElementRegistryImpl.addLast(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "particle"), new ScreenParticleRenderer());

        ChromaParticles.register();
        ChromaPipelines.register();
        ChromaRenderTypes.register();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()){
            ClientTickEvents.END_WORLD_TICK.register(clientLevel -> {
                LensFlareEvent.register(ctx -> {
                    ctx.addFlare(new LensFlare(new Vec3(0, 100, 0), 1.0F, 1.0F, 10F,
                            List.of(
                                    new LensFlare.Flare(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "textures/flare/orb.png"), -0.8F, 200, 1F),
                                    new LensFlare.Flare(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "textures/flare/shine.png"), 1, 100, 0.25F),
                                    new LensFlare.Flare(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "textures/flare/orb.png"), 0.8F, 200, 1F),
                                    new LensFlare.Flare(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "textures/flare/orb.png"), -3F, 100, 0.25F),
                                    new LensFlare.Flare(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "textures/flare/orb.png"), 3F, 100, 0.25F)
                            )
                    ));
                });
            });
        }

        HudElementRegistryImpl.addLast(Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "lens_flare"), new LensFlareHudRenderer());

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (client.level != null){
                client.level.chroma$clear();
            }
            renderer = null;
        });

        WorldRenderEvents.END_MAIN.register(worldRenderContext -> {
            Minecraft minecraft = Minecraft.getInstance();
            Vec3 cameraPosition = minecraft.gameRenderer.getMainCamera().position();
            worldRenderContext.matrices().pushPose();
            worldRenderContext.matrices().translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

            if (minecraft.level != null){
                if (renderer == null){;
                    renderer = new ChromaParticleRenderer(null);
                }
                if (renderer.chromaWorld != minecraft.level){
                    Chroma.LOGGER.info("Initializing Chroma Particle Renderer");
                    renderer = new ChromaParticleRenderer(minecraft.level);
                }
                renderer.render(worldRenderContext.matrices(), worldRenderContext.commandQueue(), minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), minecraft.gameRenderer.getMainCamera());
            }

            worldRenderContext.matrices().popPose();
        });
    }

    public static float test(){
        return 1.0F;
    }
}