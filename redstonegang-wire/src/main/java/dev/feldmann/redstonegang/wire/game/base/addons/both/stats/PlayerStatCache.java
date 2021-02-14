package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import java.util.HashMap;

public class PlayerStatCache {

    private int playerId;
    private HashMap<String, Long> values = new HashMap<String, Long>();

    public PlayerStatCache(int playerId) {
        this.playerId = playerId;
    }

    public void setValue(PlayerStat stat, long value) {
        values.put(stat.getUniqueId(), value);
    }

    public void addValue(PlayerStat stat, long value) {
        if (values.containsKey(stat.getUniqueId())) {
            value += values.get(stat.getUniqueId());
        }
        values.put(stat.getUniqueId(), value);
    }

    public long getValue(PlayerStat st) {
        if (values.containsKey(st.getUniqueId())) {
            return values.get(st.getUniqueId());
        }
        return 0;
    }

}
