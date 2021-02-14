package dev.feldmann.redstonegang.network;

import dev.feldmann.redstonegang.app.RedstoneTerminal;
import dev.feldmann.redstonegang.app.console.Console;
import dev.feldmann.redstonegang.common.RedstoneGangTerminal;

import java.util.logging.Logger;

public class RedstoneGangNetwork extends RedstoneGangTerminal {

    @Override
    public boolean useDatabase() {
        return false;
    }

    @Override
    public boolean useNetwork() {
        return false;
    }

    @Override
    public boolean useUsers() {
        return false;
    }

    @Override
    public void shutdown(String cause) {
        RedstoneTerminal.stop();
    }

    @Override
    public Logger getLogger() {
        return Console.log;
    }
}
