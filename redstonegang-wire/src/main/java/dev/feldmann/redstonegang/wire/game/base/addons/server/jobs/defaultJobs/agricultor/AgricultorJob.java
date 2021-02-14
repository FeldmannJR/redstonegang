package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.agricultor;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BreakBlockJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BlockXpInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakSpecificPerk;
import dev.feldmann.redstonegang.wire.modulos.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.MaterialData;

public class AgricultorJob extends Job implements BreakBlockJob {
    public AgricultorJob() {
        super("agricultor", "Agricultor", new ItemStack(Material.IRON_HOE));
        addPerk(new RandomSeedPerk(10, 25));
        addPerk(new DropOnBreakSpecificPerk(20, 25, "Pode ganhar melancia bonus", new ItemStack(Material.MELON_BLOCK), new MaterialData(Material.MELON_BLOCK)));
        addPerk(new DropOnBreakPerk(30, 25, "Pode ganhar cenoura", new ItemStack(Material.CARROT_ITEM)));
        addPerk(new DropOnBreakPerk(50, 50, "Pode ganhar fungo do nether", new ItemStack(Material.NETHER_STALK)));
        addPerk(new DropOnBreakPerk(60, 50, "Pode ganhar gramas", new ItemStack(Material.LONG_GRASS, 1, (short) 1)));
        addPerk(new DropOnBreakPerk(90, 30, "Pode ganhar pão", new ItemStack(Material.BREAD)));
    }

    @Override
    public BlockXpInfo[] getBlocksXps() {
        return new BlockXpInfo[]{
                new BlockXpInfo(2, Material.NETHER_WARTS, (byte) 3, true),
                new BlockXpInfo(3, Material.POTATO, (byte) 7, true),
                new BlockXpInfo(3, Material.CARROT, (byte) 7, true),
                new BlockXpInfo(3, Material.CROPS, (byte) 7, true),
                new BlockXpInfo(2, Material.MELON_BLOCK),
                new BlockXpInfo(2, Material.PUMPKIN),


        };
    }

    @Override
    public String getTitulo() {
        return "Agricultor";
    }

    @Override
    public String getDesc() {
        return "colher plantas";
    }


    public int praCima(Block b, Material m) {
        int x = 0;
        while (b.getType() == m) {
            if (!BlockUtils.isPlayerPlaced(b)) {
                x++;
            }
            b = b.getRelative(BlockFace.UP);
        }

        return x;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void breakCanaOrCactus(BlockBreakEvent ev) {
        Material m = ev.getBlock().getType();
        if (m == Material.SUGAR_CANE_BLOCK || m == Material.CACTUS) {
            int xp = praCima(ev.getBlock(), m);
            if (xp >= 1) {
                addXp(ev.getPlayer(), xp);
                callActions(ev.getPlayer(), DropOnBreakPerk.class, ev.getBlock());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void breakCocoa(BlockBreakEvent ev) {
        Material m = ev.getBlock().getType();
        if (m == Material.COCOA) {
            CocoaPlant cp = (CocoaPlant) ev.getBlock().getState().getData();
            int xp = 0;
            if (cp.getSize() == CocoaPlant.CocoaPlantSize.LARGE) {
                xp = 2;
            }
            if (cp.getSize() == CocoaPlant.CocoaPlantSize.MEDIUM) {
                xp = 1;
            }
            if (xp > 0) {
                addXp(ev.getPlayer(), 1);
                callActions(ev.getPlayer(), DropOnBreakPerk.class, ev.getBlock());
            }

        }
    }

    @Override
    public int getMultiplicador() {
        return 90;
    }
}
