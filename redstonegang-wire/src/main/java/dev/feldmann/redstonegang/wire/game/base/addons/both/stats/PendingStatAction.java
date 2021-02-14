package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import java.util.HashMap;

class PendingStatAction {

    public int pid;
    protected HashMap<PlayerStat, Long> map = new HashMap<>();

    public PendingStatAction(int pid) {
        this.pid = pid;
    }

    public void addPoints(PlayerStat st, long points) {
        if (map.containsKey(st)) {
            points += map.get(st);
        }
        map.put(st, points);
    }

    public void clear() {
        map.clear();
    }
}
