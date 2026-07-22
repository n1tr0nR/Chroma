package dev.rbn.chroma.submods.puppet;

import dev.rbn.chroma.logging.LogEntry;
import dev.rbn.chroma.logging.Logger;
import dev.rbn.chroma.submods.Submod;

public class Puppet implements Submod {
    public static Logger LOG = new Logger("Puppet");

    @Override
    public void onInitialize() {
        new LogEntry(LOG).info("| > Initializing §greenPuppet§end.").send();
    }
}
