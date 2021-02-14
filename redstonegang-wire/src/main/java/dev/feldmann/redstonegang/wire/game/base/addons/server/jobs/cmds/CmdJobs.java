package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.subs.JobBonus;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.subs.JobTop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.subs.JobVer;
import org.bukkit.command.CommandSender;

public class CmdJobs extends RedstoneCmd {
    public CmdJobs(JobsAddon addon) {
        super("jobs", "comando dos jobs");
        addCmd(new JobTop(addon));
        addCmd(new JobVer(addon));
        addCmd(new JobBonus(addon));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
