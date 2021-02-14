package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.WhitelistCmdAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class List extends RedstoneCmd {
    private WhitelistCmdAddon addon;

    public List(WhitelistCmdAddon addon) {
        super("list", "lista os comandos que estão na whitelist");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        C.info(sender, "Permitidos: " + C.joinList(addon.getWhitelisted()));
    }
}
