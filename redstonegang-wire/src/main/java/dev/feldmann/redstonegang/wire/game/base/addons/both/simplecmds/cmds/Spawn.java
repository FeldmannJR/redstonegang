package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class Spawn extends SimpleCmd {


    public Spawn() {
        super("spawn", "vai para o spawn do servidor", "spawn");
    }

    @Override
    public void command(Player p, Arguments a) {
        BungeeLocation spawn = getAddon().getSpawn();
        C.info(p, "Teleportado para o spawn!");
        if (spawn == null) {
            p.teleport(p.getLocation().getWorld().getSpawnLocation());
        } else {
            getAddon().getServer().teleport(p, spawn);
        }


    }

    @Override
    public boolean canOverride() {
        return true;
    }
}