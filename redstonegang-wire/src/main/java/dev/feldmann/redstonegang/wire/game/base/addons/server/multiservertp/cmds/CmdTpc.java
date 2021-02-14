package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import org.bukkit.entity.Player;

public class CmdTpc extends PlayerCmd {


    private MultiServerTpAddon addon;

    public CmdTpc(MultiServerTpAddon addon) {
        super("tpc", "cancela um pedido de teleporte");
        setAlias("tpcancelar");
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.cancelRequest(player);
    }
}
