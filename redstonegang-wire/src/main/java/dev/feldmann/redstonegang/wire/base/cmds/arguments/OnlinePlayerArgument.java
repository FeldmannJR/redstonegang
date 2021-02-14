package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class OnlinePlayerArgument extends Argument<User> {
    public OnlinePlayerArgument(String name, boolean optional) {
        super(name, optional);
    }

    @Override
    public String getErrorMessage(String input) {
        return "O jogador `" + input + "` não está online!";
    }

    @Override
    public User process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        String name = args[index];
        Player found = Bukkit.getPlayer(name);
        if (found == null) {
            return null;
        }
        return RedstoneGang.getPlayer(found.getUniqueId());
    }

    @Override
    public List<String> autoComplete(CommandSender cs,String start) {
        return autoCompletePlayers(start);
    }
}
