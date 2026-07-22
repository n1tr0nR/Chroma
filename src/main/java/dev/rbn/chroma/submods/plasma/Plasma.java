package dev.rbn.chroma.submods.plasma;

import dev.rbn.chroma.logging.LogEntry;
import dev.rbn.chroma.logging.Logger;
import dev.rbn.chroma.submods.Submod;

public class Plasma implements Submod {
    public static Logger LOG = new Logger("Plasma");

    @Override
    public void onInitialize() {
        new LogEntry(LOG).info("| > Initializing §greenPlasma§end.").send();
    }
}