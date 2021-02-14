package dev.feldmann.redstonegang.wire.permissions.user;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.player.permissions.PermissionUser;
import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Info extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", true);

    public Info() {
        super("info", "ve as permissões e os grupos de um jogador", PLAYER);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = null;
        if (args.isPresent(PLAYER)) {
            pl = args.get(PLAYER);
        } else if (sender instanceof Player) {
            pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());
        }
        if (pl == null) {
            C.error(sender, "Jogador não encontrado!");
            return;
        }
        PermissionUser perm = pl.permissions();
        sender.sendMessage("§7=====- §d" + pl.getName() + " §7-=====");
        sender.sendMessage("§7Grupo: §6" + perm.getGroup().getNome());


        sender.sendMessage(" §9Permissions");
        HashMap<PermissionServer, HashMap<String, PermissionValue>> permissions = perm.getPermissionList();
        for (PermissionServer sv : permissions.keySet()) {
            sender.sendMessage("§a" + sv.name());
            for (String s : permissions.get(sv).keySet()) {
                sender.sendMessage("  §7" + s + "§f=§e" + permissions.get(sv).get(s).name().toLowerCase());
            }
        }


    }
}
