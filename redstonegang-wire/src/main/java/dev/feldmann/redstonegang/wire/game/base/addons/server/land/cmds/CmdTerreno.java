package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs.*;
import org.bukkit.command.CommandSender;

public class CmdTerreno extends RedstoneCmd {

    public CmdTerreno(LandAddon manager) {
        super("terreno", "Comando de terrenos");
        addCmd(new Comprar(manager));
        addCmd(new Expandir(manager));
        addCmd(new Add(manager));
        addCmd(new Config(manager));
        addCmd(new AddGlobal(manager));
        addCmd(new Amigos(manager));
        addCmd(new Listar(manager));
        addCmd(new Abandonar(manager));
        addCmd(new Confirmar(manager));
        addCmd(new Cancelar(manager));
        addCmd(new HelpCmd());
        setCooldown(new Cooldown(600));

    }

    @Override
    public void command(CommandSender sender, Arguments args) {
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
