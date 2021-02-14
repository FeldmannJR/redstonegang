package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ClanArg extends Argument<Clan> {
    ClanAddon addon;

    public ClanArg(String name, boolean optional, ClanAddon addon) {
        super(name, optional);
        this.addon = addon;
    }

    @Override
    public String getErrorMessage(String input) {
        return "O clan `" + input + "` não existe!";
    }

    @Override
    public Clan process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        String name = args[index];
        if (!(cs instanceof Player)) {
            return null;
        }
        Clan cl = addon.getCache().getClan(name.toLowerCase());
        return cl;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {
        return Arrays.asList();
    }

}
