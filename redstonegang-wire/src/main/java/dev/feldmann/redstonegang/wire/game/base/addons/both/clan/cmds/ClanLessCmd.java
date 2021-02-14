package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ClanLessCmd extends ClanSubCmd {
    public ClanLessCmd(ClanAddon addon, String name, String desc, Argument... args) {
        super(addon, name, desc, args);
        setExecutePerm(cs -> {
            User pl = RedstoneGangSpigot.getPlayer(((Player) cs).getUniqueId());
            return !getAddon().getCache().hasClan(pl.getId());
        });
    }


    @Override
    public boolean canExecute(CommandSender cs) {

        boolean b = super.canExecute(cs);
        if (!b) {
            return b;
        }
        User pl = RedstoneGangSpigot.getPlayer(((Player) cs).getUniqueId());
        if (getAddon().getCache().hasClan(pl.getId())) {
            C.error(cs, "Você não pode estar em um clan para usar isto!");
            return false;
        }
        return true;
    }
}
