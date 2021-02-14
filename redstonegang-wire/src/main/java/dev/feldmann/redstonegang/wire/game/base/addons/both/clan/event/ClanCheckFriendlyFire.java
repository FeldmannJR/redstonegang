package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class ClanCheckFriendlyFire extends Event {

    private boolean canceled = false;
    private Player damager;
    private Player damaged;

    public ClanCheckFriendlyFire(Player damager, Player damaged) {
        this.damager = damager;
        this.damaged = damaged;
    }

    public Player getDamaged() {
        return damaged;
    }


    public Player getDamager() {
        return damager;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public boolean canAttack() {
        return canceled;
    }

    public void setCanAttack(boolean bln) {
        this.canceled = bln;
    }

}
