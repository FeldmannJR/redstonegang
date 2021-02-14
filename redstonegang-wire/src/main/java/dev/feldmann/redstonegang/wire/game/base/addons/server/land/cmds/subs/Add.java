package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus.EditPlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Add extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false, false);
    LandAddon manager;

    public Add(LandAddon manager) {
        super("add", "adiciona uma pessoa para mexer no terreno atual", PLAYER);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = args.get(PLAYER);
        Player p = (Player) sender;
        Land terreno = manager.getTerreno(p);

        if (manager.canEditTerreno(p, terreno)) {
            if (terreno.hasPlayerProperty(pl.getId())) {
                C.error(sender, "Este jogador já é um membro do terreno");
                C.info(sender, "Para editar ou remover como membro use o comando `%cmd%` e vá em membros!", "/terreno config");
                return;
            }
            PlayerProperties properties = terreno.getPlayerProperty(pl.getId());
            new EditPlayerProperty(manager,properties, 0, false).open(p);
        }


    }
}
