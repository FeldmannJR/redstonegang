package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanCreateEvent extends PlayerEvent implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;
    private String tag;
    private String name;
    private ClanAddon addon;


    public ClanCreateEvent(Player who, String tag, String name, ClanAddon addon) {
        super(who);
        this.tag = tag;
        this.name = name;
        this.addon = addon;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public ClanAddon getAddon() {
        return addon;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
