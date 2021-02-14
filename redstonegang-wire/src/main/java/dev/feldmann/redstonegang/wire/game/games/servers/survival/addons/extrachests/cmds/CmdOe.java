package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests.ExtraChests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdOe extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false, false);

    ExtraChests manager;

    public CmdOe(ExtraChests manager) {
        super("openenderchest", "abre o enderchest de um jogador", PLAYER);
        setPermission(ExtraChests.OPEN_OTHER_ENDERCHEST);
        setAlias("oe");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        manager.openEnderchest((Player) sender, args.get(PLAYER).getId());
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
