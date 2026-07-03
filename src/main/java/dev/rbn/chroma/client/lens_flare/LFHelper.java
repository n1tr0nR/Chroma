package dev.rbn.chroma.client.lens_flare;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector2f;

@ApiStatus.Experimental
public class LFHelper {
    public static @Nullable Vector2f worldToScreen(Vec3 worldPos) {
        Minecraft client = Minecraft.getInstance();
        GameRenderer renderer = client.gameRenderer;

        Camera camera = renderer.getMainCamera();

        Vec3 camPos = camera.position();
        Vec3 delta = worldPos.subtract(camPos);

        Vec3 projected = renderer.projectPointToScreen(worldPos);

        if (projected.z > 1.0 || projected.z < -1.0) {
            return null;
        }

        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();

        float x = (float) ((projected.x * 0.5F + 0.5F) * width);
        float y = (float) ((-projected.y * 0.5F + 0.5F) * height);

        if (!Float.isFinite(x) || !Float.isFinite(y)) return null;

        return new Vector2f(x, y);
    }

    public static class WorldRenderState {
        public static Matrix4f VIEW;
        public static Matrix4f PROJECTION;
    }
}