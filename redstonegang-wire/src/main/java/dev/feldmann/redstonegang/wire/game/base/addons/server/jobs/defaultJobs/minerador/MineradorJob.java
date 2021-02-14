package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.minerador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BlockXpInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BreakBlockJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class MineradorJob extends Job implements BreakBlockJob {

    public MineradorJob() {
        super("minerador", "Minerador", new ItemStack(Material.IRON_PICKAXE));
        addPerk(new DropOnBreakPerk(10, 50, "Pode ganhar + minérios", new ItemStack(Material.IRON_ORE)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, Block ent) {
                if (ent.getType() != Material.STONE) {
                    Collection<ItemStack> drops = ent.getDrops();
                    return drops.toArray(new ItemStack[drops.size()]);
                }
                return null;
            }
        });
        addPerk(new DropOnBreakPerk(20, 50, "Pode achar carvão ao quebrar blocos", new ItemStack(Material.COAL, 3)));
        addPerk(new DropOnBreakPerk(30, 50, "Pode achar pedras com musgo ao quebrar blocos", new ItemStack(Material.MOSSY_COBBLESTONE, 2)));
        addPerk(new DropOnBreakPerk(50, 70, "Pode achar ouro ao quebrar blocos", new ItemStack(Material.GOLD_INGOT)));
        addPerk(new DropOnBreakPerk(70, 70, "Pode achar redstone ao quebrar blocos", new ItemStack(Material.REDSTONE, 3)));
        addPerk(new DropOnBreakPerk(75, 70, "Pode achar ferro ao quebrar blocos", new ItemStack(Material.IRON_INGOT)));
        addPerk(new DropOnBreakPerk(100, 140, "Pode achar diamantes ao quebrar blocos", new ItemStack(Material.DIAMOND)));
    }


    @Override
    public String getTitulo() {
        return "Minerador";
    }

    @Override
    public String getDesc() {
        return "ganha xp ao minerar";
    }

    @Override
    public BlockXpInfo[] getBlocksXps() {
        return new BlockXpInfo[]{
                new BlockXpInfo(1, Material.STONE),

                new BlockXpInfo(3, Material.COAL_ORE),
                new BlockXpInfo(3, Material.IRON_ORE),


                new BlockXpInfo(4, Material.GOLD_ORE),
                new BlockXpInfo(4, Material.LAPIS_ORE),
                new BlockXpInfo(4, Material.GLOWING_REDSTONE_ORE),
                new BlockXpInfo(4, Material.REDSTONE_ORE),

                new BlockXpInfo(5, Material.DIAMOND_ORE),
                new BlockXpInfo(5, Material.EMERALD_ORE),

                new BlockXpInfo(6, Material.QUARTZ_ORE)

        };
    }

    @Override
    public int getMultiplicador() {
        return 120;
    }
}
