package dev.rbn.chroma.mixin;

import dev.rbn.chroma.client.particle.ChromaParticle;
import dev.rbn.chroma.client.particle.ChromaParticleType;
import dev.rbn.chroma.client.particle.ChromaWorld;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements ChromaWorld {
    @Unique
    private final List<ChromaParticle> particles = new ArrayList<>();

    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, bl, bl2, l, i);
    }

    @Override
    public List<ChromaParticle> chroma$getParticles() {
        return particles;
    }

    @Override
    public void chroma$removeParticle(int id) {
        this.particles.remove(id);
    }

    @Override
    public void chroma$addParticle(ChromaParticleType<?> type, double x, double y, double z, double xv, double yv, double zv) {
        this.particles.add(type.create(new Vec3(x, y, z), new Vec3(xv, yv, zv)));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void chroma$tickParticles(BooleanSupplier booleanSupplier, CallbackInfo ci){
        for (ChromaParticle particle : this.particles){
            particle.tick();
        }
    }

    @Override
    public void chroma$clear() {
        particles.clear();
    }
}