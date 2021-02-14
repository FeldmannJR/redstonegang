package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandPlayer;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus.EditPlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddGlobal extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false, false);

    LandAddon manager;

    public AddGlobal(LandAddon manager) {
        super("addtodos", "adiciona um jogador para mexer em todos os seus terrenos", PLAYER);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = args.get(PLAYER);
        Player p = (Player) sender;
        LandPlayer tp = manager.getPlayer(RedstoneGang.getPlayer(p.getUniqueId()).getId());

        if (tp.getProperty(pl.getId()) != null) {
            C.error(sender, "Este jogador já é um amigo seu!");
            C.info(p, "Para editar ou remover um amigo, use o comando `%%`!", "/terreno amigos");
            return;
        }
        PlayerProperties add = tp.add(pl.getId());
        new EditPlayerProperty(manager, add, 0, false).open(p);


    }
}
