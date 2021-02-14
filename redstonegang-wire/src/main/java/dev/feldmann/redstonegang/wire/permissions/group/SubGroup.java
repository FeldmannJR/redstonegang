package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import org.bukkit.command.CommandSender;

public class SubGroup extends RedstoneCmd {
    public SubGroup() {
        super("group", "comandos de group");
        addCmd(new Create());
        addCmd(new Info());
        addCmd(new Set());
        addCmd(new SetDisplayName());
        addCmd(new SetOption());
        addCmd(new SetParent());
        addCmd(new List());
        addCmd(new SetPrefix());
        addCmd(new SetSuffix());
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
    }
}
