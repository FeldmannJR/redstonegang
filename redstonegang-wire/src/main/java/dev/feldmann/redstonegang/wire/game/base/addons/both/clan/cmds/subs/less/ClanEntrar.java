package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.less;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLessCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanJoinEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.invites.ClanInvite;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanEntrar extends ClanLessCmd {
    private static final StringArgument TAG = new StringArgument("tag", false);

    public ClanEntrar(ClanAddon addon) {
        super(addon, "entrar", "entra em um clan", TAG);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String tag = args.get(TAG).toLowerCase();
        User p = RedstoneGangSpigot.getPlayer(((Player) sender).getUniqueId());
        ClanInvite invite = getAddon().getInvites().getInvite(tag, p.getId());
        if (invite == null) {
            C.error(sender, "Você não foi convidado para entrar neste clan!");
            return;
        }
        Clan c = getAddon().getCache().getClan(tag);
        if (c == null) {
            C.error(sender, "Este clan não existe mais");
            return;
        }
        ClanJoinEvent ev = new ClanJoinEvent((Player) sender, c, getAddon());
        if (Wire.callEvent(ev)) {
            return;
        }
        ClanMember member = getAddon().getCache().getMember(p.getId());
        member.setRole(ClanRole.MEMBER);
        getAddon().getCache().addToClan(member, c);
        getAddon().getInvites().deleteInvite(invite.getInviter(), invite.getInvited());
        c.sendMessage("O jogador %% entrou no clan!", p);
        C.send(sender, ClanAddon.CLAN_INFO, "Você entrou no clan %%!", c);
    }
}
