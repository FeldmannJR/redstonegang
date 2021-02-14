package dev.feldmann.redstonegang.wire.modulos.customevents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerBreedingEntityEvent extends PlayerEvent {

    public static HandlerList handlers = new HandlerList();


    private Entity spawned;

    public PlayerBreedingEntityEvent(Player who, Entity spawned) {
        super(who);
        this.spawned = spawned;
    }

    public Entity getSpawned() {
        return spawned;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
