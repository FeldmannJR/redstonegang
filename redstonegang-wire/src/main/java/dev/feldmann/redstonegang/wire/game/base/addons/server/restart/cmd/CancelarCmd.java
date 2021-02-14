package dev.feldmann.redstonegang.wire.game.base.addons.server.restart.cmd;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.restart.ServerRestartAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class CancelarCmd extends RedstoneCmd {

    private ServerRestartAddon addon;

    public CancelarCmd(ServerRestartAddon addon) {
        super("cancelar", "Cancela o reinicio do servidor");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        if (addon.isRestarting()) {
            addon.cancelRestart();
            C.info(sender, "Reinicio cancelado!");
        } else {
            C.error(sender, "O servidor não está sendo reiniciado!");
        }
    }
}
