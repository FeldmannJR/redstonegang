package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.args;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class JobArgument extends Argument<Job> {
    private JobsAddon addon;

    public JobArgument(JobsAddon addon, boolean optional) {
        super("job", optional);
        this.addon = addon;
    }

    @Override
    public String getErrorMessage(String input) {
        return input + " não é um job! Use /jobs ver";
    }

    @Override
    public Job process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        String n = args[index];
        for (Job job : addon.getJobs()) {
            if (job.getNome().equalsIgnoreCase(n)) {
                return job;
            }
        }
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender sender, String start) {
        return addon.getJobs().stream().map((job) -> job.getNome()).collect(Collectors.toList());
    }
}
