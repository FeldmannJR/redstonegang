package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ClanMemberArg extends Argument<ClanMember> {
    ClanAddon addon;

    public ClanMemberArg(String name, boolean optional, ClanAddon addon) {
        super(name, optional);
        this.addon = addon;
    }

    @Override
    public String getErrorMessage(String input) {
        return "O jogador `" + input + "` não está no seu clan!";
    }

    @Override
    public ClanMember process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) return null;
        String name = args[index];
        if (!(cs instanceof Player)) {
            return null;
        }
        if (cs.getName().equals(name)) {
            return null;
        }
        String tag = addon.getCache().getMember((Player) cs).getClanTag();
        User rgpl = RedstoneGangSpigot.getPlayer(name);
        if (rgpl == null) return null;
        ClanMember member = addon.getCache().getMember(rgpl.getId());
        if (!member.getClanTag().equals(tag)) {
            return null;
        }
        return member;
    }

    @Override
    public List<String> autoComplete(CommandSender cs, String start) {
        return autoCompletePlayers(start);
    }

}
