package dev.feldmann.redstonegang.common.player.config;

import java.util.HashMap;

public class ConfigManager {

    HashMap<String, ConfigType> configs = new HashMap();
    HashMap<Integer, PlayerConfig> players = new HashMap<>();

    ConfigDB db;


    public ConfigManager() {
        this.db = new ConfigDB(this);
    }

    public void registerConfig(ConfigType type) {
        configs.put(type.getName(), type);
    }

    public void removeConfig(ConfigType type) {
        configs.remove(type.getName());
    }

    public void reload() {
        db.alterTables();
        players.clear();
    }

    public void reloadPlayer(int playerId) {
        players.put(playerId, db.load(playerId));
    }

    public PlayerConfig get(int playerId) {
        if (!players.containsKey(playerId)) {
            players.put(playerId, db.load(playerId));
        }
        return players.get(playerId);
    }


}
