package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class AddTitulo extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false);
    private static final StringArgument TITULO = new StringArgument("titulo", false);
    private static final StringArgument COR = new StringArgument("cor", false);


    TituloAddon addon;

    public AddTitulo(TituloAddon addon) {
        super("addtitulo", "Escolhe um titulo", PLAYER, TITULO, COR);
        this.addon = addon;
        setPermission("rg.titulo.cmd.addtitulo");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        addon.addTitulo(args.get(PLAYER).getId(), args.get(TITULO), args.get(COR));
        C.info(sender, "Adicionado!");
    }
}
