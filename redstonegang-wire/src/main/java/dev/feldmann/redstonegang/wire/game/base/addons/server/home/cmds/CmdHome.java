package dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.events.PlayerTeleportToHomeEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHome extends RedstoneCmd {


    private static final StringArgument HOME = new StringArgument("home", true, "casa");

    private HomeAddon manager;

    public CmdHome(HomeAddon manager) {
        super("home", "vai para uma casa", HOME);
        setAlias("casa");
        this.manager = manager;
        setPermission("rg.home.cmd.home");
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
            String[] spl = home.split(":");
            if (spl.length != 2) {
                C.error(sender, "Uso incorreto! Use /home %%:%%", "nomedojogador", "nomedahome");
                return;
            }
            pl = RedstoneGangSpigot.getPlayer(spl[0]);
            if (pl == null) {
                C.error(sender, "Jogador %% não encontrado!", spl[0]);
                return;
            }
            home = spl[1];
        }
        final boolean self = pl.getName().equals(sender.getName());
        final String homename = home;
        User finalPl = pl;
        new Thread(() -> {
            synchronized (sender) {
                HomeEntry entry = manager.getDb().loadHome(finalPl.getId(), homename);
                if (entry == null || !manager.canUse((Player) sender, entry)) {
                    if (self) {
                        C.error(sender, "Home não encontrada! Use %% para ver suas homes!", "/homes");

                    } else {
                        C.error(sender, "Home não encontrada!");
                    }
                    return;
                }

                manager.scheduler().runSync(() -> {
                    if (sender != null && ((Player) sender).isOnline()) {
                        if (Wire.callEvent(new PlayerTeleportToHomeEvent((Player) sender, entry))) {
                            return;
                        }
                        manager.getServer().teleport((Player) sender, entry.getLocation());
                        if (self) {
                            C.info(sender, "Teleportado para a home %% !", entry.getName());
                        } else {
                            C.info(sender, "Teleportado para a home %% de %% !", entry.getName(), finalPl.getName());
                            if (finalPl.isOnline() && finalPl.getConfig(HomeAddon.SHOW_TELEPORT)) {
                                finalPl.sendMessage(C.msg(MsgType.INFO, "O jogador %% teleportou para a sua home %% !", sender, entry.getName()));
                            }
                        }

                    }

                });
            }
        }
        ).start();
    }

}
