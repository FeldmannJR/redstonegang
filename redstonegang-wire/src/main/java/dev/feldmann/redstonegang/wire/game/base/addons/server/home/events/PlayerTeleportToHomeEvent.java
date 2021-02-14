package dev.feldmann.redstonegang.wire.game.base.addons.server.home.events;

import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportToHomeEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private HomeEntry home = null;

    public PlayerTeleportToHomeEvent(Player who, HomeEntry home) {
        super(who);
        this.home = home;
    }


    public HomeEntry getHome() {
        return home;
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
