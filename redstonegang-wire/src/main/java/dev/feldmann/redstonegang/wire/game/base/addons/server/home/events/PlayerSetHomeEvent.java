package dev.feldmann.redstonegang.wire.game.base.addons.server.home.events;

import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSetHomeEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();


    private HomeAddon manager;
    private String name;
    private BungeeLocation location;
    private boolean update;
    private boolean cancelled = false;

    public PlayerSetHomeEvent(Player who, HomeAddon manager, String name, BungeeLocation location, boolean update) {
        super(who);
        this.name = name;
        this.manager = manager;
        this.location = location;
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public HomeAddon getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public BungeeLocation getLocation() {
        return location;
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
