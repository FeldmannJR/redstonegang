package dev.feldmann.redstonegang.wire.permissions;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Fodao;
import dev.feldmann.redstonegang.wire.permissions.user.SubUser;
import dev.feldmann.redstonegang.wire.permissions.group.SubGroup;
import org.bukkit.command.CommandSender;

public class CmdPerm extends RedstoneCmd {
    public CmdPerm() {
        super("perm");
        setExecutePerm(Fodao.INSTANCE);
        //Sub cmds
        addCmd(new SubUser());
        addCmd(new SubGroup());
        addCmd(new HelpCmd());

    }

    //Executa se não achar nenhum subcmd
    @Override
    public void command(CommandSender sender, Arguments args) {
    }
}
