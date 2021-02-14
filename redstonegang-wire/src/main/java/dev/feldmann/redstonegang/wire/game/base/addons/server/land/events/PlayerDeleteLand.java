package dev.feldmann.redstonegang.wire.game.base.addons.server.land.events;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDeleteLand extends PlayerEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private Land terreno;
    private boolean cancelled = false;
    private boolean admin = false;

    public PlayerDeleteLand(Player who, Land terreno, boolean admin) {
        super(who);
        this.terreno = terreno;
        this.admin = admin;
    }

    public Land getTerreno() {
        return terreno;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public boolean isAdmin() {
        return admin;
    }
}
