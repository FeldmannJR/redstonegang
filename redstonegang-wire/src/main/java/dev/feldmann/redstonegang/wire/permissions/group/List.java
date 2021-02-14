package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class List extends RedstoneCmd {


    public List() {
        super("list", "lista os grupos ");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Collection<Group> gr = RedstoneGang.instance.user().getPermissions().getGroups();
        for (Group g : gr) {
            sender.sendMessage("" + g.getIdentifier() + ". " + g.getNome() + (g.getParent() != null ? (" Parent: " + g.getParent().getNome()) : ""));
        }
        if (gr.isEmpty()) {
            C.error(sender, "Não foi encontrado nenhum grupo !");
        }
    }
}
