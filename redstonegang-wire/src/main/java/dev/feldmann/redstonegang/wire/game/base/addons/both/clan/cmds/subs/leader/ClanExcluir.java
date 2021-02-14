package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLeaderCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.command.CommandSender;

public class ClanExcluir extends ClanLeaderCmd {

    public static final StringArgument CONFIRMAR = new StringArgument("confirmar", true, false);

    public ClanExcluir(ClanAddon addon) {
        super(addon, "excluir", "excluir o clan", CONFIRMAR);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        if (!args.isPresent(CONFIRMAR)) {
            C.info(sender, "Para confirmar digite '%cmd%'", "/clan excluir confirmar");
            return;
        }
        if (!args.get(CONFIRMAR).equalsIgnoreCase("confirmar")) {
            return;
        }

        ClanMember mem = getMember(sender);
        getAddon().broadcast(C.msgText(MsgType.INFO, "O clan %% foi excluido por %%!", mem.getClan(), mem.getPlayer()));
        mem.getClan().sendMessage("O clan foi excluido pelo lider %%!", mem.getPlayer());
        getAddon().getCache().deleteClan(mem.getClan());
    }

}
