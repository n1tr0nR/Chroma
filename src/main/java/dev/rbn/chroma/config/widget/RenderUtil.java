package dev.rbn.chroma.config.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface RenderUtil {
    default int withAlpha(int color, int alpha) {
        alpha = Math.max(0, Math.min(255, alpha));
        return (alpha << 24) | (color & 0x00FFFFFF);
    }

    default Component drawSmartText(Minecraft minecraft, int startX, int startY, Component text, int defaultColor, int highlightedColor, int mouseX, int mouseY) {
        final float radius = 20.0f;

        String string = text.getString();
        MutableComponent result = Component.empty();

        int currentX = startX;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            int charWidth = minecraft.font.width(String.valueOf(c));

            float charCenterX = currentX + charWidth * 0.5f;
            float charCenterY = startY + minecraft.font.lineHeight * 0.5f;

            float dx = mouseX - charCenterX;
            float dy = mouseY - charCenterY;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            float t = 1.0f - Math.min(dist / radius, 1.0f);

            t = t * t * (3.0f - 2.0f * t);

            int r1 = (defaultColor >> 16) & 255;
            int g1 = (defaultColor >> 8) & 255;
            int b1 = defaultColor & 255;

            int r2 = (highlightedColor >> 16) & 255;
            int g2 = (highlightedColor >> 8) & 255;
            int b2 = highlightedColor & 255;

            int r = (int) (r1 + (r2 - r1) * t);
            int g = (int) (g1 + (g2 - g1) * t);
            int b = (int) (b1 + (b2 - b1) * t);

            int color = (r << 16) | (g << 8) | b;

            result.append(
                    Component.literal(String.valueOf(c))
                            .withColor(color)
            );

            currentX += charWidth;
        }

        return result;
    }
}
