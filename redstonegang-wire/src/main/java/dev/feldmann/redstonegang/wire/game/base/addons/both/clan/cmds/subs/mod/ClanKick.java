package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanModCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.args.ClanMemberArg;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class ClanKick extends ClanModCmd {

    public ClanMemberArg MEMBER;

    public ClanKick(ClanAddon addon) {
        super(addon, "kick", "expulsa um membro do clan", new ClanMemberArg("membro", false, addon));
        MEMBER = (ClanMemberArg) getArgs().get(0);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember member = args.get(MEMBER);
        ClanMember sen = getMember(sender);
        if (sen.getRole() <= member.getRole()) {
            C.error(sender, "Você só pode expulsar pessoas com o cargo menor que o seu!");
            return;
        }
        getAddon().getCache().removeFromClan(member);
        sen.getClan().sendMessage("%% foi expulso do clan por %%", member.getPlayer(), sen.getPlayer());
    }
}
