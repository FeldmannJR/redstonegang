

package dev.feldmann.redstonegang.discord.utils;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private long tempo;
    HashMap<Long, Long> cds = new HashMap();

    public Cooldown(long tempo) {
        this.tempo = tempo;
    }


    public boolean isCooldown(Long uid) {
        if (this.cds.containsKey(uid)) {
            if ((Long) this.cds.get(uid) < System.currentTimeMillis()) {
                this.cds.remove(uid);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean can(Long uid) {
        if (isCooldown(uid)) {
            return false;
        }
        addCooldown(uid);
        return true;
    }

    public boolean addCooldown(Long uid) {
        this.cds.put(uid, System.currentTimeMillis() + this.tempo);
        return true;
    }
}
