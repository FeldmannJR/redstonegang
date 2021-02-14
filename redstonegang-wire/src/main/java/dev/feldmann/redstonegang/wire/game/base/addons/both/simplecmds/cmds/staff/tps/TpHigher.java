package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DoubleArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TpHigher extends SimpleCmd {

    private static final DoubleArgument X = new DoubleArgument("x", false);
    private static final DoubleArgument Z = new DoubleArgument("z", false);

    public TpHigher() {
        super("tphigher", "teleporta para uma coordenada", "tphigher", X, Z);
    }

    @Override
    public void command(Player p, Arguments a) {
        double y = p.getWorld().getHighestBlockYAt(a.get(X).intValue(), a.get(Z).intValue()) + 2;
        p.teleport(new Location(p.getWorld(), a.get(X), y, a.get(Z)));
        C.info(p, "Teleportado para %% %% %%", a.get(X), y, a.get(Z));
    }
}
