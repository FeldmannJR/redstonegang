package dev.feldmann.redstonegang.wire.base.cmds.types;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCmd extends RedstoneCmd {
    public PlayerCmd(String name, String desc, Argument... args) {
        super(name, desc, args);
    }

    public PlayerCmd(String name) {
        super(name);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        command((Player) sender, args);
    }

    public abstract void command(Player player, Arguments args);

    public User getUser(Player player){
        return RedstoneGangSpigot.getPlayer(player);
    }
    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
