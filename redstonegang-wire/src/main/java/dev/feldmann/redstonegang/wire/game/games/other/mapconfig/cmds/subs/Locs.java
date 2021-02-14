package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;


import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.ViewLocationsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Locs extends RedstoneCmd {


    MapConfigGame game;

    public Locs(MapConfigGame game) {
        super("locs", "Ve as localizações setadas!");
        this.game = game;
    }

    @Override
    public void command(CommandSender cs, Arguments args) {
        new ViewLocationsMenu(game).open((Player) cs);
    }
}
