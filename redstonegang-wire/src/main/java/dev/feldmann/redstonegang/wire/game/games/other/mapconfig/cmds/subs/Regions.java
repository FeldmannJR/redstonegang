package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;


import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.ViewRegionsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Regions extends RedstoneCmd {


    MapConfigGame game;

    public Regions(MapConfigGame game) {
        super("regions", "Ve as regions setadas!");
        this.game = game;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new ViewRegionsMenu(game).open((Player) sender);
    }
}
