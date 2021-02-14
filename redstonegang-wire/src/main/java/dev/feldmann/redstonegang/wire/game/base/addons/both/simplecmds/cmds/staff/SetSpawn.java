package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class SetSpawn extends SimpleCmd {


    public SetSpawn() {
        super("setspawn", "Seta o spawn na localização atual", "setspawn");
    }

    @Override
    public void command(Player p, Arguments a) {
        getAddon().setSpawn(p.getLocation());
        p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
        C.info(p, "Spawn setado para a localização atual!");
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}