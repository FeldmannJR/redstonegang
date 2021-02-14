package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.menu.EscolheTitulo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Titulo extends RedstoneCmd {
    TituloAddon addon;

    public Titulo(TituloAddon addon) {
        super("titulo", "Escolhe um titulo");
        setPermission("rg.titulo.cmd.titulo");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new EscolheTitulo(getPlayerId(sender), addon, null).open((Player) sender);
    }
}
