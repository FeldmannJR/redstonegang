package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClanJoinEvent extends PlayerEvent implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private boolean cancelled;
    private Clan clan;
    private ClanAddon addon;

    public ClanJoinEvent(Player player, Clan clan, ClanAddon addon) {
        super(player);
        this.clan = clan;
        this.addon = addon;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }


    public Clan getClan() {
        return clan;
    }

    public ClanAddon getAddon() {
        return addon;
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
