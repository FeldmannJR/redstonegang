package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OnlinePlayerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanModCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanInviteEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.invites.ClanInvite;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdConvidar extends ClanModCmd {
    private static final OnlinePlayerArgument PLAYER = new OnlinePlayerArgument("jogador", false);


    public CmdConvidar(ClanAddon addon) {
        super(addon, "convidar", "convida um jogador para seu clan", PLAYER);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = args.get(PLAYER);
        Player p = RedstoneGangSpigot.getOnlinePlayer(pl.getId());
        if (getAddon().getCache().hasClan(pl.getId())) {
            C.error(sender, "O jogador já está em um clan!");
            return;
        }
        Player sen = (Player) sender;
        ClanMember inviter = getAddon().getCache().getMember(sen);
        if (getAddon().getInvites().isInviting(inviter.getClanTag(), pl.getId())) {
            C.error(sender, "Seu clan já está convidando este jogador!");
            return;
        }
        if (inviter.getClan().canInvite()) {
            C.error(sender, "Seu clan já possuí o número máximo de membros (%%)!", inviter.getClan().getMaxMembers());
            return;
        }
        ClanInviteEvent ev = new ClanInviteEvent(sen, p, inviter.getClan(), getAddon());
        if (Wire.callEvent(ev)) {
            return;
        }
        getAddon().getInvites().addInvite(new ClanInvite(inviter.getClanTag(), pl.getId()));
        C.send(p, ClanAddon.CLAN_INFO, "Você foi convidado para entrar no clan %% por %%", inviter.getClan(), sender);
        C.send(p, ClanAddon.CLAN_INFO, "Para aceitar execute o comando '%cmd%'", "/clan entrar " + inviter.getClanTag());
        inviter.getClan().sendMessage("%% convidou %% para entrar no clan!", sen, pl);
    }
}
