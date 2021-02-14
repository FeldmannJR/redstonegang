package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub.Config;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub.Deletar;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub.Listar;
import org.bukkit.command.CommandSender;

public class CmdTerrenoAdmin extends RedstoneCmd {
    LandAddon manager;

    public CmdTerrenoAdmin(LandAddon manager) {
        super("terrenoadm");
        setExecutePerm(new Permission(LandAddon.TERRENOS_ADMIN));
        addCmd(new Listar(manager));
        addCmd(new Deletar(manager));
        addCmd(new Config(manager));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
