package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.invites;

import dev.feldmann.redstonegang.common.utils.invites.InviteManager;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class ClanInvites extends InviteManager<String, Integer, ClanInvite> {
    ClanAddon addon;

    public ClanInvites(ClanAddon addon) {
        super(60000);
        this.addon = addon;
    }

    @Override
    protected void inviteExpired(ClanInvite inv) {
        Clan clan = addon.getCache().getClan(inv.getInviter());
        if (clan != null) {
            clan.sendMessage("O convite para " + RedstoneGangSpigot.getOnlinePlayer(inv.getInvited()).getName() + " expirou!");
        }
        Player i = RedstoneGangSpigot.getOnlinePlayer(inv.getInvited());
        if (i != null) {
            C.info(i, "O convite para entrar no clan %% expirou!", inv.getInviter());
        }
    }
}
