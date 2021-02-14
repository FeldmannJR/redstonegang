package dev.feldmann.redstonegang.wire.base.cmds.perm;

import dev.feldmann.redstonegang.wire.base.cmds.CmdExecutePerm;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Console implements CmdExecutePerm {
    public static final Console INSTANCE = new Console();

    @Override
    public boolean canExecute(CommandSender cs) {
        return cs instanceof ConsoleCommandSender;
    }
}
