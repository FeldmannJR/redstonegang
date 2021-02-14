package dev.feldmann.redstonegang.wire.game.base.addons.both.changeserver.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.changeserver.ChangeServerAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class ServidorCmd extends PlayerCmd {
    private static final StringArgument SERVIDOR = new StringArgument("servidor", false);

    private ChangeServerAddon addon;

    public ServidorCmd(ChangeServerAddon addon) {
        super("servidor", "Teleporta para outro servidor", SERVIDOR);
        setPermission(addon.generatePermission("command"));
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        String server = args.get(SERVIDOR);
        if (!addon.hasPermission(getUser(player), server)) {
            C.error(player, "Você não tem permissão para ir para este servidor!");
            return;
        }
        if (!addon.isServerOnline(server)) {
            C.error(player, "Servidor offline ou não existe!");
            return;
        }
        C.info(player, "Teleportando para server %%!", server);
        addon.getServer().teleportToServer(player, server);

    }
}
