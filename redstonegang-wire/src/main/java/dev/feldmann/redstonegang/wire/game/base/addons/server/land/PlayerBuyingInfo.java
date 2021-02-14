package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PlayerBuyingInfo {
    private Hitbox2D rg;
    private String mundo;
    private long start;
    HashSet<Block> displaing = new HashSet();

    public PlayerBuyingInfo(Hitbox2D rg, String mundo, long start) {
        this.rg = rg;
        this.start = start;
        this.mundo = mundo;
    }

    public Hitbox2D getRg() {
        return rg;
    }

    public String getMundo() {
        return mundo;
    }

    public long getStart() {
        return start;
    }

    public void setDisplaing(HashSet<Block> displaing) {
        this.displaing = displaing;
    }

    public HashSet<Block> getDisplaing() {
        return displaing;
    }

    public double getPreco() {
        return getRg().getWidth() * getRg().getHeight() * LandAddon.PRECO_POR_BLOCO;
    }


    public Block getHigherAt(int x, int z) {
        Block b = Bukkit.getWorld(mundo).getHighestBlockAt(x, z);
        int y = b.getY();
        List<Material> ignore = Arrays.asList(Material.LOG, Material.LOG_2, Material.LEAVES, Material.LEAVES_2, Material.AIR);
        while ((ignore.contains(b.getType()) || !b.getType().isSolid()) && y > 0) {
            b = b.getRelative(BlockFace.DOWN);
            y--;
        }
        if (y > 0) {
            return b;
        }
        return null;
    }

    public void botaBlocos(int color) {
        Hitbox2D box = getRg();
        byte data = (byte) color;
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinY(); z <= box.getMaxY(); z++) {
                if (x == box.getMinX() || x == box.getMaxX() || z == box.getMinY() || z == box.getMaxY()) {
                    Block b = getHigherAt(x, z);
                    if (b != null) {
                        if (b.getType() != Material.GRASS && b.getType() != Material.DIRT && b.getType() != Material.STONE && b.getType() != Material.MYCEL) {
                            b.breakNaturally();
                        }
                        b.setType(Material.STAINED_CLAY);
                        b.setData(data);
                    }


                }
            }
        }
    }
}
