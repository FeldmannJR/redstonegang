package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.lenhador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import dev.feldmann.redstonegang.wire.utils.items.TreeUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropSaplingPerk extends DropOnBreakPerk {
    public DropSaplingPerk(int level, int chance) {
        super(level, chance, "Dropar mudas ao quebrar arvores!", new ItemStack(Material.SAPLING));
    }

    @Override
    public ItemStack[] getDropItemStack(Player p, Block ent) {
        TreeUtils.TreeInfo type = TreeUtils.getFrom(ent);
        if (type != null) {
            return new ItemStack[]{type.getSapling()};
        }
        return null;
    }

}
