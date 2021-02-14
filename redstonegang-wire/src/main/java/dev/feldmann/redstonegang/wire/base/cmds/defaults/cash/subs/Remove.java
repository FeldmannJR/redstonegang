package dev.feldmann.redstonegang.wire.base.cmds.defaults.cash.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.ConsoleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.ConsoleCommandSender;

public class Remove extends ConsoleCmd {
    private static PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);
    private static IntegerArgument QUANTIDADE = new IntegerArgument("quantidade", 0, Integer.MAX_VALUE);

    public Remove() {
        super("remove", "remove cash para um jogador", PLAYER, QUANTIDADE);
    }

    @Override
    public void command(ConsoleCommandSender player, Arguments args) {

        args.get(PLAYER).removeCash(args.get(QUANTIDADE));
        C.info(player, "Removeido %% cash para %%!", args.get(QUANTIDADE), args.get(PLAYER));
    }
}
