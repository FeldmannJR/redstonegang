package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Fodao;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.WhitelistCmdAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs.Add;

import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs.Check;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs.Del;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs.List;
import org.bukkit.command.CommandSender;

public class CmdWhitelistCmd extends RedstoneCmd {

    public CmdWhitelistCmd(WhitelistCmdAddon addon) {
        super("whitelistcmd", "comandos permitidos no servidor");
        setExecutePerm(Fodao.INSTANCE);
        addCmd(new Add(addon));
        addCmd(new Del(addon));
        addCmd(new List(addon));
        addCmd(new Check(addon));
        addCmd(new HelpCmd());

    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
