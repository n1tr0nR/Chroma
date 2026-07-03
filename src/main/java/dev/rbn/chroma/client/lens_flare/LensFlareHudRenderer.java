package dev.rbn.chroma.client.lens_flare;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.jspecify.annotations.NonNull;

import java.util.List;

@ApiStatus.Experimental
public class LensFlareHudRenderer implements HudElement {
    @Override
    public void render(@NonNull GuiGraphics gg, @NonNull DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        LensFlareHandler.clear();
        LensFlareEvent.fire(mc);

        if (mc.player == null || mc.level == null) return;
        int screenW = mc.getWindow().getGuiScaledWidth();
        int screenH = mc.getWindow().getGuiScaledHeight();

        Vec2 screenCenter = new Vec2(screenW / 2f, screenH / 2f);
        for (LensFlare flare : LensFlareHandler.getFlares()) {
            @Nullable Vector2f pos = LFHelper.worldToScreen(flare.position());
            if (pos != null) {
                Vec2 dir = new Vec2(
                        pos.x - screenCenter.x,
                        pos.y - screenCenter.y
                );
                float dist = (float) Math.sqrt(dir.x * dir.x + dir.y * dir.y);
                dir = new Vec2(dir.x / dist, dir.y / dist);

                float visibility = flare.intensity();
                float centerFade = 1.0f / (1.0f + (dist / flare.sizeFalloff()));
                float finalAlphaBase = visibility * centerFade;

                renderFlareChain(gg, flare, screenCenter, dir, dist, finalAlphaBase);
            }
        }
    }

    private void renderFlareChain(
            GuiGraphics gg,
            LensFlare flare,
            Vec2 center,
            Vec2 dir,
            float baseDist,
            float alphaBase
    ) {

        List<LensFlare.Flare> flares = flare.flares();

        for (LensFlare.Flare f : flares) {

            float offset = f.offset();

            float x = center.x + dir.x * baseDist * (offset);
            float y = center.y + dir.y * baseDist * (offset);

            float size = f.size() * flare.scale();

            int alpha = (int) (255 * alphaBase * f.alpha());

            renderSprite(
                    gg,
                    f.texture(),
                    x - size / 2f,
                    y - size / 2f,
                    (int) size,
                    (int) size,
                    alpha
            );
        }
    }

    private int packColor(int r, int g, int b, int a) {
        return ((a & 255) << 24) |
                ((r & 255) << 16) |
                ((g & 255) << 8) |
                (b & 255);
    }

    private void renderSprite(
            GuiGraphics gg,
            Identifier texture,
            float x,
            float y,
            int w,
            int h,
            int alpha
    ) {
        int color = packColor(255, 255, 255, alpha);

        gg.pose().pushMatrix();
        gg.pose().translate(x, y);

        //gg.fill((int) 0, (int) 0, w, h, color);

        gg.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0, 0, w, h, w, h, color);

        gg.pose().popMatrix();
    }
}