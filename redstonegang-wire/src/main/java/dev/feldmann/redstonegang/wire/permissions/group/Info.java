package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Info extends RedstoneCmd {

    private static final StringArgument GRUPO = new StringArgument("grupo");

    public Info() {
        super("info", "ve as informações/perms do grupo", GRUPO);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(args.get(GRUPO));
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        sender.sendMessage("§7=====- §e" + g.getIdentifier() + ". " + g.getNome() + " §7-=====");
        sender.sendMessage("§7Default Group: §f" + g.isDefaultGroup());
        if (g.getParent() != null) {
            sender.sendMessage("§7Parent: §f" + g.getParent().getNome());
        }
        if (g.getDiscordRole() != null) {
            sender.sendMessage("§7Discord Role: §f" + g.getDiscordRole());
        }
        if (g.getPrefix() != null) {
            sender.sendMessage("§7Prefix: §r" + g.getPrefix());
        }
        if (g.getPrefix() != null) {
            sender.sendMessage("§7Suffix: §r" + g.getSuffix());
        }

        if (!g.getPermissionList().isEmpty()) {
            sender.sendMessage("§9Permissions ");
            HashMap<PermissionServer, HashMap<String, PermissionValue>> permissions = g.getPermissionList();
            for (PermissionServer sv : permissions.keySet()) {
                sender.sendMessage("§a" + sv.name());
                for (String s : permissions.get(sv).keySet()) {
                    sender.sendMessage("  §7" + s + "§f=§e" + permissions.get(sv).get(s).name().toLowerCase());
                }
            }
        }
        if (!g.getOptions().isEmpty()) {
            sender.sendMessage("§dOptions ");
            for (String key : g.getOptions()) {
                sender.sendMessage(" §7" + key + "§f=§e" + g.getOption(key));
            }
        }

    }
}
