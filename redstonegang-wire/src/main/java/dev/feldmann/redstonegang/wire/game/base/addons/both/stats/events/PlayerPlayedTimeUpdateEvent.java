package dev.feldmann.redstonegang.wire.game.base.addons.both.stats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPlayedTimeUpdateEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();


    private long minutes;

    public PlayerPlayedTimeUpdateEvent(Player who, long minutes) {
        super(who);
        this.minutes = minutes;
    }

    public long getMinutes() {
        return minutes;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
