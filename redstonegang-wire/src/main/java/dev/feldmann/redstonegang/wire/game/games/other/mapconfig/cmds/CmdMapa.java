package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds;


import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs.*;
import org.bukkit.command.CommandSender;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs.*;

public class CmdMapa extends RedstoneCmd {

    MapConfigGame game;

    public CmdMapa(MapConfigGame game) {
        super("mapa","Comando de configuração de mapas!");
        this.game = game;
        addCmd(new Troca(game));
        addCmd(new Set(game));
        addCmd(new Locs(game));
        addCmd(new Regions(game));
        addCmd(new Salvar(game));
        addCmd(new SalvarConfig(game));
        addCmd(new Criar(game));
        addCmd(new HelpCmd());
    }



    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
