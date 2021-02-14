package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.event.EventHandler;

public class Permissions extends Module {

    @EventHandler
    public void check(PermissionCheckEvent ev) {
        ev.setHasPermission(false);
        if (ev.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) ev.getSender();
            if (player.getUniqueId() != null) {
                User pl = RedstoneGang.getPlayer(player.getUniqueId());
                ev.setHasPermission(pl.permissions().hasPermission(ev.getPermission()));

            }
        }

    }
}
