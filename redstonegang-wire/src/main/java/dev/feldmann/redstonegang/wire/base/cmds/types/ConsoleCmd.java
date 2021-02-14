package dev.feldmann.redstonegang.wire.base.cmds.types;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCmd extends RedstoneCmd {
    public ConsoleCmd(String name, String desc, Argument... args) {
        super(name, desc, args);
    }

    public ConsoleCmd(String name) {
        super(name);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        command((ConsoleCommandSender) sender, args);
    }

    public abstract void command(ConsoleCommandSender player, Arguments args);

    @Override
    public boolean canConsoleExecute() {
        return true;
    }

    @Override
    public boolean canPlayerExecute() {
        return false;
    }
}
