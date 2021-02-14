package dev.feldmann.redstonegang.wire.game.games.servers.survival;

import dev.feldmann.redstonegang.common.db.Database;

public abstract class SurvivalDatabase extends Database {

    public SurvivalDatabase(){
        super(Survival.database);
    }


}
