package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanChangeTagEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private boolean cancelled = false;
    private ClanAddon addon;
    private Clan clan;
    private String newTag;


    public ClanChangeTagEvent(ClanAddon addon, Clan clan, String newTag) {
        this.addon = addon;
        this.clan = clan;
        this.newTag = newTag;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public ClanAddon getAddon() {
        return addon;
    }

    public String getNewTag() {
        return newTag;
    }

    public Clan getClan() {
        return clan;
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
