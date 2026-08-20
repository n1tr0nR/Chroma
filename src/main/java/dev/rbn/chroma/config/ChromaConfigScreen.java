package dev.rbn.chroma.config;

import dev.rbn.chroma.client.Chroma;
import dev.rbn.chroma.config.option.ConfigSection;
import dev.rbn.chroma.config.section.ConfigSectionScreen;
import dev.rbn.chroma.config.widget.ChromaButton;
import dev.rbn.chroma.config.widget.ChromaImageButton;
import dev.rbn.chroma.config.widget.RenderUtil;
import dev.rbn.chroma.submods.canvas.Target;
import dev.rbn.chroma.submods.canvas.emitter.BurstEmitter;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChromaConfigScreen extends Screen implements RenderUtil {
    private final Screen prevScreen;
    public final String modId;
    private final Config config;

    private final BurstEmitter emitter = new BurstEmitter(Target.ALL);

    private final List<ChromaButton> buttons = new ArrayList<>();

    private ChromaImageButton cancel;
    private ChromaImageButton confirm;

    private int ticksOpen = 0;

    private int scrollOffset = 0;
    private int contentHeight = 0;

    private static final int LIST_TOP = 51;
    private static final int BUTTON_SPACING = 10;
    private static final int LIST_BOTTOM_PADDING = 0;

    protected ChromaConfigScreen(Screen prevScreen, String modId) {
        super(Component.empty());

        this.prevScreen = prevScreen;
        this.modId = modId;
        this.config = GlobalConfigHandler.instance.getConfigForId(modId);

        if (this.config != null) {
            this.config.load();
        } else {
            this.onClose();
        }
    }

    @Override
    protected void init() {
        super.init();

        if (config == null) {
            onClose();
            return;
        }

        buttons.clear();

        int y = LIST_TOP + 10;

        for (ConfigSection section : config.sections) {
            ChromaButton button = new ChromaButton(
                    width,
                    y,
                    Component.translatable(section.getTranslated()),
                    Component.translatable(section.getTranslated() + ".desc"),
                    b -> { minecraft.setScreen(new ConfigSectionScreen(this, section, this.emitter)); },
                    section.color
            );

            buttons.add(button);
            addRenderableWidget(button);

            y += button.getHeight() + BUTTON_SPACING;
        }

        contentHeight = y - LIST_TOP;

        scrollOffset = 0;

        confirm = new ChromaImageButton(
                width - 40,
                10,
                b -> {
                    this.config.save();
                    this.onClose();
                },
                0xc6fc6f,
                Component.literal("Confirm"),
                Identifier.fromNamespaceAndPath(
                        Chroma.MOD_ID,
                        "button/confirm"
                )
        );

        cancel = new ChromaImageButton(
                width - 75,
                10,
                b -> this.onClose(),
                0xd40021,
                Component.literal("Cancel"),
                Identifier.fromNamespaceAndPath(
                        Chroma.MOD_ID,
                        "button/cancel"
                )
        );

        addRenderableWidget(confirm);
        addRenderableWidget(cancel);
    }

    private int getListBottom() {
        return height - LIST_BOTTOM_PADDING;
    }

    private int getListHeight() {
        return Math.max(0, getListBottom() - LIST_TOP);
    }

    private int getMaxScroll() {
        return Math.max(0, contentHeight - getListHeight());
    }

    private void clampScroll() {
        scrollOffset = Math.max(
                0,
                Math.min(scrollOffset, getMaxScroll())
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int y = LIST_TOP - scrollOffset + 10;

        for (ChromaButton button : buttons) {
            button.updatePosition(
                    guiGraphics.guiWidth() / 2 - button.getWidth() / 2,
                    y,
                    this.ticksOpen
            );

            boolean visible =
                    y + button.getHeight() >= LIST_TOP
                            && y <= getListBottom();

            button.visible = visible;
            button.active = visible;

            y += button.getHeight() + BUTTON_SPACING;
        }

        confirm.updatePosition(
                guiGraphics.guiWidth() - 40,
                10,
                this.ticksOpen
        );

        cancel.updatePosition(
                guiGraphics.guiWidth() - 75,
                10,
                this.ticksOpen
        );

        // Render the background first.
        //this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        // Clip only the scrolling section buttons.
        guiGraphics.enableScissor(
                0,
                LIST_TOP,
                guiGraphics.guiWidth(),
                getListBottom()
        );

        for (ChromaButton button : buttons) {
            if (button.visible) {
                button.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }

        guiGraphics.disableScissor();

        // Render fixed buttons outside of the scissor.
        confirm.render(guiGraphics, mouseX, mouseY, partialTick);
        cancel.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double scrollX,
            double scrollY
    ) {
        /*
         * Only scroll when the mouse is inside the section list.
         */
        if (mouseY >= LIST_TOP && mouseY <= getListBottom()) {
            scrollOffset -= (int) (scrollY * 20);
            clampScroll();

            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void tick() {
        super.tick();

        ticksOpen++;

        RandomSource source = RandomSource.create();
        int randomX = (int) (source.nextFloat() * this.width);
        int randomY = (int) (source.nextFloat() * this.height);
        if (source.nextBoolean()){
            emitter.emit(randomX, randomY, 1, 0, 1);
        }

        emitter.tick();
    }

    @Override
    public void renderBackground(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        emitter.render(guiGraphics);
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        /*
         * Main background.
         */
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

        /*
         * Header separator.
         */
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
                Identifier.fromNamespaceAndPath(modId, "icon.png"),
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
                FabricLoader.getInstance().getModContainer(this.modId);

        if (container.isPresent()) {
            ModContainer mod = container.get();
            ModMetadata metadata = mod.getMetadata();

            String name = metadata.getName();
            String version = metadata.getVersion().getFriendlyString();

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
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(prevScreen);
    }
}