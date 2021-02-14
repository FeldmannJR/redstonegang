package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events;

import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSyncEvent extends PlayerEvent {

    public static HandlerList handlers = new HandlerList();


    public PlayerSyncEvent(Player who, BungeeLocation lastLocation, BungeeLocation teleportLocation) {
        super(who);
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
