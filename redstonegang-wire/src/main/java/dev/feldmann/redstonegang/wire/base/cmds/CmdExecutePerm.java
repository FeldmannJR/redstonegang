package dev.feldmann.redstonegang.wire.base.cmds;

import org.bukkit.command.CommandSender;

public interface CmdExecutePerm {

    boolean canExecute(CommandSender cs);
}
