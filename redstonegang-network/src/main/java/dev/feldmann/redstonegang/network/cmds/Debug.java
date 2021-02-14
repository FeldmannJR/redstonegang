package dev.feldmann.redstonegang.network.cmds;

import dev.feldmann.redstonegang.app.cmds.Comando;
import dev.feldmann.redstonegang.network.RedstoneNetwork;

public class Debug extends Comando {
    public Debug() {
        super("debug", "ve as msgs recebendo!");
    }

    @Override
    public void exec(String[] args) {
        RedstoneNetwork.debug = !RedstoneNetwork.debug;
        out("Debug: " + RedstoneNetwork.debug);
    }
}
