package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.OnlinePlayerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TpHere extends SimpleCmd {


    private static final OnlinePlayerArgument PLAYER = new OnlinePlayerArgument("player", false);


    public TpHere() {
        super("tphere", "teleporta um jogador até você", "tphere", PLAYER);
    }

    @Override
    public void command(Player p, Arguments a) {
        User rg = a.get(PLAYER);
        Player target = Bukkit.getPlayer(rg.getUuid());
        target.teleport(p.getLocation());

        C.info(p, "Você puxou %% para você!", rg);
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}
