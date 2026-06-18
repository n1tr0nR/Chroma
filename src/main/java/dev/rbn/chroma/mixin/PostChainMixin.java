package dev.rbn.chroma.mixin;

import dev.rbn.chroma.client.ChromaClient;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.UniformValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(PostChain.class)
public class PostChainMixin {
    @ModifyArg(method = "createPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PostPass;<init>(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;Ljava/util/Map;Ljava/util/List;)V"), index = 2)
    private static Map<String, List<UniformValue>> chroma$replaceUniforms(Map<String, List<UniformValue>> original) {
        Map<String, List<UniformValue>> modified = new HashMap<>();

        for (Map.Entry<String, List<UniformValue>> entry : original.entrySet()) {
            List<UniformValue> originalList = entry.getValue();
            List<UniformValue> newList = new ArrayList<>(originalList);

            if (entry.getKey().equals("BitsConfig")) {
                for (int i = 0; i < newList.size(); i++) {
                    UniformValue v = newList.get(i);

                    if (i == 1 && v instanceof UniformValue.FloatUniform) {
                        newList.set(i, new UniformValue.FloatUniform(ChromaClient.test()));
                    }
                }
            }

            modified.put(entry.getKey(), newList);
        }

        return modified;
    }
}