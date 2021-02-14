package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.everybody;

import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanSubCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args.ClanArg;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClanVer extends ClanSubCmd {

    private ClanArg CLAN;

    public ClanVer(ClanAddon addon) {
        super(addon, "ver", "ve os membros de um clan", new ClanArg("clan", true, addon));
        CLAN = (ClanArg) getArgs().get(0);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        ClanMember m = getMember(sender);
        Clan view;
        if (args.isPresent(CLAN)) {
            view = args.get(CLAN);
        } else {
            if (m.getClanTag() == null) {
                C.error(sender, "Você não está em um clan! Use /clan ver TAG !");
                return;
            }
            view = m.getClan();
        }
        sender.sendMessage(" ");
        sender.sendMessage(" " + view.getColorTag() + " §7- §f" + view.getName());
        sender.sendMessage("  §eFundado em: §f" + DateUtils.formatDate(view.getFounded()));
        //Mebmros
        List<Integer> members = new ArrayList<>(view.getMembers());
        members.sort(Comparator.comparingInt(o -> getAddon().getCache().getMember(o).getRole()));
        String membersString = "";
        for (Integer id : members) {
            ClanMember idm = getAddon().getCache().getMember(id);
            if (idm.getRole() == ClanRole.STAFF) {
                continue;
            }
            if (idm.getRole() == ClanRole.MEMBER) {
                membersString += idm.getPlayer().getDisplayName() + " ";
            }
            if (idm.getRole() == ClanRole.LEADER) {
                sender.sendMessage("  §2Líder §f" + idm.getPlayer().getDisplayName());
            }
            if (idm.getRole() == ClanRole.SUBLEADER) {
                sender.sendMessage("  §4Sub-Líder §f" + idm.getPlayer().getDisplayName());
            }
        }
        if (!membersString.isEmpty())
            sender.sendMessage("  §eMembros: §f" + membersString);
        sender.sendMessage(" ");
    }
}
