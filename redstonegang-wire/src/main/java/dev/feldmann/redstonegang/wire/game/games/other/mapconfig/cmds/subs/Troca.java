package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.load.SelectFolderMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Troca extends RedstoneCmd {
    MapConfigGame game;

    public Troca(MapConfigGame game) {
        super("troca", "Troca o mapa para configurar");
        this.game = game;
    }

    @Override
    public void command(CommandSender cs, Arguments args) {
        new SelectFolderMenu(game).open((Player) cs);
    }
}
