package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus.ListGlobalMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Amigos extends RedstoneCmd {

    LandAddon manager;
    public Amigos(LandAddon manager) {
        super("todos", "Modifica os jogadores que podem mexer em todos os seus terrenos");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        new ListGlobalMenu(manager,RedstoneGang.getPlayer(p.getUniqueId()).getId()).open(p);
    }
}
