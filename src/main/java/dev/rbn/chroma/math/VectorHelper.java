package dev.rbn.chroma.math;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public interface VectorHelper {
    static Vec3 lerpVec3(float tickDelta, Vec3 previous, Vec3 current){
        double x = Mth.lerp(tickDelta, previous.x, current.x);
        double y = Mth.lerp(tickDelta, previous.y, current.y);
        double z = Mth.lerp(tickDelta, previous.z, current.z);
        return new Vec3(x, y, z);
    }
}