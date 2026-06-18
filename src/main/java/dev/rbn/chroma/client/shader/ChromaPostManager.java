package dev.rbn.chroma.client.shader;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import dev.rbn.chroma.client.shader.event.PostEffectEvent;
import dev.rbn.chroma.client.shader.uniform.PostEffectHandle;
import dev.rbn.chroma.config.ChromaConfig;
import dev.rbn.chroma.mixin.accessors.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChromaPostManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("Chroma/Post");
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static ChromaPostManager instance;
    private CrossFrameResourcePool pool;
    private final List<ShaderPipeline> queuedEffects = new ArrayList<>();
    public static final PostEffectEvent POST_EFFECTS = new PostEffectEvent();

    public ChromaPostManager() {
        instance = this;
    }

    public static ChromaPostManager getInstance() {
        return instance;
    }

    public void initialize() {
        LOGGER.info("Initializing Chroma Post effects");
        LOGGER.warn("Post effects are experimental!");
    }

    public void applyShaders() {
        if (!ChromaConfig.SHADERS.ENABLED.get()){
            return;
        }

        checkPool();

        queuedEffects.clear();
        POST_EFFECTS.invoker().apply(this, minecraft);

        for (ShaderPipeline pipeline : queuedEffects) {
            processChain(pipeline);
        }

        queuedEffects.clear();
    }

    public void applyEffect(ShaderPipeline pipeline) {
        queuedEffects.add(pipeline);
    }

    @SuppressWarnings("deprecation")
    public void processChain(ShaderPipeline pipeline) {
        PostChain chain = minecraft.getShaderManager()
                .getPostChain(pipeline.effectId(), LevelTargetBundle.MAIN_TARGETS);

        if (chain != null) {
            ((PostEffectHandle) chain).chroma$setUniforms(pipeline.uniforms());
            chain.process(minecraft.getMainRenderTarget(), pool);
        }
    }

    private void checkPool() {
        if (pool == null) {
            GameRenderer renderer = minecraft.gameRenderer;
            pool = ((GameRendererAccessor) renderer)
                    .chroma$getResourcePool();
        }
    }
}