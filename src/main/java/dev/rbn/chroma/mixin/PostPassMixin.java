package dev.rbn.chroma.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.rbn.chroma.client.shader.uniform.ChromaConfigPatch;
import dev.rbn.chroma.client.shader.uniform.ChromaUniforms;
import dev.rbn.chroma.client.shader.uniform.PostEffectHandle;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.UniformValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.*;

@Mixin(PostPass.class)
public class PostPassMixin  {

}
