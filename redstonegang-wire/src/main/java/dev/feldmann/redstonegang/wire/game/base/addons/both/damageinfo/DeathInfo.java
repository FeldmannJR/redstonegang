package dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo;

import java.util.List;

public class DeathInfo {
    List<HitInfo> hits;
    long quando = System.currentTimeMillis();

    public DeathInfo(List<HitInfo> hits) {
        this.hits = hits;
    }

    public List<HitInfo> getHits() {
        return hits;
    }

    public HitInfo getLastPlayerDamage() {
        HitInfo last = null;
        for (HitInfo di : hits) {
            if (last == null || last.getTimestamp() < di.getTimestamp()) {
                if (di.getUserDamager() != null) {
                    last = di;
                }
            }
        }
        if (last != null) {
            if (last.getTimestamp() < (System.currentTimeMillis() - (DamageInfoAddon.DAMAGE_DECAY))) {
                last = null;
            }
        }
        return last;
    }

    public HitInfo getLastHit() {
        HitInfo last = null;
        for (HitInfo di : hits) {
            if (last == null || last.getTimestamp() < di.getTimestamp()) {
                last = di;
            }
        }
        return last;
    }

}
