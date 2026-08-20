package dev.rbn.chroma.config.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class Toggle extends AbstractWidget {
    public boolean value = false;

    public Toggle(int x, int y) {
        super(x, y, 30, 15, Component.empty());
    }

    @Override
    protected void renderWidget(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        int outlineColor = getValue() ? 0x40c6fc6f : 0x40d40021;

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0xFF000000);

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 1, this.isHovered() ? 0xFFFFFFFF : outlineColor);
        guiGraphics.fill(this.getX(), this.getY() + this.getHeight() - 1, this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.isHovered() ? 0xFFFFFFFF : outlineColor);
        guiGraphics.fill(this.getX(), this.getY() + 1, this.getX() + 1, this.getY() + this.getHeight() - 1, this.isHovered() ? 0xFFFFFFFF : outlineColor);
        guiGraphics.fill(this.getX() + getWidth() - 1, this.getY() + 1, this.getX() + getWidth(), this.getY() + this.getHeight() - 1, this.isHovered() ? 0xFFFFFFFF : outlineColor);

        if (getValue()){
            guiGraphics.fill(this.getX() + 3, this.getY() + 3, this.getX() + this.getWidth() - 3, this.getY() + this.getHeight() - 3, 0x40c6fc6f);
            guiGraphics.fill(this.getX() + (this.width / 2), this.getY() + 3, this.getX() + this.getWidth() - 3, this.getY() + this.getHeight() - 3, 0xFFc6fc6f);
        } else {
            guiGraphics.fill(this.getX() + 3, this.getY() + 3, this.getX() + this.getWidth() - 3, this.getY() + this.getHeight() - 3, 0x40d40021);
            guiGraphics.fill(this.getX() + 3, this.getY() + 3, this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() - 3, 0xFFd40021);
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent mouseButtonEvent, boolean bl) {
        setValue(!getValue());
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {

    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
