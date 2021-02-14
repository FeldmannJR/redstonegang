package dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerStopFreePvpEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();


    public PlayerStopFreePvpEvent(Player who) {
        super(who);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
