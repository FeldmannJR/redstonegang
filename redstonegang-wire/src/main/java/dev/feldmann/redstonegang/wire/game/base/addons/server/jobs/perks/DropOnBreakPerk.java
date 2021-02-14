package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class DropOnBreakPerk extends DropOnActionPerk<Block> {

    public DropOnBreakPerk(int level, int chance, String nome, ItemStack it) {
        super(level, chance, nome, it);
    }

    public DropOnBreakPerk(int level, int chance, String nome, Cooldown cooldown, ItemStack it) {
        super(level, chance, nome, cooldown, it);
    }

    @Override
    public Location getLocation(Block block) {
        return block.getLocation();
    }

}
