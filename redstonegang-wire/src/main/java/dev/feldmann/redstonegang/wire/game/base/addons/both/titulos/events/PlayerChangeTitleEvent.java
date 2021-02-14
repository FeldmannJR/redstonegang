package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events;

import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChangeTitleEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private TituloCor oldvalue;
    private TituloCor newvalue;

    private boolean cancelled = false;

    public PlayerChangeTitleEvent(Player who, TituloCor oldvalue, TituloCor newvalue) {
        super(who);
        this.oldvalue = oldvalue;
        this.newvalue = newvalue;
    }

    public TituloCor getOld() {
        return oldvalue;
    }

    public TituloCor getNew() {
        return newvalue;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
