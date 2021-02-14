package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerListener extends BaseListener {

    private Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        User user = RedstoneGangSpigot.getPlayer(ev.getPlayer());
        String old = user.getServerIdentifier();
        String identifier = server.getIdentifier();
        boolean update = false;
        if ((old != null && identifier == null) || (old == null && identifier != null) || (old == null && identifier == null) || (!old.equals(identifier))) {
            RedstoneGang.instance().user().updateIdentifier(user, identifier);
            update = true;
        }
        PlayerJoinServerEvent custom = new PlayerJoinServerEvent(ev.getPlayer(), true, update);
        Bukkit.getPluginManager().callEvent(custom);

    }
}
