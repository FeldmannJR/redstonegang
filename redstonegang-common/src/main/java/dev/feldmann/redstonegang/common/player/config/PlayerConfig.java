package dev.feldmann.redstonegang.common.player.config;

import java.util.HashMap;

public class PlayerConfig {

    private ConfigManager manager;

    int pid;
    private HashMap<ConfigType, Object> values = new HashMap<>();

    public PlayerConfig(ConfigManager manager, int pid) {
        this.manager = manager;
        this.pid = pid;
    }

    public <T> T get(ConfigType<T, ?> type) {
        if (!values.containsKey(type)) {
            return type.getDefault();
        }
        return (T) values.get(type);
    }

    public <T> void set(ConfigType<T, ?> key, T value) {
        values.put(key, value);
        manager.db.set(this, key, value);
    }

    <T> void load(ConfigType<T, ?> key, T value) {

        values.put(key, value);
    }


}
