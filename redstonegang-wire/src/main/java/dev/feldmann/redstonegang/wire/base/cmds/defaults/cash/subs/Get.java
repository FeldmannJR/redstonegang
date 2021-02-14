package dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.ConsoleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.ConsoleCommandSender;

public class Get extends ConsoleCmd {
    private static PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);

    public Get() {
        super("ver", "ver quanto cash um jogador tem", PLAYER);
    }

    @Override
    public void command(ConsoleCommandSender player, Arguments args) {

        int tem = args.get(PLAYER).getCash();
        C.info(player, "O jogador %% tem %% cash!", args.get(PLAYER), tem);
    }
}
