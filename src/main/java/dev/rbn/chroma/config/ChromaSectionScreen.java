package dev.rbn.chroma.config;

import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.values.BooleanValue;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.widget.ChromaButton;
import dev.rbn.chroma.config.widget.RenderUtil;
import dev.rbn.chroma.config.widget.Toggle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ChromaSectionScreen extends Screen implements RenderUtil {
    private final ChromaConfigScreen parent;
    private final ConfigSection section;

    private ChromaButton exit;

    private final List<Toggle> toggles = new ArrayList<>();

    public ChromaSectionScreen(Component component, ChromaConfigScreen parent, ConfigSection section) {
        super(component);
        this.parent = parent;
        this.section = section;
    }

    @Override
    protected void init() {
        super.init();

        this.exit = new ChromaButton(0, 0, Component.literal("Exit"), null, button -> onClose());
        this.addRenderableWidget(this.exit);

        this.toggles.clear();

        int id = 0;

        for (ConfigValue<?> value : this.section.getValues()) {
            if (value instanceof BooleanValue booleanValue) {
                int offset = 60 * id;

                Toggle toggle = new Toggle(this.width - 80, 35 + offset);
                toggle.setValue(booleanValue.get());

                this.addRenderableWidget(toggle);
                this.toggles.add(toggle);

                id++;
            }
        }
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        if (this.exit != null) this.exit.updatePosition(10, 10, 200);

        guiGraphics.fillGradient(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0x80000000, 0x00000000);

        guiGraphics.fillGradient(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0x00000000, 0x80000000);

        guiGraphics.fill(60, 0, guiGraphics.guiWidth() - 30, guiGraphics.guiHeight(), 0x90000000);
        guiGraphics.fill(60, 0, 61, guiGraphics.guiHeight(), 0x90FFFFFF);
        guiGraphics.fill(guiGraphics.guiWidth() - 30, 0, guiGraphics.guiWidth() - 31, guiGraphics.guiHeight(), 0x90FFFFFF);

        int id = 0;
        for (ConfigValue<?> value : this.section.getValues()){
            if (value instanceof BooleanValue booleanValue){
                int offset = 60 * id;

                String title = value + ".title";
                String description = value + ".desc";
                int width = this.minecraft.font.width(Component.translatable(title));

                Toggle toggle = this.toggles.get(id);

                toggle.setPosition(60 + width + 30, 25 + offset);

                booleanValue.set(toggle.getValue());

                guiGraphics.fill(70, 20 + offset, guiGraphics.guiWidth() - 50, 70 + offset, 0xcc000000);
                guiGraphics.fill(70, 19 + offset, guiGraphics.guiWidth() - 50, 20 + offset, 0x40FFFFFF);
                guiGraphics.fill(70, 69 + offset, guiGraphics.guiWidth() - 50, 70 + offset, 0x40FFFFFF);
                guiGraphics.fill(guiGraphics.guiWidth() - 51, 19 + offset, guiGraphics.guiWidth() - 50, 70 + offset, 0x40FFFFFF);
                guiGraphics.fill(70, 19 + offset, 73, 70 + offset, 0xccFFFFFF);


                Component titleT = drawSmartText(this.minecraft, 80, 30 + offset, Component.translatable(title), 0x999999, 0xFFFFFF, i, j);
                Component descriptionT = drawSmartText(this.minecraft, 80, 50 + offset, Component.translatable(description), 0x757575, 0x909090, i, j);

                guiGraphics.drawString(this.minecraft.font, titleT, 80, 30 + offset, 0xFFFFFFFF, true);
                guiGraphics.drawString(this.minecraft.font, descriptionT, 80, 50 + offset, 0xFF909090, true);

                id++;
            }
        }

        super.render(guiGraphics, i, j, f);
    }

    @Override
    public void onClose() {
        ConfigManager.save(ChromaConfig.getSections());

        this.parent.setTicksOpen(200);
        this.minecraft.setScreen(this.parent);
    }
}
