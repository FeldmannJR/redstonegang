package dev.feldmann.redstonegang.wire.permissions.user;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.permissions.PermissionBaseRG;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CmdSet extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("user", false);
    private static final StringArgument PERMISSION = new StringArgument("permission", false);
    private static final PermissionServerArgument SERVER = new PermissionServerArgument("server", true);
    private static final OneOfThoseArgument VALUE = new OneOfThoseArgument("value", false, "allow", "deny", "none");


    public CmdSet() {
        super("set", "seta uma permissão para um jogador", PLAYER, PERMISSION, VALUE, SERVER);
    }

    @Override
    public void command(CommandSender sender, Arguments arg) {
        User pl = arg.getValue(PLAYER);
        String perm = arg.getValue(PERMISSION);
        PermissionServer server = PermissionServer.GERAL;
        if (arg.isPresent(SERVER)) {
            server = arg.get(SERVER);
        }
        pl.permissions().addPermission(server, perm, PermissionValue.valueOf(arg.getValue(VALUE).toUpperCase()));
        C.info(sender, "Permissão %% setada para %%", perm, arg.getValue(VALUE));
        if (Bukkit.getPlayer(pl.getUuid()) != null) {
            Player p = Bukkit.getPlayer(pl.getUuid());
            PermissionBaseRG.updateOp(p);
        }
    }


}
