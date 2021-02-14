package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLeaderCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args.ClanMemberArg;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class ClanRebaixar extends ClanLeaderCmd {

    public ClanMemberArg MEMBER;

    public ClanRebaixar(ClanAddon addon) {
        super(addon, "rebaixar", "rebaixa um Sub-Líder a membro", new ClanMemberArg("membro", false, addon));
        MEMBER = (ClanMemberArg) getArgs().get(0);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember member = args.get(MEMBER);
        if (member.getRole() != ClanRole.SUBLEADER) {
            C.error(sender, "Este jogador não é Sub-Líder");
            return;
        }
        member.setRole(ClanRole.MEMBER);
        getAddon().getCache().saveMember(member);
        member.getClan().sendMessage("%% rebaixo %% para membro!", sender, member.getPlayer());
    }

}
