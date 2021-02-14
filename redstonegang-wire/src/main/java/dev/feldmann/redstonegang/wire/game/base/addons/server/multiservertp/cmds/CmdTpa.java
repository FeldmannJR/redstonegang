package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import org.bukkit.entity.Player;

public class CmdTpa extends PlayerCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false, false);

    private MultiServerTpAddon addon;

    public CmdTpa(MultiServerTpAddon addon) {
        super("tpa", "aceita um pedido de teleporte", PLAYER);
        setAlias("tpaceitar");
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.acceptTpRequest(player, args.get(PLAYER));
    }
}
