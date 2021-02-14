package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLeaderCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args.ClanMemberArg;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class ClanDarLider extends ClanLeaderCmd {

    public ClanMemberArg MEMBER;

    public ClanDarLider(ClanAddon addon) {
        super(addon, "darlider", "da lider para um membro do clan", new ClanMemberArg("membro", false, addon));
        MEMBER = (ClanMemberArg) getArgs().get(0);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember member = args.get(MEMBER);


        if (member.getPlayer().getOption(getAddon().MAX_MEMBERS) < member.getClan().getMembers().size()) {
            C.error(sender, "Este jogador não pode ser líder! Pois o clan tem mais membros que ele pode liderar!");
            return;
        }

        member.setRole(ClanRole.LEADER);
        ClanMember sen = getMember(sender);
        sen.setRole(ClanRole.MEMBER);
        member.getClan().setFounder(member.getPlayerId());
        member.getClan().clearCache();
        getAddon().getCache().saveClan(member.getClan());
        getAddon().getCache().saveMember(member);
        getAddon().getCache().saveMember(sen);
        member.getClan().sendMessage("%% passou a liderança para %%!", sen.getPlayer(), member.getPlayer());
    }

}
