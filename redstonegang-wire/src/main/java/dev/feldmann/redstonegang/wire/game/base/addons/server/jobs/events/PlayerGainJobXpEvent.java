package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.events;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerGainJobXpEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();

    private Job job;
    private long oldXp;
    private int addedXp;
    boolean newLevel;

    public PlayerGainJobXpEvent(Player who, Job job, long oldXp, int addedXp,boolean newLevel) {
        super(who);
        this.job = job;
        this.oldXp = oldXp;
        this.addedXp = addedXp;
        this.newLevel = newLevel;
    }

    public int getAddedXp() {
        return addedXp;
    }

    public boolean isNewLevel() {
        return newLevel;
    }

    public long getOldXp() {
        return oldXp;
    }

    public long getNewXp() {
        return oldXp + addedXp;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
