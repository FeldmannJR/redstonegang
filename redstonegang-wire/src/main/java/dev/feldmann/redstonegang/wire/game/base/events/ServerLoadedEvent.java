package dev.feldmann.redstonegang.wire.game.base.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerLoadedEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
