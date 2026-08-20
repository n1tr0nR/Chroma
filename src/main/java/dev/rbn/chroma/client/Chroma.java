package dev.rbn.chroma.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.rbn.chroma.client.lens_flare.*;
import dev.rbn.chroma.client.particle.ChromaParticleRenderer;
import dev.rbn.chroma.submods.flare.screenshake.Screenshake;
import dev.rbn.chroma.client.shader.ChromaPostManager;
import dev.rbn.chroma.config.ConfigEvent;
import dev.rbn.chroma.submods.Submod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chroma implements ClientModInitializer {
    public ChromaParticleRenderer renderer;

    public static final String MOD_ID = "chroma";
    public static final Logger LOGGER = LoggerFactory.getLogger("Chroma");

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance()
                .getEntrypoints("chroma-submod", Submod.class)
                .forEach(Submod::onInitialize);

        ConfigEvent.REGISTER.addConfig(instance ->
                instance.registerNewConfig(MOD_ID, new ChromaConfig())
        );


        ChromaPostManager post = new ChromaPostManager();
        post.initialize();

        //Registries
        ChromaParticles.register();
        ChromaPipelines.register();
        ChromaRenderTypes.register();

        //Events
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
                    LOGGER.info("Initializing Chroma Particle Renderer");
                    renderer = new ChromaParticleRenderer(minecraft.level);
                }
                renderer.render(worldRenderContext.matrices(), worldRenderContext.commandQueue(), minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), minecraft.gameRenderer.getMainCamera());
            }

            worldRenderContext.matrices().popPose();
        });
        WorldRenderEvents.BEFORE_TRANSLUCENT.register(context -> {
            LFHelper.WorldRenderState.VIEW = RenderSystem.getModelViewMatrix();
            LFHelper.WorldRenderState.PROJECTION = Minecraft.getInstance().gameRenderer.getProjectionMatrix(0);
        });
    }

    public static float test(){
        return 1.0F;
    }
}