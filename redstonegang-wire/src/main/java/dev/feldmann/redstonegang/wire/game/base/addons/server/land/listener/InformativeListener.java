package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import dev.feldmann.redstonegang.wire.utils.player.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class InformativeListener extends TerrenoListener {


    public InformativeListener(LandAddon manager) {
        super(manager);
    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (!LocUtils.isSameBlockIgnoreY(ev.getFrom(), ev.getTo())) {
            LandAddon t = manager();
            Land old = t.getTerreno(ev.getFrom());
            Land neww = t.getTerreno(ev.getTo());
            if (old != neww) {
                sendEnterTerrain(ev.getPlayer(), neww);
            }
        }
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent ev) {

        Land to = manager().getTerreno(ev.getTo());
        Land from = manager().getTerreno(ev.getFrom());
        if (to != from)
            sendEnterTerrain(ev.getPlayer(), to);
    }

    public void sendEnterTerrain(Player p, Land t) {
        if (t != null) {
            if (manager().getUser(p).getConfig(manager().SHOW_ENTER_TITLE)) {
                User owner = RedstoneGangSpigot.getPlayer(t.getOwnerId());
                if (owner != null)
                    Title.sendTitle(p, "", owner.getNameWithPrefix(), 5, 20, 5);
            }
        }
    }


}
