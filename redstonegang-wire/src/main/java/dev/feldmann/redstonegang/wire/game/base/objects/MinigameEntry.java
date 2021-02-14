package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.wire.game.base.Server;

public abstract class MinigameEntry extends ServerEntry {
    public MinigameEntry(Class<? extends Server> classe) {
        super(classe);
    }
}
