package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import org.bukkit.command.CommandSender;

public abstract class SimpleCmd extends PlayerCmd {

    SimpleCmds addon;

    public SimpleCmd(String name, String desc, String perm, Argument... args) {
        super(name, desc, args);
        setExecutePerm(new Permission(SimpleCmds.getPermission(perm)));
    }

    public String getPermissionString() {
        return ((Permission) getExecutePerm()).getPermission();
    }

    public boolean hasSubPermission(CommandSender sender, String nome) {
        return sender.hasPermission(getPermission() + "." + nome);
    }

    public void setAddon(SimpleCmds addon) {
        this.addon = addon;
    }

    public SimpleCmds getAddon() {
        return addon;
    }

}
