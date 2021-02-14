package dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerAfterStartFreePvpEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();


    public PlayerAfterStartFreePvpEvent(Player who) {
        super(who);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
