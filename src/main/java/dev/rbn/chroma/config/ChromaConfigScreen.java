package dev.rbn.chroma.config;

import dev.rbn.chroma.Chroma;
import dev.rbn.chroma.config.widget.ChromaButton;
import dev.rbn.chroma.config.widget.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class ChromaConfigScreen extends Screen implements RenderUtil {
    private final Screen prevScreen;

    private ChromaButton exit;
    private ChromaButton shader;
    private ChromaButton screenshake;
    private ChromaButton effects;

    private int ticksOpen = 0;

    protected ChromaConfigScreen(Screen prevScreen) {
        super(Component.empty());
        this.prevScreen = prevScreen;
    }

    @Override
    protected void init() {
        super.init();

        this.exit = new ChromaButton(0, 0, Component.literal("Exit"), null, button -> onClose());
        this.addRenderableWidget(this.exit);

        this.shader = new ChromaButton(0, 0, Component.literal("Shader Pipeline"), Component.literal("Configure visual shader effects."), button -> this.minecraft.setScreen(new ShaderSectionScreen(this)));
        this.addRenderableWidget(this.shader);

        this.screenshake = new ChromaButton(0, 0, Component.literal("Screenshake"), Component.literal("Configure camera shake effects."), button -> this.minecraft.setScreen(new ScreenshakeSectionScreen(this)));
        this.addRenderableWidget(this.screenshake);

        this.effects = new ChromaButton(0, 0, Component.literal("Effects"), Component.literal("Adjust Chroma's built in effects."), button -> this.minecraft.setScreen(new EffectsSectionScreen(this)));
        this.addRenderableWidget(this.effects);
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);

        if (this.exit != null) this.exit.updatePosition(10, 10, this.ticksOpen);

        if (this.shader != null) this.shader.updatePosition(guiGraphics.guiWidth() / 2 - (this.shader.getWidth() / 2), guiGraphics.guiHeight() - 150, this.ticksOpen);

        if (this.screenshake != null) this.screenshake.updatePosition(guiGraphics.guiWidth() / 2 - (this.screenshake.getWidth() / 2), guiGraphics.guiHeight() - 110, this.ticksOpen);

        if (this.effects != null) this.effects.updatePosition(guiGraphics.guiWidth() / 2 - (this.effects.getWidth() / 2), guiGraphics.guiHeight() - 70, this.ticksOpen);
    }

    @Override
    public void renderBackground(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        super.renderBackground(guiGraphics, mouseX, mouseY, f);
        renderTop(guiGraphics, mouseX, mouseY);
    }

    public void renderTop(GuiGraphics guiGraphics, int mouseX, int mouseY){
        guiGraphics.fillGradient(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0x80000000, 0x00000000);

        guiGraphics.fillGradient(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0x00000000, 0x80000000);

        int startX = guiGraphics.guiWidth() / 2;
        int startY = 10;

        int width = 200;
        int height = 80;

        int background = withAlpha(0x00000000, (int) (getPercentFromRange(0, 10) * 150));
        int border = withAlpha(0x80FFFFFF, (int) (getPercentFromRange(5, 25) * 150));

        guiGraphics.fill(startX - width / 2, startY, startX + width / 2, startY + height, background);

        guiGraphics.fill(startX - width / 2 + 1, startY - 1, startX + width / 2 - 1, startY, background);
        guiGraphics.fill(startX - width / 2 + 1, startY + height, startX + width / 2 - 1, startY + height + 1, background);

        guiGraphics.fill(startX - width / 2 + 1, startY, startX - width / 2 + 2, startY + height, border);
        guiGraphics.fill(startX + width / 2 - 2, startY, startX + width / 2 - 1, startY + height, border);
        guiGraphics.fill(startX - width / 2 + 2, startY, startX + width / 2 - 2, startY + 1, border);
        guiGraphics.fill(startX - width / 2 + 2, startY + height - 1, startX + width / 2 - 2, startY + height, border);

        Identifier texture = Identifier.fromNamespaceAndPath(Chroma.MOD_ID, "icon.png");

        int chroma = withAlpha(0xFFFFFFFF, (int) (getPercentFromRange(25, 35) * 255));

        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                startX - 38, startY + 3,
                0, 0,
                32, 32,
                32, 32,
                chroma
        );


        Component component = drawSmartText(this.minecraft, startX - this.minecraft.font.width(Component.literal("Chroma")) / 2 + 15, startY + 15, Component.literal("Chroma"), 0xc080cc, 0xc099cc, mouseX, mouseY);
        guiGraphics.drawString(minecraft.font, component, startX - this.minecraft.font.width(component) / 2 + 15, startY + 15, chroma, true);


        int desc1 = withAlpha(0xFFFFFFFF, (int) (getPercentFromRange(35, 45) * 255));
        int desc2 = withAlpha(0xFFFFFFFF, (int) (getPercentFromRange(40, 50) * 100));
        int desc3 = withAlpha(0xFFFFFFFF, (int) (getPercentFromRange(45, 55) * 200));

        Component component1 = drawSmartText(this.minecraft, startX - this.minecraft.font.width(Component.literal("Rendering at its finest.")) / 2, startY + 37, Component.literal("Rendering at its finest."), 0x808080, 0x999999, mouseX, mouseY);
        guiGraphics.drawString(minecraft.font, component1, startX - this.minecraft.font.width(component1) / 2, startY + 37, desc1, true);

        guiGraphics.fill(startX - width / 2 + 30, startY + 50, startX + width / 2 - 30, startY + 51, desc2);

        Component data = drawSmartText(this.minecraft, startX - this.minecraft.font.width(Component.literal("Chroma: 1.0v | NitronRbn.")) / 2, startY + 65, Component.literal("Chroma: 1.0v | NitronRbn."), 0x808080, 0x999999, mouseX, mouseY);
        guiGraphics.drawString(minecraft.font, data, startX - this.minecraft.font.width(data) / 2, startY + 65, desc3, true);
    }

    public float getPercentFromRange(float start, float end) {
        float range = end - start;
        if (range == 0.0f) return 1.0f;
        return Mth.clamp((Mth.lerp(this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false), this.ticksOpen - 1, this.ticksOpen) - start) / range, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticksOpen++;
    }

    public void setTicksOpen(int ticksOpen) {
        this.ticksOpen = ticksOpen;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(prevScreen);
    }
}
