package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.everybody.ClanListar;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.everybody.ClanVer;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.less.ClanEntrar;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.less.ClanCriar;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.member.ClanSair;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod.ClanFF;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod.ClanKick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.mod.CmdConvidar;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.leader.*;
import org.bukkit.command.CommandSender;

public class CmdClan extends RedstoneCmd {
    public CmdClan(ClanAddon addon) {
        super("clan", "comando de clans");
        addCmd(new ClanVer(addon));
        addCmd(new ClanListar(addon));
        addCmd(new ClanCriar(addon));
        addCmd(new CmdConvidar(addon));
        addCmd(new ClanEntrar(addon));
        addCmd(new ClanKick(addon));
        addCmd(new ClanSair(addon));
        addCmd(new ClanTag(addon));
        addCmd(new ClanNome(addon));
        addCmd(new ClanDarLider(addon));
        addCmd(new ClanExcluir(addon));
        addCmd(new ClanFF(addon));
        addCmd(new ClanPromover(addon));
        addCmd(new ClanRebaixar(addon));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
