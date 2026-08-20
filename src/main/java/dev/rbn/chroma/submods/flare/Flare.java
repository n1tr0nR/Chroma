package dev.rbn.chroma.submods.flare;

import dev.rbn.chroma.logging.LogEntry;
import dev.rbn.chroma.logging.Logger;
import dev.rbn.chroma.submods.Submod;
import dev.rbn.chroma.submods.flare.screenshake.Screenshake;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Flare implements Submod {
    public static Logger LOG = new Logger("Flare");

    @Override
    public void onInitialize() {
        new LogEntry(LOG).info("| > Initializing §greenFlare§end.").send();

        Screenshake screenshake = new Screenshake();
        screenshake.initialize();
        ClientTickEvents.END_CLIENT_TICK.register(screenshake::tick);
    }
}
