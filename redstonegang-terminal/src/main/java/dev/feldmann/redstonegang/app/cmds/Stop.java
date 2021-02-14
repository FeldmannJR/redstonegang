package dev.feldmann.redstonegang.app.cmds;

import dev.feldmann.redstonegang.app.RedstoneTerminal;

public class Stop extends Comando {

    public Stop() {
        super("stop", "Fecha a aplicação!");
    }

    @Override
    public void exec(String[] args) {
        out("Fechando");
        RedstoneTerminal.stop();

    }
}
