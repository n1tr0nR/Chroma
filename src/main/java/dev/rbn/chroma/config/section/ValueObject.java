package dev.rbn.chroma.config.section;

import com.mojang.serialization.Codec;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.widget.RenderUtil;
import dev.rbn.chroma.config.widget.Slider;
import dev.rbn.chroma.config.widget.TickableWidget;
import dev.rbn.chroma.config.widget.Toggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ValueObject extends AbstractWidget implements TickableWidget, RenderUtil {
    private final ConfigSection section;
    private final ConfigValue<?> value;
    private final @Nullable AbstractWidget targetWidget;

    public ValueObject(int x, int y, Component component, ConfigSection section, ConfigValue<?> value) {
        super(x, y, 200, 40, component);
        this.section = section;
        this.value = value;
        if (value.getCodec().equals(Codec.BOOL)) {
            Toggle toggle = new Toggle(this.getX(), this.getY());
            toggle.setValue((boolean) value.get());

            targetWidget = toggle;
        } else if (value.get() instanceof Integer ||
                value.get() instanceof Float ||
                value.get() instanceof Double) {
            Slider.ValueType type;

            if (value.get() instanceof Integer) {
                type = Slider.ValueType.INTEGER;
            } else if (value.get() instanceof Float) {
                type = Slider.ValueType.FLOAT;
            } else {
                type = Slider.ValueType.DOUBLE;
            }

            Number current = (Number) value.get();
            Number min = (Number) value.getMin();
            Number max = (Number) value.getMax();

            targetWidget = new Slider(
                    this.getX(),
                    this.getY(),
                    180,
                    current.doubleValue(),
                    min.doubleValue(),
                    max.doubleValue(),
                    type
            );
        } else {
            targetWidget = null;
        }
    }

    @Override
    public void tick() {
        if (targetWidget instanceof TickableWidget tickableWidget){
            tickableWidget.tick();
        }
    }

    @Override
    protected void renderWidget(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();

        int sectionColor = (0xFF << 24) | (this.section.color & 0x00FFFFFF);
        int translucentSectionColor = (0x10 << 24) | (this.section.color & 0x00FFFFFF);
        int semitranslucentSectionColor = (0x20 << 24) | (this.section.color & 0x00FFFFFF);

        boolean modified = !value.get().equals(value.getDefaultValue());

        if (modified) {
            int resetX = this.getX() - 38;
            int resetY = this.getY() + 13;
            int resetWidth = 34;
            int resetHeight = 16;

            boolean hovered = isMouseOverReset(i, j);

            int outlineColor = hovered
                    ? 0xFFFFFFFF
                    : semitranslucentSectionColor;

            int backgroundColor = hovered
                    ? 0xFF000000
                    : 0xC0000000;

            int textColor = hovered
                    ? 0xFFFFFFFF
                    : sectionColor;

            guiGraphics.fill(
                    resetX,
                    resetY,
                    resetX + resetWidth,
                    resetY + resetHeight,
                    backgroundColor
            );

            if (!hovered) {
                guiGraphics.fill(
                        resetX,
                        resetY,
                        resetX + resetWidth,
                        resetY + resetHeight,
                        semitranslucentSectionColor
                );
            }

            guiGraphics.fill(
                    resetX,
                    resetY,
                    resetX + resetWidth,
                    resetY + 1,
                    outlineColor
            );

            guiGraphics.fill(
                    resetX,
                    resetY + resetHeight - 1,
                    resetX + resetWidth,
                    resetY + resetHeight,
                    outlineColor
            );

            guiGraphics.fill(
                    resetX,
                    resetY + 1,
                    resetX + 1,
                    resetY + resetHeight - 1,
                    outlineColor
            );

            guiGraphics.fill(
                    resetX + resetWidth - 1,
                    resetY + 1,
                    resetX + resetWidth,
                    resetY + resetHeight - 1,
                    outlineColor
            );

            Component resetText = Component.literal("Reset");

            int textWidth = minecraft.font.width(resetText);

            guiGraphics.drawString(
                    minecraft.font,
                    resetText,
                    resetX + (resetWidth - textWidth) / 2,
                    resetY + 4,
                    textColor,
                    false
            );
        }

        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0xc0000000);
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), semitranslucentSectionColor);

        guiGraphics.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, 0xFF000000);
        guiGraphics.fillGradient(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + this.getHeight() - 1, translucentSectionColor, 0x00FFFFFF);

        guiGraphics.drawString(minecraft.font, this.message, this.getX() + 5, this.getY() + 5, sectionColor);

        if (this.targetWidget != null){
            targetWidget.setX(this.getX() + 10);
            targetWidget.setY(this.getY() + 18);

            targetWidget.render(guiGraphics, i, j, f);
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (isMouseOverReset(mouseX, mouseY)) {
            return true;
        }

        if (targetWidget == null) {
            return false;
        }

        return targetWidget.isMouseOver(mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean bl) {
        if (isMouseOverReset(event.x(), event.y())) {
            resetValue();
            return true;
        }

        if (targetWidget == null) {
            return false;
        }

        boolean clicked = targetWidget.mouseClicked(event, bl);

        if (clicked && targetWidget instanceof Toggle toggle) {
            ((ConfigValue<Boolean>) value).set(toggle.getValue());
        }

        return clicked;
    }

    private void resetValue() {
        value.reset();

        if (targetWidget instanceof Toggle toggle) {
            toggle.setValue((Boolean) value.get());
        } else if (targetWidget instanceof Slider slider) {
            slider.setValue(((Number) value.get()).doubleValue());
        }
    }

    private boolean isMouseOverReset(double mouseX, double mouseY) {
        if (value.get().equals(value.getDefaultValue())) {
            return false;
        }

        int resetX = this.getX() - 48;
        int resetY = this.getY() + 10;
        int resetWidth = 42;
        int resetHeight = 20;

        return mouseX >= resetX
                && mouseX <= resetX + resetWidth
                && mouseY >= resetY
                && mouseY <= resetY + resetHeight;
    }

    @Override
    public boolean mouseDragged(
            @NonNull MouseButtonEvent event,
            double mouseX,
            double mouseY
    ) {
        if (targetWidget == null) return false;

        boolean dragged = targetWidget.mouseDragged(event, mouseX, mouseY);

        if (dragged && targetWidget instanceof Slider slider) {
            double sliderValue = slider.getValue();

            if (value.get() instanceof Integer) {
                ((ConfigValue<Integer>) value).set((int) sliderValue);
            } else if (value.get() instanceof Float) {
                ((ConfigValue<Float>) value).set((float) sliderValue);
            } else if (value.get() instanceof Double) {
                ((ConfigValue<Double>) value).set(sliderValue);
            }
        }

        return dragged;
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        if (targetWidget == null) return false;

        boolean released = targetWidget.mouseReleased(event);

        if (targetWidget instanceof Slider slider) {
            double sliderValue = slider.getValue();

            if (value.get() instanceof Integer) {
                ((ConfigValue<Integer>) value).set((int) sliderValue);
            } else if (value.get() instanceof Float) {
                ((ConfigValue<Float>) value).set((float) sliderValue);
            } else if (value.get() instanceof Double) {
                ((ConfigValue<Double>) value).set(sliderValue);
            }
        }

        return released;
    }

    @SuppressWarnings("unchecked")
    private void updateSliderValue(Slider slider) {
        double sliderValue = slider.getValue();

        if (value.get() instanceof Integer) {
            ((ConfigValue<Integer>) value).set((int) sliderValue);
        } else if (value.get() instanceof Float) {
            ((ConfigValue<Float>) value).set((float) sliderValue);
        } else if (value.get() instanceof Double) {
            ((ConfigValue<Double>) value).set(sliderValue);
        }
    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {

    }

    public void updatePosition(int x, int y){
        this.setX(x);
        this.setY(y);
    }
}
