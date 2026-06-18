package dev.rbn.chroma.client.screenshake;

import net.minecraft.core.BlockPos;
import net.minecraft.util.EasingType;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

public class ScreenshakeInstance {
    private final int maxDuration;
    private final float power;
    private final @Nullable BlockPos origin;
    private final EasingType easingType;

    private float prevNowPower;
    private float nowPower;
    private int duration;

    public ScreenshakeInstance(@Nullable BlockPos origin, EasingType easingType, int duration, float power){
        this.origin = origin;
        this.easingType = easingType;
        this.maxDuration = duration;
        this.power = power;

        this.prevNowPower = power;
        this.nowPower = power;
        this.duration = duration;
    }

    public void tick(){
        this.prevNowPower = this.nowPower;

        this.duration--;
        float percent = (float) this.duration / this.maxDuration;
        this.nowPower = this.power * percent;
    }

    public float getPower(float tickDelta){
        return easingType.apply(Mth.lerp(tickDelta, this.prevNowPower, this.nowPower));
    }

    public boolean removeIf() {
        return duration < 0;
    }
}
