package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ClanSubCmd extends RedstoneCmd {
    private ClanAddon addon;

    public ClanSubCmd(ClanAddon addon, String name, String desc, Argument... args) {
        super(name, desc, args);
        this.addon = addon;
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    public ClanAddon getAddon() {
        return addon;
    }

    public ClanMember getMember(CommandSender cs) {
        if (cs instanceof Player) {
            return addon.getCache().getMember((Player) cs);
        }
        return null;

    }
}
