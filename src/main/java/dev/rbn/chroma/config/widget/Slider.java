package dev.rbn.chroma.config.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

import java.util.Locale;

public class Slider extends AbstractWidget {
    public enum ValueType {
        INTEGER,
        FLOAT,
        DOUBLE
    }

    private double value;
    private final double min;
    private final double max;
    private final ValueType valueType;

    private boolean dragging;

    public Slider(
            int x,
            int y,
            int width,
            double value,
            double min,
            double max,
            ValueType valueType
    ) {
        super(x, y, width, 15, Component.empty());

        this.min = min;
        this.max = max;
        this.valueType = valueType;
        this.value = Mth.clamp(value, min, max);
    }

    @Override
    protected void renderWidget(
            @NonNull GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        int outlineColor = isHovered()
                ? 0xFFFFFFFF
                : 0x40606060;

        guiGraphics.fill(
                getX(),
                getY(),
                getX() + getWidth(),
                getY() + getHeight(),
                0xFF000000
        );

        guiGraphics.fill(
                getX(),
                getY(),
                getX() + getWidth(),
                getY() + 1,
                outlineColor
        );

        guiGraphics.fill(
                getX(),
                getY() + getHeight() - 1,
                getX() + getWidth(),
                getY() + getHeight(),
                outlineColor
        );

        guiGraphics.fill(
                getX(),
                getY() + 1,
                getX() + 1,
                getY() + getHeight() - 1,
                outlineColor
        );

        guiGraphics.fill(
                getX() + getWidth() - 1,
                getY() + 1,
                getX() + getWidth(),
                getY() + getHeight() - 1,
                outlineColor
        );

        int trackX = getX() + 3;
        int trackWidth = getWidth() - 6;

        guiGraphics.fill(
                trackX,
                getY() + 5,
                trackX + trackWidth,
                getY() + 10,
                0x40202020
        );

        double percentage = max == min
                ? 0
                : (value - min) / (max - min);

        int filledWidth = (int) (trackWidth * percentage);

        guiGraphics.fill(
                trackX,
                getY() + 5,
                trackX + filledWidth,
                getY() + 10,
                0x40c6fc6f
        );

        int handleX = trackX + filledWidth - 2;

        guiGraphics.fill(
                handleX,
                getY() + 3,
                handleX + 5,
                getY() + 12,
                isHovered()
                        ? 0xFFFFFFFF
                        : 0xFFc6fc6f
        );

        String text = formatValue();

        var font = Minecraft.getInstance().font;

        guiGraphics.drawString(
                font,
                text,
                getX() + getWidth() / 2 - font.width(text) / 2,
                getY() + 3,
                0xFFFFFFFF,
                true
        );
    }

    @Override
    public boolean mouseClicked(
            @NonNull MouseButtonEvent event,
            boolean bl
    ) {
        if (event.button() != 0 || !isMouseOver(event.x(), event.y())) {
            return false;
        }

        dragging = true;
        updateValue(event.x());

        return true;
    }

    @Override
    public boolean mouseDragged(
            @NonNull MouseButtonEvent event,
            double mouseX,
            double mouseY
    ) {
        if (!dragging) {
            return false;
        }

        updateValue(event.x());

        return true;
    }

    @Override
    public boolean mouseReleased(
            @NonNull MouseButtonEvent event
    ) {
        if (event.button() == 0) {
            dragging = false;
        }

        return true;
    }

    private void updateValue(double mouseX) {
        double trackX = getX() + 3;
        double trackWidth = getWidth() - 6;

        double percentage = (mouseX - trackX) / trackWidth;
        percentage = Mth.clamp(percentage, 0.0, 1.0);

        double newValue = min + (max - min) * percentage;

        if (!Minecraft.getInstance().hasControlDown()) {
            newValue = Math.round(newValue / 10.0) * 10.0;
        }

        if (valueType == ValueType.INTEGER) {
            newValue = Math.round(newValue);
        }

        value = Mth.clamp(newValue, min, max);
    }

    private String formatValue() {
        return switch (valueType) {
            case INTEGER -> Integer.toString((int) value);
            case FLOAT -> String.format(Locale.ROOT, "%.2f", value);
            case DOUBLE -> String.format(Locale.ROOT, "%.2f", value);
        };
    }

    public void setValue(double value) {
        if (valueType == ValueType.INTEGER) {
            value = Math.round(value);
        }

        this.value = Mth.clamp(value, min, max);
    }

    public double getValue() {
        return value;
    }

    @Override
    protected void updateWidgetNarration(
            @NonNull NarrationElementOutput narrationElementOutput
    ) {
    }
}