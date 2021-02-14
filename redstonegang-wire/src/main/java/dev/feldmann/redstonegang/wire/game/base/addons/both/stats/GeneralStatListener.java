package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.events.PlayerPlayedTimeUpdateEvent;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class GeneralStatListener extends BaseListener {

    StatAddon addon;
    HashMap<Integer, Long> last = new HashMap<>();


    public GeneralStatListener(StatAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_4) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                update(p, true, false);
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        update(ev.getPlayer(), false, true);
    }


    public void update(Player p, boolean add, boolean remove) {
        int pId = addon.getPlayerId(p);
        if (last.containsKey(pId)) {
            long dif = System.currentTimeMillis() - last.get(pId);
            long minutos = dif / 1000L / 60L;

            if (add) {
                long resto = dif - (minutos * 1000 * 60);
                last.put(pId, System.currentTimeMillis() - resto);
            }
            if (remove) {
                last.remove(pId);
            }
            if (minutos > 0) {
                Wire.callEvent(new PlayerPlayedTimeUpdateEvent(p, minutos));
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        last.put(addon.getPlayerId(ev.getPlayer()), System.currentTimeMillis());
    }


    @EventHandler
    public void updateTime(PlayerPlayedTimeUpdateEvent ev) {
        Stats.MINUTES_ONLINE.addPoints(addon.getPlayerId(ev.getPlayer()), ev.getMinutes());
    }
}
