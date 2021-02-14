package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import org.bukkit.command.CommandSender;

public class DefaultArguments extends Argument<String[]> {
    public DefaultArguments(String name) {
        super(name, true);
    }

    @Override
    public String getErrorMessage(String input) {
        return "";
    }

    @Override
    public String[] process(CommandSender cs,int index, String[] args) {
        return args;
    }

    @Override
    public boolean onlyLast() {
        return true;
    }
}
