package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import org.bukkit.entity.Player;

public class CmdOi extends PlayerCmd {
    private static PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false, false);

    public CmdOi() {
        super("openinv", "abre o inventário", PLAYER);
        setAlias("oi");
    }

    @Override
    public void command(Player player, Arguments args) {
        User user = args.get(PLAYER);
        player.sendMessage("Tchau");
    }
}
