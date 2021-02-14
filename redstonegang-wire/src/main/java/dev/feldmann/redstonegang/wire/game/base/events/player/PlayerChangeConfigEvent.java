package dev.feldmann.redstonegang.wire.game.base.events.player;

import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.player.config.SimpleConfigType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChangeConfigEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private ConfigType cfg;
    private Object old;
    private Object newvalue;

    private boolean cancelled = false;

    public PlayerChangeConfigEvent(Player who, ConfigType cfg, Object old, Object newvalue) {
        super(who);
        this.cfg = cfg;
        this.old = old;
        this.newvalue = newvalue;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public ConfigType getConfig() {
        return cfg;
    }

    public Object getOld() {
        return old;
    }

    public Object getNewvalue() {
        return newvalue;
    }
}
