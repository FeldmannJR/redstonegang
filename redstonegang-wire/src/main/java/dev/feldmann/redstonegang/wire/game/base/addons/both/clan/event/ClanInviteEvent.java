package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanInviteEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private boolean cancelled;
    private Player inviter;
    private Player invited;
    private Clan clan;
    private ClanAddon addon;

    public ClanInviteEvent(Player inviter, Player invited, Clan clan, ClanAddon addon) {
        this.inviter = inviter;
        this.invited = invited;
        this.clan = clan;
        this.addon = addon;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Player getInvited() {
        return invited;
    }

    public Player getInviter() {
        return inviter;
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
