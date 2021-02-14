package dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDeleteHome extends RedstoneCmd {


    private static final StringArgument HOME = new StringArgument("home", false);

    private HomeAddon manager;

    public CmdDeleteHome(HomeAddon manager) {
        super("deletehome", "deleta uma home", HOME);
        this.manager = manager;
        setCooldown(new Cooldown(3000));
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String home = args.get(HOME);
        User pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());
        if (home.contains(":")) {
            if (!pl.hasPermission(manager.HOME_ADMIN)) {
                C.error(sender, "Home não encontrada! Use %% para ver suas homes!", "/homes");
                return;
            }
            String[] spl = home.split(":");
            if (spl.length != 2) {
                C.error(sender, "Uso incorreto! Use /deletehome %%:%%", "nomedojogador", "nomedahome");
                return;
            }
            pl = RedstoneGangSpigot.getPlayer(spl[0]);
            if (pl == null) {
                C.error(sender, "Jogador %% não encontrado!", spl[0]);
                return;
            }
            home = spl[1];
        }

        final String homename = home;
        User finalPl = pl;
        new Thread(() -> {
            synchronized (sender) {
                HomeEntry entry = manager.getDb().loadHome(finalPl.getId(), homename);
                if (entry == null) {
                    C.info(sender, "Teleportado para a home %% de %% !", entry.getName(), finalPl.getName());
                    return;
                }
                manager.getDb().delete(entry);
                C.info(sender, "Home %% deletada!", entry.getName());
            }
        }
        ).start();
    }


}
