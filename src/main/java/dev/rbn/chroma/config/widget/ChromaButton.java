package dev.rbn.chroma.config.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ChromaButton extends Button implements TickableWidget, RenderUtil {
    private final Minecraft minecraft = Minecraft.getInstance();
    private int ticksOpen = 0;

    private @Nullable final Component description;

    private float prevHovered;
    private float hovered;

    public ChromaButton(int x, int y, Component component, Component description, OnPress onPress) {
        super(x, y, 1, 1, component, onPress, Button.DEFAULT_NARRATION);

        int w = minecraft.font.width(component);
        this.setWidth((int) (w * 2));
        this.description = description;
        this.setHeight(15);

        if (this.description != null){
            this.setWidth(300);
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
        int background = withAlpha(0x00000000, (int) (getPercentFromRange(0, 10) * 150));
        int highlight = withAlpha(0x00FFFFFF, (int) (getPercentFromRange(0, 10) * (50 + (100 * Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.prevHovered, this.hovered)))));
        int highlight1 = withAlpha(0x00FFFFFF, (int) (getPercentFromRange(0, 10) * (100 + (150 * Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.prevHovered, this.hovered)))));
        int descc = withAlpha(0x00FFFFFF, (int) (getPercentFromRange(10, 20) * (200 + (50 * Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.prevHovered, this.hovered)))));

        int textc = withAlpha(0x00FFFFFF, (int) (getPercentFromRange(5, 15) * (200 + (50 * Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.prevHovered, this.hovered)))));

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), background);

        guiGraphics.fill(this.getX() + 1, this.getY() - 1, this.getX() + this.getWidth() - 1, this.getY(), background);
        guiGraphics.fill(this.getX() + 1, this.getY() + this.getHeight(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() + 1, background);

        guiGraphics.fill(this.getX() + 1, this.getY(), this.getX() + 2, this.getY() + this.getHeight(), highlight);
        guiGraphics.fill(this.getX() + this.getWidth() - 2, this.getY(), this.getX() + this.getWidth() - 1, this.getY() + this.getHeight(), highlight);
        guiGraphics.fill(this.getX() + 2, this.getY(), this.getX() + this.getWidth() - 2, this.getY() + 1, highlight);
        guiGraphics.fill(this.getX() + 2, this.getY() + this.getHeight() - 1, this.getX() + this.getWidth() - 2, this.getY() + this.getHeight(), highlight);

        Component text = drawSmartText(this.minecraft, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.message) / 2, this.getY() + 4, this.message, 0x999999, 0xFFFFFF, i, j);
        guiGraphics.drawString(this.minecraft.font, text, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.message) / 2, this.getY() + 4, this.description != null ? textc : highlight1, true);

        if (this.description != null){
            Component desc = drawSmartText(this.minecraft, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.description) / 2, this.getY() + 16, this.description, 0x606060, 0x808080, i, j);
            guiGraphics.drawString(this.minecraft.font, desc, this.getX() + (this.getWidth() / 2) - this.minecraft.font.width(this.description) / 2, this.getY() + 16, descc, true);
        }
    }

    @Override
    public void tick() {
        this.prevHovered = this.hovered;

        if (this.isHovered()){
            this.hovered = Math.clamp(this.hovered += 0.25F, 0, 1);
        } else {
            this.hovered = Math.clamp(this.hovered -= 0.25F, 0, 1);
        }
    }

    public float getPercentFromRange(float start, float end) {
        float range = end - start;
        if (range == 0.0f) return 1.0f;
        return Mth.clamp((Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.ticksOpen - 1, this.ticksOpen) - start) / range, 0.0f, 1.0f);
    }
}
