package dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUseElevatorEvent extends PlayerEvent implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    private Block block;
    private boolean canceled = false;

    public PlayerUseElevatorEvent(Player who, Block elevator) {
        super(who);
        this.block = elevator;
    }

    public Block getBlock() {
        return block;
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
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        canceled = b;
    }
}
