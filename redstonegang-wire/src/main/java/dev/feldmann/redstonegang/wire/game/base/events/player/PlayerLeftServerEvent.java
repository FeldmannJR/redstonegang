package dev.feldmann.redstonegang.wire.game.base.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerLeftServerEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();

    public PlayerLeftServerEvent(Player who) {
        super(who);

    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
