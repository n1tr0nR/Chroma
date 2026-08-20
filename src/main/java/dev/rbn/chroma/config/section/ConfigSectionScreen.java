package dev.rbn.chroma.config.section;

import dev.rbn.chroma.config.ChromaConfigScreen;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.option.SectionChunk;
import dev.rbn.chroma.config.option.values.ConfigValue;
import dev.rbn.chroma.config.option.values.TextPoint;
import dev.rbn.chroma.config.widget.ChromaButton;
import dev.rbn.chroma.config.widget.RenderUtil;
import dev.rbn.chroma.submods.canvas.emitter.BurstEmitter;
import dev.rbn.chroma.submods.canvas.emitter.ScreenEmitter;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConfigSectionScreen extends Screen implements RenderUtil {
    private final ChromaConfigScreen parent;
    private final ConfigSection section;
    private final BurstEmitter emitter;

    private int scrollOffset = 0;
    private int contentHeight = 0;

    private static final int LIST_TOP = 51;
    private static final int BUTTON_SPACING = 10;
    private static final int LIST_BOTTOM_PADDING = 0;

    private final List<AbstractWidget> valueObjects = new ArrayList<>();

    public ConfigSectionScreen(ChromaConfigScreen parent, ConfigSection section, BurstEmitter emitter) {
        super(Component.empty());

        this.parent = parent;
        this.section = section;
        this.emitter = emitter;
    }

    @Override
    protected void init() {
        super.init();

        valueObjects.clear();

        int y = LIST_TOP + 10;

        for (SectionChunk value : this.section.getValues()) {
            if (value instanceof ConfigValue<?> configValue){
                ValueObject obj = new ValueObject(
                        this.width / 2 - 100,
                        y,
                        Component.translatable(value.toString()),
                        this.section,
                        configValue
                );

                valueObjects.add(obj);
                addRenderableWidget(obj);

                y += obj.getHeight() + BUTTON_SPACING;
            } else if (value instanceof TextPoint textPoint){
                TextObject text = new TextObject(this.width / 2 - 10, y, textPoint.text);
                text.setX(this.width / 2);
                valueObjects.add(text);
                addRenderableOnly(text);

                y += text.getHeight() + BUTTON_SPACING;
            }
        }

        this.valueObjects.add(new ChromaButton(this.width / 2, y, Component.literal("Back"), null, button -> { this.onClose(); }, 0xFFFFFFFF));
        this.addRenderableWidget(this.valueObjects.getLast());
        if (this.valueObjects.getLast() instanceof ChromaButton button){
            button.updatePosition(0, y, 50);
        }
        y += 30;

        contentHeight = y - LIST_TOP;

        scrollOffset = 0;
    }

    private int getListBottom() {
        return height - LIST_BOTTOM_PADDING;
    }

    private int getListHeight() {
        return Math.max(
                0,
                getListBottom() - LIST_TOP
        );
    }

    private int getMaxScroll() {
        return Math.max(
                0,
                contentHeight - getListHeight()
        );
    }

    private void clampScroll() {
        scrollOffset = Math.max(
                0,
                Math.min(
                        scrollOffset,
                        getMaxScroll()
                )
        );
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        /*
         * Position the value objects BEFORE rendering them.
         */
        int y = LIST_TOP - scrollOffset + 10;

        for (AbstractWidget obj : valueObjects) {
            obj.setX(this.width / 2 - obj.getWidth() / 2);
            obj.setY(y);

            boolean visible =
                    y + obj.getHeight() >= LIST_TOP
                            && y <= getListBottom();

            obj.visible = visible;
            obj.active = visible;

            y += obj.getHeight() + BUTTON_SPACING;
        }

        /*
         * Clip ONLY the value objects.
         */
        guiGraphics.enableScissor(
                0,
                LIST_TOP,
                guiGraphics.guiWidth(),
                getListBottom()
        );

        for (AbstractWidget obj : valueObjects) {
            if (obj.visible) {
                obj.render(
                        guiGraphics,
                        mouseX,
                        mouseY,
                        partialTick
                );
            }
        }

        guiGraphics.disableScissor();
    }

    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double scrollX,
            double scrollY
    ) {
        /*
         * Only scroll when the mouse is inside
         * the value list.
         */
        if (mouseY >= LIST_TOP && mouseY <= getListBottom()) {
            scrollOffset -= (int) (scrollY * 20);

            clampScroll();

            return true;
        }

        return super.mouseScrolled(
                mouseX,
                mouseY,
                scrollX,
                scrollY
        );
    }

    @Override
    public void tick() {
        super.tick();
        emitter.tick();

        RandomSource source = RandomSource.create();
        int randomX = (int) (source.nextFloat() * this.width);
        int randomY = (int) (source.nextFloat() * this.height);
        if (source.nextBoolean()){
            emitter.emit(randomX, randomY, 1, 0, 1, this.section.color);
        }
    }

    @Override
    public void renderBackground(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        emitter.render(guiGraphics);

        super.renderBackground(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );

        guiGraphics.fillGradient(
                0,
                0,
                guiGraphics.guiWidth(),
                guiGraphics.guiHeight(),
                0xFF000000,
                0x00000000
        );

        guiGraphics.fillGradient(
                0,
                0,
                guiGraphics.guiWidth(),
                guiGraphics.guiHeight(),
                0x00000000,
                0x80000010
        );

        guiGraphics.fill(
                0,
                50,
                guiGraphics.guiWidth(),
                51,
                0x50FFFFFF
        );

        guiGraphics.fillGradient(
                0,
                51,
                guiGraphics.guiWidth(),
                guiGraphics.guiHeight() / 2,
                0x10FFFFFF,
                0x00FFFFFF
        );

        /*
         * Mod icon.
         */
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(
                        this.parent.modId,
                        "icon.png"
                ),
                10,
                10,
                0,
                0,
                32,
                32,
                32,
                32
        );

        /*
         * Mod metadata.
         */
        Optional<ModContainer> container =
                FabricLoader.getInstance()
                        .getModContainer(this.parent.modId);

        if (container.isPresent()) {
            ModContainer mod = container.get();
            ModMetadata metadata = mod.getMetadata();

            String name = metadata.getName();
            String version =
                    metadata.getVersion().getFriendlyString();

            String authors = metadata.getAuthors()
                    .stream()
                    .map(Person::getName)
                    .limit(3)
                    .collect(Collectors.joining(", "));

            if (metadata.getAuthors().size() > 3) {
                authors += ", ...";
            }

            guiGraphics.drawString(
                    this.minecraft.font,
                    name,
                    45,
                    10,
                    0xFFFFFFFF
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    version,
                    45
                            + this.minecraft.font.width(name)
                            + 60
                            - this.minecraft.font.width(version),
                    10,
                    0x30FFFFFF
            );

            guiGraphics.fill(
                    45,
                    21,
                    140,
                    22,
                    0x30FFFFFF
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    "Authors:",
                    45,
                    24,
                    0x30FFFFFF
            );

            guiGraphics.drawString(
                    this.minecraft.font,
                    authors,
                    45,
                    35,
                    0x60FFFFFF
            );
        }

        /*
         * Section title.
         */
        guiGraphics.pose().pushMatrix();

        float scale = 1.5f;

        Component text =
                Component.translatable(
                        this.section.getTranslated()
                );

        int textWidth = this.minecraft.font.width(text);

        int textX =
                (int) (
                        (
                                guiGraphics.guiWidth() / scale
                                        - textWidth
                        ) / 2.0f
                );

        int textY = 10;

        int scaledMouseX =
                (int) (mouseX / scale);

        int scaledMouseY =
                (int) (mouseY / scale);

        guiGraphics.pose().pushMatrix();

        guiGraphics.pose().scale(scale);

        int darkerT =
                (this.section.color & 0xFF000000)
                        | (
                        (int) (
                                (
                                        (this.section.color >> 16)
                                                & 0xFF
                                ) * 0.8F
                        ) << 16
                )
                        | (
                        (int) (
                                (
                                        (this.section.color >> 8)
                                                & 0xFF
                                ) * 0.9F
                        ) << 8
                )
                        | (
                        (int) (
                                (this.section.color & 0xFF)
                                        * 0.7F
                        )
                );

        int darkerD =
                (this.section.color & 0xFF000000)
                        | (
                        (int) (
                                (
                                        (this.section.color >> 16)
                                                & 0xFF
                                ) * 0.6F
                        ) << 16
                )
                        | (
                        (int) (
                                (
                                        (this.section.color >> 8)
                                                & 0xFF
                                ) * 0.6F
                        ) << 8
                )
                        | (
                        (int) (
                                (this.section.color & 0xFF)
                                        * 0.5F
                        )
                );

        Component correct = this.drawSmartText(
                minecraft,
                textX,
                textY,
                text,
                darkerT,
                this.section.color,
                scaledMouseX,
                scaledMouseY
        );

        guiGraphics.drawString(
                this.minecraft.font,
                correct,
                textX,
                textY,
                0xFFFFFFFF
        );

        guiGraphics.pose().popMatrix();

        /*
         * Section description.
         */
        int descTextWidth =
                this.font.width(
                        Component.translatable(
                                this.section.getTranslated() + ".desc"
                        )
                );

        int descTextX =
                (
                        guiGraphics.guiWidth()
                                - descTextWidth
                ) / 2;

        int descTextY = 30;

        Component desc = this.drawSmartText(
                minecraft,
                descTextX,
                descTextY,
                Component.translatable(
                        this.section.getTranslated() + ".desc"
                ),
                darkerD,
                darkerT,
                mouseX,
                mouseY
        );

        guiGraphics.drawString(
                this.minecraft.font,
                desc,
                descTextX,
                descTextY,
                0xFFFFFFFF
        );
    }

    @Override
    public void onClose() {
        super.onClose();

        this.minecraft.setScreen(this.parent);
    }
}