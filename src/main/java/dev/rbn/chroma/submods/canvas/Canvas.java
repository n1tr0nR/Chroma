package dev.rbn.chroma.submods.canvas;

import dev.rbn.chroma.logging.LogEntry;
import dev.rbn.chroma.logging.Logger;
import dev.rbn.chroma.submods.Submod;

public class Canvas implements Submod {
    public static Logger LOG = new Logger("Canvas");

    @Override
    public void onInitialize() {
        new LogEntry(LOG).info("| > Initializing §greenCanvas§end.").send();
    }
}
