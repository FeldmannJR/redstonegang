package dev.feldmann.redstonegang.wire.game.base.addons.server.tosar;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerShearAnimalEvent extends PlayerEvent implements Cancellable {


    List<ItemStack> drops;

    private static final HandlerList handlers = new HandlerList();

    private Entity ent;
    private boolean cancelled = false;

    public PlayerShearAnimalEvent(Player who, Entity ent, List<ItemStack> drosp) {
        super(who);
        this.ent = ent;
        this.drops = drosp;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public Entity getTosado() {
        return ent;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }


}
