package dev.feldmann.redstonegang.wire.base.cmds.perm;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.CmdExecutePerm;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Permission implements CmdExecutePerm {

    private String permission;

    public Permission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean canExecute(CommandSender cs) {
        if (cs instanceof ConsoleCommandSender) {
            return true;
        }
        if (cs instanceof Player) {
            User pl = RedstoneGang.getPlayer(((Player) cs).getUniqueId());
            return pl.permissions().hasPermission(permission);
        }
        return false;
    }

    public String getPermission() {
        return permission;
    }
}
