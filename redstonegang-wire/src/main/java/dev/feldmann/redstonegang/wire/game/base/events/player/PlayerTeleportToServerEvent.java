package dev.feldmann.redstonegang.wire.game.base.events.player;

import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportToServerEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private String server;
    private BungeeLocation loc = null;

    public PlayerTeleportToServerEvent(Player who, BungeeLocation loc) {
        this(who, loc.getServer());
        this.loc = loc;
    }

    public PlayerTeleportToServerEvent(Player who, String server) {
        super(who);
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public BungeeLocation getLoc() {
        return loc;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
