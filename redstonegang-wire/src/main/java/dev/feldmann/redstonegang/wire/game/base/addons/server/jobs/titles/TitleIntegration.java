package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.titles;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerGetTitlesEvent;
import org.bukkit.event.EventHandler;

public class TitleIntegration extends BaseListener {

    JobsAddon addon;

    public TitleIntegration(JobsAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void getTitles(PlayerGetTitlesEvent ev) {
        int x = 0;
        for (Job job : addon.getJobs()) {
            if (addon.getJobPlayer(ev.getOwner()).getLevel(job) >= JobsAddon.lowestColor) {
                ev.addTitle(new JobTitle(job, ev.getOwner(), x));
            }
            x++;
        }
    }

}
