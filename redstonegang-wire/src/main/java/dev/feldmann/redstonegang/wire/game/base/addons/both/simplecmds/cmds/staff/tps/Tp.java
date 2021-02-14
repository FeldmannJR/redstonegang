package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OnlinePlayerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Tp extends SimpleCmd {


    private static final OnlinePlayerArgument PLAYER = new OnlinePlayerArgument("player", false);


    public Tp() {
        super("tp", "teleporta para um jogador", "tp", PLAYER);
    }

    @Override
    public void command(Player p, Arguments a) {
        User rg = a.get(PLAYER);
        Location loc = Bukkit.getPlayer(rg.getUuid()).getLocation();
        p.teleport(loc);
        C.info(p, "Teleportado para %% !", rg);
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}
