package dev.feldmann.redstonegang.wire.permissions.user;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import org.bukkit.command.CommandSender;

public class SubUser extends RedstoneCmd {
    public SubUser() {
        super("user", "comandos de usuario");
        addCmd(new CmdSet());
        addCmd(new Info());
        addCmd(new SetGroup());
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
