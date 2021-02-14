package dev.feldmann.redstonegang.wire.base.cmds.perm;

import dev.feldmann.redstonegang.wire.base.cmds.CmdExecutePerm;
import org.bukkit.command.CommandSender;

public class Op implements CmdExecutePerm {

    public static final Op INSTANCE = new Op();

    @Override
    public boolean canExecute(CommandSender cs) {
        return cs.isOp();
    }
}
