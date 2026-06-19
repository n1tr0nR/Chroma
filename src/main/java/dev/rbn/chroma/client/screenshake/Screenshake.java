package dev.rbn.chroma.client.screenshake;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.EasingType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Screenshake {
    private static final Logger LOGGER = LoggerFactory.getLogger("Chroma/Screenshake");
    private static Screenshake INSTANCE;

    private final List<ScreenshakeInstance> instances = new ArrayList<>();

    public Screenshake(){
        INSTANCE = this;
    }

    public void initialize() {
        LOGGER.info("Initializing Chroma Screenshake");
    }

    public void tick(Minecraft minecraft){
        instances.forEach(ScreenshakeInstance::tick);

        instances.removeIf(ScreenshakeInstance::removeIf);
    }

    public void screenshake(float power, int duration, EasingType easingType){
        this.instances.add(new ScreenshakeInstance(null, easingType, duration, power));
    }

    public void screenshake(BlockPos origin, float power, int duration, EasingType easingType){
        this.instances.add(new ScreenshakeInstance(origin, easingType, duration, power));
    }

    public void screenshakeAroundPoint(ClientLevel world, int duration, float strength, float maxDistance, Vec3 point, EasingType easingType){
        for (Player player : world.players()){
            if (player instanceof LocalPlayer entity){
                var dist = entity.position().distanceTo(point);
                if (dist > maxDistance) return;

                var delta = dist / maxDistance;
                var falloff = 1.0D - delta;
                var effectiveIntensity = (float) (strength * falloff);
                if (effectiveIntensity <= 0.001) return;

                screenshake(BlockPos.containing(point), effectiveIntensity, duration, easingType);
            }
        }
    }

    public float getCombinedStrength(float tickDelta){
        float value = 0;
        for (ScreenshakeInstance instance : instances){
            value+=instance.getPower(tickDelta);
        }
        return value;
    }

    public static Screenshake getInstance() {
        return INSTANCE;
    }
}