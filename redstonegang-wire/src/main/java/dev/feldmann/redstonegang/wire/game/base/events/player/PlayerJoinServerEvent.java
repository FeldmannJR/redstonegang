package dev.feldmann.redstonegang.wire.game.base.events.player;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerJoinServerEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();
    boolean join = false;
    boolean changedIndentifier = false;

    public PlayerJoinServerEvent(Player who, boolean join,boolean changedIndentifier) {
        super(who);
        this.join = join;
        this.changedIndentifier = changedIndentifier;
    }

    public boolean isJoin() {
        return join;
    }

    public boolean changedIndentifier() {
        return changedIndentifier;
    }

    public User getUser() {
        return RedstoneGang.getPlayer(getPlayer().getUniqueId());
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
