package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.tps;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DoubleArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TpPos extends SimpleCmd {

    private static final DoubleArgument X = new DoubleArgument("x", false);
    private static final DoubleArgument Y = new DoubleArgument("y", false, 0.5, 250);
    private static final DoubleArgument Z = new DoubleArgument("z", false);

    public TpPos() {
        super("tppos", "teleporta para uma coordenada", "tppos", X, Y, Z);
    }

    @Override
    public void command(Player p, Arguments a) {
        p.teleport(new Location(p.getWorld(), a.get(X), a.get(Y), a.get(Z)));
        C.info(p, "Teleportado para %% %% %%", a.get(X), a.get(Y), a.get(Z));
    }
}
