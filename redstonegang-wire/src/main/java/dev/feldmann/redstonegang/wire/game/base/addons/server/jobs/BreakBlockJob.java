package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BreakBlockJob {


    BlockXpInfo[] getBlocksXps();

    default boolean canGainXp(Player p, Block b) {
        return true;
    }
}
