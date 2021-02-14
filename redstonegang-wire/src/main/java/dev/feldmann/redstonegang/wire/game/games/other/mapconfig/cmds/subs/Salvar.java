package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import org.bukkit.command.CommandSender;

public class Salvar extends RedstoneCmd {

    MapConfigGame game;


    public Salvar(MapConfigGame game) {
        super("salvar", "usar para salvar o mapa atual para updateFloatItems!");
        this.game = game;
    }

    @Override
    public void command(CommandSender cs, Arguments a) {
        if (game.getConfigurando() == null) {
            cs.sendMessage("Para salvar o mapa, você precisa abrir algum mapa antes!");
            return;
        }
        game.saveCurrent();

    }
}
