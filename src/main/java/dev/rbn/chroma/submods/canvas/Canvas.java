package dev.rbn.chroma.submods.canvas;

import dev.rbn.chroma.logging.LogEntry;
import dev.rbn.chroma.logging.Logger;
import dev.rbn.chroma.submods.Submod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Canvas implements Submod {
    public static Logger LOG = new Logger("Canvas");

    @Override
    public void onInitialize() {
        new LogEntry(LOG).info("| > Initializing §greenCanvas§end.").send();
        ClientTickEvents.END_CLIENT_TICK.register(client -> CanvasCore.getInstance().tick());
    }
}
