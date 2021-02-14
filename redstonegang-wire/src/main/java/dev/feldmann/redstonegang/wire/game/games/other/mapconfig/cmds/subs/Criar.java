package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.load.CriarMapaMenu;
import org.bukkit.entity.Player;

public class Criar extends PlayerCmd {


    MapConfigGame game;

    public Criar(MapConfigGame game) {
        super("criar", "Cria um mapa novo");
        this.game = game;
    }

    @Override
    public void command(Player player, Arguments args) {
        new CriarMapaMenu(game).open(player);
    }
}
