package dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds.adm;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Set extends RedstoneCmd {
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false);
    private static final IntegerArgument VALOR = new IntegerArgument("valor", 0, Integer.MAX_VALUE);


    public Set() {
        super("set", "seta o valor para um jogador", PLAYER, VALOR);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        EconomyAddon manager = ((EconomyAdmCommand) getParent()).getManager();
        manager.set(args.get(PLAYER).getId(), args.get(VALOR));
        C.info(sender, "Setado ## para o jogador %% !", args.get(VALOR), args.get(PLAYER));

    }
}
