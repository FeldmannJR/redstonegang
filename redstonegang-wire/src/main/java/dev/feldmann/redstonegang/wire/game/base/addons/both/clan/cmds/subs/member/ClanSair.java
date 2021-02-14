package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.member;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanMemberCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class ClanSair extends ClanMemberCmd {

    public static final StringArgument CONFIRMAR = new StringArgument("confirmar", true, false);

    public ClanSair(ClanAddon addon) {
        super(addon, "sair", "sair do clan", CONFIRMAR);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        if (!args.isPresent(CONFIRMAR)) {
            C.info(sender, "Para confirmar digite '%cmd%'", "/clan sair confirmar");
            return;
        }
        if (!args.get(CONFIRMAR).equalsIgnoreCase("confirmar")) {
            return;
        }
        ClanMember member = getMember(sender);
        if (member.getClan().getFounder() == member.getPlayerId()) {
            C.error(sender, "Você é o fundador do clan, não pode sair! Passe a liderança para alguem para poder sair! Ou exclua o clan!");
            return;
        }
        member.getClan().sendMessage("%% saiu do clan", member.getPlayer());
        getAddon().getCache().removeFromClan(member);
        C.info(sender, "Você saiu do clan!");
    }
}
