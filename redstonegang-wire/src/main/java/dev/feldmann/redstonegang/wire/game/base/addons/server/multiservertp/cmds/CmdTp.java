package dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Permission;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import org.bukkit.entity.Player;

public class CmdTp extends PlayerCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false, false);

    private MultiServerTpAddon addon;

    public CmdTp(MultiServerTpAddon addon) {
        super("tp", "teleporta para o jogador selecionado", PLAYER);
        this.addon = addon;
        setExecutePerm(new Permission(addon.TPA_PERMISSION.getKey()));
    }

    @Override
    public void command(Player player, Arguments args) {
        if (addon.hasTeleportPermission(player)) {
            addon.teleport(player, args.get(PLAYER), false);
        } else {
            addon.sendRequest(player, args.get(PLAYER));
        }

    }
}
