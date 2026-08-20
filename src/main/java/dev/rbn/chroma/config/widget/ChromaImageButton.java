package dev.rbn.chroma.config.widget;

import dev.rbn.chroma.client.Chroma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ChromaImageButton extends Button implements TickableWidget, RenderUtil {
    private final Minecraft minecraft = Minecraft.getInstance();
    private int ticksOpen = 0;

    private final int color;
    private float hovered;
    private final Identifier textureID;

    public ChromaImageButton(int x, int y, OnPress onPress, int color, Component component, Identifier textureID) {
        super(x, y, 1, 1, component, onPress, Button.DEFAULT_NARRATION);
        this.color = color;
        this.textureID = textureID;

        this.setWidth(24);
        this.setHeight(22);
    }

    public void updatePosition(int x, int y, int ticks){
        this.setX(x);
        this.setY(y);
        this.ticksOpen = ticks;
    }

    @Override
    protected void renderContents(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        if (this.isHovered()){
            this.hovered = Math.clamp(this.hovered += 0.09F, 0, 1);
        } else {
            this.hovered = Math.clamp(this.hovered -= 0.09F, 0, 1);
        }


        float hoverLerp = this.hovered;

        int background = withAlpha(0x00101015, (int) (getPercentFromRange(0, 10) * 200));
        int highlight = withAlpha(color, (int) (getPercentFromRange(0, 10) * (50 + (100 * hoverLerp))));
        int highlight1 = withAlpha(color, (int) (getPercentFromRange(0, 10) * (100 + (150 * hoverLerp))));
        int descc = withAlpha(color, (int) (getPercentFromRange(10, 20) * (200 + (50 * hoverLerp))));
        int bgGlow = withAlpha(color, (int) (getPercentFromRange(10, 20) * 20 + (hoverLerp * 150)));

        int textc = withAlpha(color, (int) (getPercentFromRange(5, 15) * ((255 * hoverLerp))));

        float centerX = this.getX() + this.getWidth() / 2.0F;
        float centerY = this.getY() + this.getHeight() / 2.0F;
        float time = minecraft.level != null ? minecraft.level.getGameTime() + f : 0.0F;
        float scale = 1.0F + (0.05F * (Math.clamp(hoverLerp * 3, 0, 1)));

        guiGraphics.pose().pushMatrix();

        guiGraphics.pose().translate(centerX, centerY);

        guiGraphics.pose().scale(scale, scale);

        guiGraphics.pose().translate(-centerX, -centerY);

        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "button/glow"),
                this.getX() - 8, this.getY() - 8, this.getWidth() + 16, this.getHeight() + 16, bgGlow
        );

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), background);

        guiGraphics.fill(this.getX() + 1, this.getY() - 1, this.getX() + this.getWidth() - 1, this.getY(), background);
        guiGraphics.fill(this.getX() + 1, this.getY() + this.getHeight(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() + 1, background);

        guiGraphics.fill(this.getX() + 1, this.getY(), this.getX() + 2, this.getY() + this.getHeight(), highlight);
        guiGraphics.fill(this.getX() + this.getWidth() - 2, this.getY(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight(), highlight);
        guiGraphics.fill(this.getX() + 2, this.getY(), this.getX() + this.getWidth() - 2, this.getY() + 1, highlight);
        guiGraphics.fill(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + this.getWidth() - 2, this.getY() + this.getHeight(), highlight);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.textureID, this.getX() + 2, this.getY() + 1, 20, 20, highlight1);

        guiGraphics.drawString(minecraft.font, this.message, this.getX() + (this.getWidth() / 2) - (minecraft.font.width(this.message) / 2), this.getY() + this.getHeight() + 5, textc);

        guiGraphics.pose().popMatrix();
    }

    @Override
    public void tick() {
    }

    public float getPercentFromRange(float start, float end) {
        float range = end - start;
        if (range == 0.0f) return 1.0f;
        return Mth.clamp((Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.ticksOpen - 1, this.ticksOpen) - start) / range, 0.0f, 1.0f);
    }
}
