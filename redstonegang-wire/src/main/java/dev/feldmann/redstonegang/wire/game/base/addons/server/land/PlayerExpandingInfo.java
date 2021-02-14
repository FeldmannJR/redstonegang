package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlayerExpandingInfo extends PlayerBuyingInfo {

    Land t;


    public PlayerExpandingInfo(Hitbox2D rg, String mundo, long start, Land t) {
        super(rg, mundo, start);
        this.t = t;
    }

    public Land getTerreno() {
        return t;
    }

    @Override
    public double getPreco() {

        return getDif() * LandAddon.PRECO_POR_BLOCO;
    }

    public int getDif() {
        Hitbox2D old = t.getRegion();
        return ((getRg().getHeight() * getRg().getWidth()) - (old.getHeight() * old.getWidth()));
    }

    @Override
    public void botaBlocos(int color) {
        byte data = (byte) color;
        for (int x = getRg().getMinX(); x <= getRg().getMaxX(); x++) {
            for (int z = getRg().getMinY(); z <= getRg().getMaxY(); z++) {
                if (x == getRg().getMinX() || x == getRg().getMaxX() || z == getRg().getMinY() || z == getRg().getMaxY()) {
                    if (x < t.getRegion().getMinX() || x > t.getRegion().getMaxX() || z > t.getRegion().getMaxY() || z < t.getRegion().getMinY()) {

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
}
