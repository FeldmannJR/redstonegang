package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;

import java.util.HashMap;

public class JobPlayer {

    int playerId;
    HashMap<Job, Long> xps = new HashMap<>();

    public JobPlayer(int playerId) {
        this.playerId = playerId;
    }

    public long getXp(Job job) {
        if (xps.containsKey(job)) {
            return xps.get(job);
        }
        return 0L;
    }

    public int getLevel(Job job) {
        return job.getLevel(getXp(job));
    }

    public void addXp(Job job, int quanto) {
        xps.put(job, getXp(job) + quanto);
    }

    public User getPlayer() {
        return RedstoneGangSpigot.getPlayer(playerId);
    }

    public long getRemaining(Job job) {
        int lvl = getLevel(job);
        return job.getRequiredXp(lvl + 1) - getXp(job);
    }


}
