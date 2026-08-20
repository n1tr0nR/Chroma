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

public class ChromaButton extends Button implements TickableWidget, RenderUtil {
    private final Minecraft minecraft = Minecraft.getInstance();
    private int ticksOpen = 0;
    private final int color;

    private @Nullable final Component description;

    private float prevHovered;
    private float hovered;

    public ChromaButton(int x, int y, Component component, @Nullable Component description, OnPress onPress, int color) {
        super(x, y, 1, 1, component, onPress, Button.DEFAULT_NARRATION);
        this.color = color;

        int w = minecraft.font.width(component);
        this.setWidth((int) (w * 2));
        this.description = description;
        this.setHeight(15);

        if (this.description != null){
            this.setWidth(minecraft.font.width(description) + 50);
            this.setHeight(30);
        }
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

        int textc = withAlpha(color, (int) (getPercentFromRange(5, 15) * (200 + (50 * hoverLerp))));

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

        int darkerT = (color & 0xFF000000)
                | ((int)(((color >> 16) & 0xFF) * 0.8F) << 16)
                | ((int)(((color >> 8) & 0xFF) * 0.9F) << 8)
                | ((int)((color & 0xFF) * 0.7F));
        int darkerD = (color & 0xFF000000)
                | ((int)(((color >> 16) & 0xFF) * 0.6F) << 16)
                | ((int)(((color >> 8) & 0xFF) * 0.6F) << 8)
                | ((int)((color & 0xFF) * 0.5F));

        Component text = drawSmartText(this.minecraft, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.message) / 2, this.getY() + 4, this.message, darkerT, color, i, j);
        guiGraphics.drawString(this.minecraft.font, text, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.message) / 2, this.getY() + 4, this.description != null ? textc : highlight1, true);

        if (this.description != null){
            Component desc = drawSmartText(this.minecraft, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.description) / 2, this.getY() + 16, this.description, darkerD, darkerT, i, j);
            guiGraphics.drawString(this.minecraft.font, desc, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.description) / 2, this.getY() + 16, descc, true);
        }

        guiGraphics.pose().popMatrix();
    }

    @Override
    public void tick() {
        this.prevHovered = this.hovered;
    }

    public float getPercentFromRange(float start, float end) {
        float range = end - start;
        if (range == 0.0f) return 1.0f;
        return Mth.clamp((Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.ticksOpen - 1, this.ticksOpen) - start) / range, 0.0f, 1.0f);
    }
}
