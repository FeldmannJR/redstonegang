package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanLeaderCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.command.CommandSender;

public class ClanNome extends ClanLeaderCmd {
    private static final StringArgument NOME = new RemainStringsArgument("nome", false);

    public ClanNome(ClanAddon addon) {
        super(addon, "nome", "troca o nome do clan", NOME);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember member = getMember(sender);
        if (!member.getClan().getProps().canChangeNome()) {
            C.error(sender, "Você não pode trocar a tag do clan ainda!");
            C.error(sender, "Espere %%!", member.getClan().getProps().getTempoRestanteNome());
            return;
        }
        String er = getAddon().isNameValid(args.get(NOME));
        if (er != null) {
            C.error(sender, er);
            return;
        }

        if (getAddon().getCache().existsName(args.get(NOME))) {
            C.error(sender, "Alguem já está usando este nome!");
            return;
        }
        member.getClan().setName(args.get(NOME));
        C.info(sender, "Você trocou o nome do clan!");
        getMember(sender).getClan().sendMessage("O nome do clan foi alterado para %% !", member.getClan().getName());
        getAddon().broadcast(C.msgText(MsgType.INFO, "Clan %% teve o nome alterado para %%!", member.getClan().getColorTag(), args.get(NOME)));
        member.getClan().getProps().changeNome();
        getAddon().getCache().saveClan(member.getClan());

    }
}
