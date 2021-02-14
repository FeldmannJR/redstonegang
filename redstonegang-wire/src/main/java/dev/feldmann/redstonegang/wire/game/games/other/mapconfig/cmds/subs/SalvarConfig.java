package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import org.bukkit.command.CommandSender;

public class SalvarConfig extends RedstoneCmd {

    MapConfigGame game;


    public SalvarConfig(MapConfigGame game) {
        super("salvarconfig", "salva somente a config de locs/regions!");
        this.game = game;
    }

    @Override
    public void command(CommandSender cs, Arguments args) {
        if (game.getConfigurando() == null) {
            cs.sendMessage("Para salvar o mapa, você precisa abrir algum mapa antes!");
            return;
        }
        game.mapas().saveConfig(game.getConfigurando());
        cs.sendMessage("§aConfig salva!");
    }


}
