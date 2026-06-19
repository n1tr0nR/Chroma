package dev.rbn.chroma.config;

import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.values.BooleanValue;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.option.values.IntegerValue;
import dev.rbn.chroma.config.widget.ChromaButton;
import dev.rbn.chroma.config.widget.RenderUtil;
import dev.rbn.chroma.config.widget.Toggle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class ChromaSectionScreen extends Screen implements RenderUtil {

    private final ChromaConfigScreen parent;
    private final ConfigSection section;

    private ChromaButton exit;

    private final Map<BooleanValue, Toggle> toggles = new HashMap<>();
    private final Map<IntegerValue, EditBox> integerBoxes = new HashMap<>();

    public ChromaSectionScreen(
            Component title,
            ChromaConfigScreen parent,
            ConfigSection section
    ) {
        super(title);
        this.parent = parent;
        this.section = section;
    }

    @Override
    protected void init() {
        super.init();

        this.toggles.clear();
        this.integerBoxes.clear();

        this.exit = new ChromaButton(
                10,
                10,
                Component.literal("Exit"),
                null,
                button -> onClose()
        );

        this.addRenderableWidget(this.exit);

        int row = 0;

        for (ConfigValue<?> value : this.section.getValues()) {
            int y = 25 + row * 60;

            if (value instanceof BooleanValue booleanValue) {
                Toggle toggle = new Toggle(0, y);

                toggle.setValue(booleanValue.get());

                this.toggles.put(booleanValue, toggle);
                this.addRenderableWidget(toggle);
            }

            if (value instanceof IntegerValue integerValue) {
                EditBox box = new EditBox(
                        this.font,
                        0,
                        y,
                        80,
                        20,
                        Component.empty()
                );

                box.setValue(String.valueOf(integerValue.get()));

                box.setFilter(text -> {
                    if (text.isEmpty()) {
                        return true;
                    }

                    try {
                        Integer.parseInt(text);
                        return true;
                    } catch (NumberFormatException ignored) {
                        return false;
                    }
                });

                box.setResponder(text -> {
                    if (!text.isEmpty()) {
                        try {
                            integerValue.set(Integer.parseInt(text));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                });

                this.integerBoxes.put(integerValue, box);
                this.addRenderableWidget(box);
            }

            row++;
        }
    }

    @Override
    public void render(
            @NonNull GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        if (this.exit != null) {
            this.exit.updatePosition(10, 10, 200);
        }

        guiGraphics.fillGradient(
                0,
                0,
                guiGraphics.guiWidth(),
                guiGraphics.guiHeight(),
                0x80000000,
                0x00000000
        );

        guiGraphics.fillGradient(
                0,
                0,
                guiGraphics.guiWidth(),
                guiGraphics.guiHeight(),
                0x00000000,
                0x80000000
        );

        guiGraphics.fill(
                60,
                0,
                guiGraphics.guiWidth() - 30,
                guiGraphics.guiHeight(),
                0x90000000
        );

        guiGraphics.fill(
                60,
                0,
                61,
                guiGraphics.guiHeight(),
                0x90FFFFFF
        );

        guiGraphics.fill(
                guiGraphics.guiWidth() - 30,
                0,
                guiGraphics.guiWidth() - 31,
                guiGraphics.guiHeight(),
                0x90FFFFFF
        );

        int row = 0;

        for (ConfigValue<?> value : this.section.getValues()) {
            int offset = row * 60;

            String titleKey = value + ".title";
            String descKey = value + ".desc";

            Component title = Component.translatable(titleKey).append((value.get() != value.getDefaultValue() ? "*" : ""));
            Component description = Component.translatable(descKey);

            guiGraphics.fill(
                    70,
                    20 + offset,
                    guiGraphics.guiWidth() - 50,
                    70 + offset,
                    0xCC000000
            );

            guiGraphics.fill(
                    70,
                    19 + offset,
                    guiGraphics.guiWidth() - 50,
                    20 + offset,
                    0x40FFFFFF
            );

            guiGraphics.fill(
                    70,
                    69 + offset,
                    guiGraphics.guiWidth() - 50,
                    70 + offset,
                    0x40FFFFFF
            );

            guiGraphics.fill(
                    guiGraphics.guiWidth() - 51,
                    19 + offset,
                    guiGraphics.guiWidth() - 50,
                    70 + offset,
                    0x40FFFFFF
            );

            guiGraphics.fill(
                    70,
                    19 + offset,
                    73,
                    70 + offset,
                    0xCCFFFFFF
            );

            int widgetX = 60 + this.font.width(title) + 30;

            if (value instanceof BooleanValue booleanValue) {
                Toggle toggle = this.toggles.get(booleanValue);

                if (toggle != null) {
                    toggle.setPosition(widgetX, 25 + offset);

                    // Sync GUI -> config
                    booleanValue.set(toggle.getValue());
                }
            }

            if (value instanceof IntegerValue integerValue) {
                EditBox box = this.integerBoxes.get(integerValue);

                if (box != null) {
                    box.setPosition(widgetX, 25 + offset);
                }
            }

            Component smartTitle = drawSmartText(
                    this.minecraft,
                    80,
                    30 + offset,
                    title,
                    0x999999,
                    0xFFFFFF,
                    mouseX,
                    mouseY
            );

            Component smartDescription = drawSmartText(
                    this.minecraft,
                    80,
                    47 + offset,
                    description,
                    0x757575,
                    0x909090,
                    mouseX,
                    mouseY
            );

            Component smartDefault = drawSmartText(
                    this.minecraft,
                    80,
                    58 + offset,
                    Component.literal("Default: " + value.getDefaultValue()),
                    0x404040,
                    0x505050,
                    mouseX,
                    mouseY
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    smartTitle,
                    80,
                    30 + offset,
                    0xFFFFFFFF,
                    true
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    smartDescription,
                    80,
                    47 + offset,
                    0xFF909090,
                    true
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    smartDefault,
                    80,
                    58 + offset,
                    0xFF606060,
                    true
            );

            row++;
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        ConfigManager.save(ChromaConfig.getSections());

        this.parent.setTicksOpen(200);
        this.minecraft.setScreen(this.parent);
    }
}