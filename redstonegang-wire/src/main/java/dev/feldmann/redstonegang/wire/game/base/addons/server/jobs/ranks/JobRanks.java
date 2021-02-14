package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.ranks;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;

import java.util.concurrent.ConcurrentHashMap;

public class JobRanks {

    private JobsAddon addon;

    private ConcurrentHashMap<Job, JobRankInfo> infos = new ConcurrentHashMap<>();


    public JobRanks(JobsAddon addon) {
        this.addon = addon;
    }


    public void updateAll() {
        for (Job job : addon.getJobs()) {
            addon.runAsync(() -> addon.getDb().getTop(job, 54), l -> {
                getInfo(job).top = l;
            });
        }

    }


    public JobRankInfo getInfo(Job job) {
        if (!infos.containsKey(job)) {
            infos.put(job, new JobRankInfo());
        }
        return infos.get(job);
    }


}
