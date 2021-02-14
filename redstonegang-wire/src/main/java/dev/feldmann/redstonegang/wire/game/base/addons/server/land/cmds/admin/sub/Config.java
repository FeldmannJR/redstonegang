package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus.EditConfigMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Config extends RedstoneCmd {
    LandAddon manager;

    public Config(LandAddon manager) {
        super("config", "edita as configurações do terreno atual");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;

        Land terreno = manager.getTerreno(p);
        if (terreno == null) {
            C.error(sender, "Você não está em um terreno");
            return;
        }
        new EditConfigMenu(manager, terreno, true).open(p);

    }
}
