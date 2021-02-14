package dev.feldmann.redstonegang.repeater;

import dev.feldmann.redstonegang.common.db.Database;

public abstract class BungeeDatabase extends Database {
    public BungeeDatabase() {
        super("redstonegang_bungeecord");
    }
}
