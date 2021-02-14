package dev.feldmann.redstonegang.wire.permissions.events;

import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeGroupEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    private User player;
    private Group oldGroup;
    private Group newGroup;

    public PlayerChangeGroupEvent(User who, Group oldGroup, Group newGroup) {
        this.player = who;

        this.oldGroup = oldGroup;
        this.newGroup = newGroup;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Group getNewGroup() {
        return newGroup;
    }

    public Group getOldGroup() {
        return oldGroup;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public User getPlayer() {
        return player;
    }
}
