package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.WhitelistCmdAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Check extends RedstoneCmd {
    private static StringArgument COMANDO = new StringArgument("comando", false);
    private WhitelistCmdAddon addon;

    public Check(WhitelistCmdAddon addon) {
        super("check", "verifica se um comando está na whitelist ou não (sem /)", COMANDO);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String cmd = args.get(COMANDO).toLowerCase();
        if (addon.isWhitelisted(cmd)) {
            C.info(sender, "O comando %% está na whitelist!", cmd);
        } else {
            C.info(sender, "O comando %% não está na whitelist!", cmd);
        }
    }
}
