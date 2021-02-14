package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.args.JobArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.menus.JobPerksMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobBonus extends RedstoneCmd {
    private JobArgument JOBS;
    private JobsAddon addon;

    public JobBonus(JobsAddon addon) {
        super("bonus", "ve os bonus de um job", new JobArgument(addon, false));
        JOBS = (JobArgument) getArgs().get(0);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Job job = args.get(JOBS);
        new JobPerksMenu(job).open((Player) sender);

    }
}
