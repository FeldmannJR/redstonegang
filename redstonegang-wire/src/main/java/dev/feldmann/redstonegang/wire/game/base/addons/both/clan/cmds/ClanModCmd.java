package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

public abstract class ClanModCmd extends ClanSubCmd {
    public ClanModCmd(ClanAddon addon, String name, String desc, Argument... args) {
        super(addon, name, desc, args);
        setExecutePerm(cs -> {
            User pl = RedstoneGangSpigot.getPlayer(((Player) cs).getUniqueId());
            return getAddon().getCache().hasClan(pl.getId()) && getAddon().getCache().getMember(pl.getId()).getRole() >= ClanRole.SUBLEADER;

        });
    }
    @Override
    protected Function<CommandSender, Boolean> preProcessCommandAsync() {
        return (sender) -> {
            ClanMember member = getAddon().getCache().getDb().loadMember(getAddon().getPlayerId((Player) sender));
            if (member.getClanTag() != null && member.getRole() >= ClanRole.SUBLEADER) {
                return member.getClanTag().equals(getAddon().getCache().getMember((Player) sender).getClanTag());
            }
            return false;
        };
    }

    @Override
    public boolean canExecute(CommandSender cs) {

        boolean b = super.canExecute(cs);
        if (!b) {
            return b;
        }
        User pl = RedstoneGangSpigot.getPlayer(((Player) cs).getUniqueId());
        if (!getAddon().getCache().hasClan(pl.getId()) || getAddon().getCache().getMember(pl.getId()).getRole() < ClanRole.SUBLEADER) {
            C.error(cs, "Você precisa ser mod de um clan para usar este comando!");
            return false;
        }
        return true;
    }
}
