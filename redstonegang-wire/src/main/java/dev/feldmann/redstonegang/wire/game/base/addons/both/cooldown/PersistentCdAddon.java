package dev.feldmann.redstonegang.wire.game.base.addons.both.cooldown;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PersistentCdAddon extends Addon {

    CooldownDB db;
    HashMap<Integer, HashMap<String, Long>> cache = new HashMap<>();

    @Override
    public void onEnable() {
        db = new CooldownDB();
        db.clear();
    }

    public boolean isCooldown(Player p, String cdname) {
        int pid = getPlayerId(p);
        if (!cache.containsKey(pid)) {
            cache.put(pid, new HashMap<>());
        }
        if (!cache.get(pid).containsKey(cdname)) {
            long cd = db.getCooldown(getPlayerId(p), cdname);
            if (cd == -1) {
                return false;
            }
            cache.get(pid).put(cdname, cd);
        }

        if (cache.get(pid).get(cdname) == 0) {
            C.error(p, "Você não pode usar isto novamente!");
            return true;
        }
        if (cache.get(pid).get(cdname) < System.currentTimeMillis()) {
            cache.get(pid).remove(cdname);
            return false;
        } else {
            C.error(p, "Espere %% para usar novamente!", TimeUtils.millisToString(cache.get(pid).get(cdname) - System.currentTimeMillis()));
            return true;
        }
    }


    public void addCooldown(Player p, String cdname, long millis) {
        db.setCooldown(getPlayerId(p), cdname, millis > 0 ? System.currentTimeMillis() + millis : 0);
        HashMap<String, Long> pcds;
        int pid = getPlayerId(p);
        if (cache.containsKey(pid)) {
            pcds = cache.get(pid);
        } else {
            pcds = new HashMap<>();
            cache.put(pid, pcds);
        }
        pcds.put(cdname, millis > 0 ? System.currentTimeMillis() + millis : 0);
    }

    public void addCooldownMinutos(Player p, String cdname, int minutos) {
        addCooldown(p, cdname, (long) minutos * 1000 * 60);
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        cache.remove(getPlayerId(ev.getPlayer()));
    }
}
