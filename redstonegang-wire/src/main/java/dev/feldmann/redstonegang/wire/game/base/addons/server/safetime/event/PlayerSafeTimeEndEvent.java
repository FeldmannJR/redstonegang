package dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSafeTimeEndEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    public PlayerSafeTimeEndEvent(Player who) {
        super(who);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
