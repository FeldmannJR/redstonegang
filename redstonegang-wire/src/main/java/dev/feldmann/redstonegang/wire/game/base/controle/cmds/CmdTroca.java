package dev.feldmann.redstonegang.wire.game.base.controle.cmds;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Fodao;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.controle.menus.TrocaGameMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTroca extends RedstoneCmd {

    private static final StringArgument GAME_NAME = new StringArgument("game", false);

    public CmdTroca() {
        super("troca");
        setExecutePerm(Fodao.INSTANCE);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        if (sender instanceof Player) {
            new TrocaGameMenu().open((Player) sender);
        } else {
            if (args.isPresent(GAME_NAME)) {
                for (Games g : Games.values()) {
                    if (g.name().equalsIgnoreCase(args.getValue(GAME_NAME))) {
                        Wire.instance.game().loadGame(g.getEntry());
                        break;
                    }
                }
            }


        }

    }
}
