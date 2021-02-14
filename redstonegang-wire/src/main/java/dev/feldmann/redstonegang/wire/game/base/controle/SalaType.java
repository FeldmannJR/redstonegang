package dev.feldmann.redstonegang.wire.game.base.controle;

import java.util.UUID;

public enum SalaType {

    OFICIAL(true),
    YOUTUBER(true),
    JOGADOR(false);

    boolean daXp;
    public UUID owner = null;

    SalaType(boolean daXp) {
        this.daXp = daXp;
    }
}
