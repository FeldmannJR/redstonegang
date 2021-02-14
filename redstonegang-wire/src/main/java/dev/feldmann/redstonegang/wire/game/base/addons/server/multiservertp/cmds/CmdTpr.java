package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import org.bukkit.entity.Player;

public class CmdTpr extends PlayerCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false, false);

    private MultiServerTpAddon addon;

    public CmdTpr(MultiServerTpAddon addon) {
        super("tpr", "recusa um pedido de teleporte", PLAYER);
        setAlias("tprecusar");
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.declineTeleport(player, args.get(PLAYER));
    }
}
