package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.WhitelistCmdAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Add extends RedstoneCmd {
    private static StringArgument COMANDO = new StringArgument("comando", false);
    private WhitelistCmdAddon addon;

    public Add(WhitelistCmdAddon addon) {
        super("add", "adiciona um comando a whitelist (sem /)", COMANDO);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String cmd = args.get(COMANDO).toLowerCase();
        if (addon.isWhitelisted(cmd)) {
            C.error(sender, "O comando %% já está na whitelist!", cmd);
            return;
        }
        addon.addCommand(cmd);
        C.info(sender, "Comando %% adicionado a whitelist!", cmd);
    }
}
