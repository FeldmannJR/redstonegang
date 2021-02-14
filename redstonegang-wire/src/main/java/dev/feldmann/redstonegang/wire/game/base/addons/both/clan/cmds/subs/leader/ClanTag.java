package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLeaderCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanChangeTagEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanTag extends ClanLeaderCmd {
    private static final StringArgument TAG = new StringArgument("tag", false);

    public ClanTag(ClanAddon addon) {
        super(addon, "tag", "troca a tag do clan", TAG);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember member = getMember(sender);
        if (!member.getClan().getProps().canChangeTag()) {
            C.error(sender, "Você não pode trocar a tag do clan ainda!");
            C.error(sender, "Espere %%!", member.getClan().getProps().getTempoRestanteTag());
            return;
        }
        String er = getAddon().isTagValid((Player) sender, args.get(TAG));
        if (er != null) {
            C.error(sender, er);
            return;
        }
        String old = member.getClan().getColorTag();
        String color = ChatColor.translateAlternateColorCodes('&', args.get(TAG));
        if (!getAddon().getCache().changeTag(member.getClan(), color)) {
            C.error(sender, "Alguem já está usando esta tag!");
            return;
        }
        if (Wire.callEvent(new ClanChangeTagEvent(getAddon(), member.getClan(), color))) {
            return;
        }
        C.info(sender, "Você trocou a tag do clan!");
        getMember(sender).getClan().sendMessage("A tag do clan foi alterada para %% !", member.getClan().getColorTag());
        getAddon().broadcast(C.msgText(MsgType.INFO, "Clan %% teve a tag alterada para %%!", old, member.getClan().getColorTag()));
        member.getClan().getProps().changeTag();
        getAddon().getCache().saveClan(member.getClan());
    }
}
