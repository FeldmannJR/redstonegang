package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerNameArgument extends Argument<User> {
    private boolean selfIncluded = true;

    public PlayerNameArgument(String name, boolean optional) {
        super(name, optional);
    }

    public PlayerNameArgument(String name, boolean optional, boolean selfIncluded) {
        super(name, optional);
        this.selfIncluded = selfIncluded;
    }

    @Override
    public String getErrorMessage(String input) {
        return "O jogador `" + input + "` não foi encontrado!";
    }

    @Override
    public User process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        String name = args[index];
        if (cs instanceof Player && !selfIncluded) {
            if (cs.getName().equals(name)) {
                return null;
            }
        }
        User rgpl = RedstoneGangSpigot.getPlayer(name);
        return rgpl;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {

        List<String> players = autoCompletePlayers(start, cs);
        if (cs instanceof Player && !selfIncluded) {
            players.remove(cs.getName());
        }
        return players;
    }
}
