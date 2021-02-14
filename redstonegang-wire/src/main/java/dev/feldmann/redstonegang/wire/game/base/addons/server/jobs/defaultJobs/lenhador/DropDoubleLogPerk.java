package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.lenhador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import dev.feldmann.redstonegang.wire.utils.items.TreeUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropDoubleLogPerk extends DropOnBreakPerk {
    public DropDoubleLogPerk(int level, int chance) {
        super(level, chance, "Pode dropar o dobro de madeira!", new ItemStack(Material.LOG));
    }


    @Override
    public ItemStack[] getDropItemStack(Player p, Block ent) {
        TreeUtils.TreeInfo type = TreeUtils.getFrom(ent);
        if (type != null) {
            return new ItemStack[]{type.getLog()};
        }
        return null;
    }


}
