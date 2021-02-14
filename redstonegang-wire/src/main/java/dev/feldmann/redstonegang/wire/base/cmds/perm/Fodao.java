package dev.feldmann.redstonegang.wire.base.cmds.perm;

import dev.feldmann.redstonegang.wire.base.cmds.CmdExecutePerm;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.List;

public class Fodao implements CmdExecutePerm {
    public static final Fodao INSTANCE = new Fodao();

    @Override
    public boolean canExecute(CommandSender cs) {
        if (cs instanceof ConsoleCommandSender) return true;
        List<String> fodas = Arrays.asList("Feldmann", "Mahzinha", "net32", "ForeverPlayerG");
        return fodas.contains(cs.getName());
    }
}
