package dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdHomes extends RedstoneCmd {


    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", true, true);

    private HomeAddon manager;

    public CmdHomes(HomeAddon manager) {
        super("homes", "lista homes", PLAYER);
        setPermission("rg.home.cmd.homes");
        this.manager = manager;
        PLAYER.setShowInHelp(false);
        setCooldown(new Cooldown(3000));
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());
        if (args.isPresent(PLAYER)) {
            if (pl.hasPermission(manager.HOME_ADMIN)) {
                pl = args.get(PLAYER);
            }
        }
        User finalPl = pl;
        new Thread(() -> {
            synchronized (sender) {
                List<HomeEntry> entry = manager.getDb().loadHomes(finalPl.getId());
                if (entry.isEmpty()) {
                    C.error(sender, "Sem nenhuma home setada!");
                    return;
                }
                String homes = "";
                for (HomeEntry h : entry) {
                    if (!homes.isEmpty()) {
                        homes += " ,";
                    }
                    homes += h.getName();
                    if (h.getType() == HomeType.OPEN) {
                        homes += "(Pública)";
                    }
                }
                C.info(sender, "Homes: %%", homes);
            }
        }
        ).start();
    }

}
