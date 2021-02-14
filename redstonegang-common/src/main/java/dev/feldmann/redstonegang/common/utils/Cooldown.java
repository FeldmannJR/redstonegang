package dev.feldmann.redstonegang.common.utils;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private long tempo;
    private String oq = null;

    HashMap<UUID, Long> cds = new HashMap();

    public Cooldown(long tempo, String oq) {
        this.tempo = tempo;
        this.oq = oq;
    }

    public Cooldown(long tempo) {
        this.tempo = tempo;
    }


    public boolean isCooldownWithMessage(UUID uid) {
        boolean is = isCooldown(uid);
        if (!is) {
            return false;
        }
        long falta = cds.get(uid) - System.currentTimeMillis();
        String tempo;
        if (falta >= 1000)
            tempo = TimeUtils.millisToString(cds.get(uid) - System.currentTimeMillis());
        else
            tempo = "menos de um segundo";
        if (oq != null) {
            RedstoneGang.getPlayer(uid).sendMessage("§©Espere " + tempo + " para " + oq + " novamente!");
        } else {
            RedstoneGang.getPlayer(uid).sendMessage("§cEspere " + tempo + " para usar isto novamente  novamente!");

        }
        return true;
    }

    public long remaining(UUID uid) {
        if (isCooldown(uid)) {
            return cds.get(uid) - System.currentTimeMillis();
        }
        return -1;
    }

    public void removeCooldown(UUID uid) {
        cds.remove(uid);
    }

    public boolean isCooldown(UUID uid) {
        if (cds.containsKey(uid)) {
            if (cds.get(uid) < System.currentTimeMillis()) {
                cds.remove(uid);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean addCooldown(UUID uid, long customTempo) {
        cds.put(uid, System.currentTimeMillis() + customTempo);
        return true;
    }

    public boolean addCooldown(UUID uid) {
        cds.put(uid, System.currentTimeMillis() + tempo);
        return true;
    }


}
