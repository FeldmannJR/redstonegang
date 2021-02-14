package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.agricultor;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RandomSeedPerk extends DropOnBreakPerk {

    public RandomSeedPerk(int level, int chance) {
        super(level, chance, "Pode dropar sementes aleatórias", new ItemStack(Material.SEEDS));
    }


    @Override
    public ItemStack[] getDropItemStack(Player p, Block b) {
        List<ItemStack> its = Arrays.asList(new ItemStack(Material.SEEDS), new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS));
        ItemStack it = RandomUtils.getRandom(its);
        return new ItemStack[]{it};
    }

}
