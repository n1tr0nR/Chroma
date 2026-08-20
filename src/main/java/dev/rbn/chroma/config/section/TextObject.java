package dev.rbn.chroma.config.section;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class TextObject extends AbstractWidget {
    public TextObject(int x, int y, Component component) {
        super(x, y, 0, 13, component);
        int width = Minecraft.getInstance().font.width(component);
        this.setWidth(width);
    }

    @Override
    protected void renderWidget(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.drawString(Minecraft.getInstance().font, this.message, this.getX(), this.getY(), 0xFF707070);
    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {

    }
}
