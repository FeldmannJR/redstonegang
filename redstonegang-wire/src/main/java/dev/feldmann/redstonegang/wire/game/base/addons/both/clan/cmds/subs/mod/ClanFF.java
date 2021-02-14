package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanModCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class ClanFF extends ClanModCmd {

    public ClanFF(ClanAddon addon) {
        super(addon, "ff", "Habilita ou desabilita o fogo amigo");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ClanMember sen = getMember(sender);
        sen.getClan().getProps().setFf(!sen.getClan().getProps().isFf());
        C.info(sender, "Você %% fogo amigo no clan!", sen.getClan().getProps().isFf() ? "habilitou" : "desabilitou");
        sen.getClan().sendMessage("%% %% fogo amigo no clan!", sen.getPlayer(), sen.getClan().getProps().isFf() ? "habilitou" : "desabilitou");
    }
}
