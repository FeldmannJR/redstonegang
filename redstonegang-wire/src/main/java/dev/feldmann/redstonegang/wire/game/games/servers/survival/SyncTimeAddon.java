package dev.feldmann.redstonegang.wire.game.games.servers.survival;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

public class SyncTimeAddon extends Addon {

    private boolean host;

    public SyncTimeAddon(boolean host) {
        this.host = host;
    }


    @EventHandler
    public void network(NetworkMessageEvent ev) {
        if (host) {
            return;
        }
        if (ev.is("synctime:" + getServer().getIdentifier())) {
            String time = ev.get(0);
            try {
                long curtime = Long.parseLong(time);
                runSync(() -> {
                    for (World w : Bukkit.getWorlds()) {
                        long diff = Math.abs(w.getTime() - curtime);
                        if (diff < 500) {
                            continue;
                        }
                        w.setTime(curtime);
                    }
                });
            } catch (NumberFormatException ex) {

            }
        }
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (host) {
            if (ev.getType() == UpdateType.SEC_16) {
                network().sendMessage("synctime:" + getServer().getIdentifier(), Bukkit.getWorlds().get(0).getTime());
            }
        }
    }

}
