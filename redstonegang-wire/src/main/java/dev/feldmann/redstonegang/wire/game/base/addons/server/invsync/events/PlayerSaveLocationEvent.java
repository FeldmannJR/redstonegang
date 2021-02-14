package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events;

import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSaveLocationEvent extends PlayerEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private BungeeLocation saveLocation;
    private BungeeLocation originalSaveLocation;
    private boolean teleportEvent;

    public PlayerSaveLocationEvent(Player who, BungeeLocation saveLocation, boolean teleportEvent) {
        super(who);
        this.saveLocation = saveLocation;
        this.teleportEvent = teleportEvent;
        this.originalSaveLocation = saveLocation;
    }

    public BungeeLocation getSaveLocation() {
        return saveLocation;
    }

    public void setSaveLocation(BungeeLocation saveLocation) {
        this.saveLocation = saveLocation;
    }

    public boolean isTeleportEvent() {
        return teleportEvent;
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
        return saveLocation == null;
    }

    @Override
    public void setCancelled(boolean b) {
        this.saveLocation = b ? null : originalSaveLocation;
    }
}
