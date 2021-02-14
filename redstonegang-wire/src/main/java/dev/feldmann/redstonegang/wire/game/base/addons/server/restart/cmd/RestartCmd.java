package dev.feldmann.redstonegang.wire.game.base.addons.server.restart.cmd;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.restart.ServerRestartAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class RestartCmd extends RedstoneCmd {
    private static final IntegerArgument TEMPO = new IntegerArgument("segundos", true, 0, 60 * 10);

    private ServerRestartAddon addon;

    public RestartCmd(ServerRestartAddon addon) {
        super("restart", "Reinicia o servidor em dado tempo",TEMPO);
        setPermission(addon.generatePermissionStaff("command"));
        this.addon = addon;
        this.addCmd(new CancelarCmd(addon));
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        boolean success;
        if (args.isPresent(TEMPO)) {
            success = addon.restart(args.get(TEMPO));
        } else {
            success = addon.restart();
        }
        if (success) {
            C.info(sender, "Servidor irá reiniciar em %% segundos!", addon.getCurrentTimer());
        } else {
            C.error(sender, "O servidor já está reiniciando primeiro cancele com %cmd%!", getFullCommand() + " cancelar");
        }
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}
