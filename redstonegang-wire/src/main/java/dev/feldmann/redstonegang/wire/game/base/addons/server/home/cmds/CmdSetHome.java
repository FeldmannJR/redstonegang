package dev.feldmann.redstonegang.wire.game.base.addons.server.home.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeEntry;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeType;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.events.PlayerSetHomeEvent;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetHome extends RedstoneCmd {
    private HomeAddon manager;
    private static final StringArgument HOME = new StringArgument("home", true, "casa");


    public CmdSetHome(HomeAddon manager) {
        super("sethome", "seta uma home", HOME);
        setPermission("rg.home.cmd.sethome");
        setAlias("setcasa");
        this.manager = manager;
        setCooldown(new Cooldown(3000));
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        if (!StringUtils.isOnlyNumbersAndLetters(args.get(HOME))) {
            C.error(sender, "Nome inválido para casa! Use somente numeros e letras!");
            return;
        }
        boolean update = manager.getDb().loadHome(manager.getPlayerId((Player) sender), args.get(HOME)) != null;
        if (!update) {

            User player = RedstoneGang.getPlayer(p.getUniqueId());
            int max = (int) player.getOption(manager.MAX_HOMES);
            if (max != -1 && max <= manager.getDb().countHomes(manager.getPlayerId(p))) {
                C.error(p, "Você não pode mais setar homes, seu limite é %% !", max);
                return;
            }

        }
        if (Wire.callEvent(new PlayerSetHomeEvent((Player) sender, manager, args.get(HOME), BungeeLocation.fromPlayer((Player) sender), update))) {
            return;
        }

        manager.getDb().saveHome(new HomeEntry(manager.getPlayerId(p), args.get(HOME), BungeeLocation.fromPlayer(p), HomeType.CLOSED));
        C.info(sender, "Home %% setada!", args.get(HOME));

    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
