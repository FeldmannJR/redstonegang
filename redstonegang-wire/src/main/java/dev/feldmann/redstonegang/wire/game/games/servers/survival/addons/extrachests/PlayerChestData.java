package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import java.util.HashMap;

public class PlayerChestData {

    private HashMap<ChestList, ChestData> data;

    public PlayerChestData(HashMap<ChestList, ChestData> data) {
        this.data = data;
    }

    public ChestData getData(ChestList c) {
        return data.get(c);
    }
}
