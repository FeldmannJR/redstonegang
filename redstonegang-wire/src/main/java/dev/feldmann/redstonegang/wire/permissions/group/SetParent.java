package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.permissions.PermissionBaseRG;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetParent extends RedstoneCmd {

    private static final StringArgument GROUP = new StringArgument("group");
    private static final StringArgument PARENT_GROUP = new StringArgument("parent");


    public SetParent() {
        super("setparent", "Set uma permissão para o grupo", GROUP, PARENT_GROUP);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(GROUP);
        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(nome);
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        Group parent = RedstoneGang.instance.user().getPermissions().getGroupByName(args.get(PARENT_GROUP));
        if (g == null) {
            C.error(sender, "Grupo parente não existe!");
            return;
        }
        if (parent.getParent() != null && parent.getParent().getIdentifier() == g.getIdentifier()) {
            C.error(sender, "Herança circular!!!");
            return;
        }

        g.setParentId(parent.getIdentifier());
        C.info(sender, "Parent setado!");
        for (Player p : Bukkit.getOnlinePlayers()) {
            PermissionBaseRG.updateOp(p);
        }
    }
}
