package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OneOfThoseArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PermissionServerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.permissions.PermissionBaseRG;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Set extends RedstoneCmd {

    private static final PermissionServerArgument SERVER = new PermissionServerArgument("server", true);
    private static final StringArgument GROUP = new StringArgument("group");
    private static final StringArgument PERMISSION = new StringArgument("permission");
    private static final OneOfThoseArgument VALUE = new OneOfThoseArgument("value", false, "allow", "deny", "none");


    public Set() {
        super("set", "Set uma permissão para o grupo", GROUP, PERMISSION, VALUE, SERVER);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(GROUP);
        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(nome);
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        PermissionServer server = PermissionServer.GERAL;
        if (args.isPresent(SERVER)) {
            server = args.get(SERVER);
        }
        g.addPermission(server, args.get(PERMISSION), PermissionValue.valueOf(args.get(VALUE).toUpperCase()));
        C.info(sender, "Permissão setada!");
        for (Player p : Bukkit.getOnlinePlayers()) {
            PermissionBaseRG.updateOp(p);
        }
    }
}
