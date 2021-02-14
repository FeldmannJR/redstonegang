package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events;

import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSyncLocationEvent extends PlayerEvent {

    public static HandlerList handlers = new HandlerList();

    private BungeeLocation savedLocation;
    private BungeeLocation teleportLocation;
    private Location spawnLocation = null;
    private boolean overrideTeleportLocation = false;

    public PlayerSyncLocationEvent(Player who, BungeeLocation lastLocation, BungeeLocation teleportLocation) {
        super(who);
        this.savedLocation = lastLocation;
        this.teleportLocation = teleportLocation;
    }

    public BungeeLocation getSavedLocation() {
        return savedLocation;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public BungeeLocation getTeleportLocation() {
        return teleportLocation;
    }

    public boolean isOverrideTeleportLocation() {
        return overrideTeleportLocation;
    }

    public void setOverrideTeleportLocation(boolean overrideTeleportLocation) {
        this.overrideTeleportLocation = overrideTeleportLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
