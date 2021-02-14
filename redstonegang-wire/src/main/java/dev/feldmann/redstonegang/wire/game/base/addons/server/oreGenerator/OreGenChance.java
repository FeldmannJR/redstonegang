package dev.feldmann.redstonegang.wire.game.base.addons.server.oreGenerator;

import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class OreGenChance {

    MaterialData data;
    int minY;
    int maxY;
    int minVein;
    int maxVein;

    double chance;

    public OreGenChance(Material m, int minY, int maxY, int minVein, int maxVein, double chance) {
        this(new MaterialData(m), minY, maxY, minVein, maxVein, chance);
    }

    public OreGenChance(MaterialData data, int minY, int maxY, int minVein, int maxVein, double chance) {
        this.data = data;
        this.minY = minY;
        this.maxY = maxY;
        this.chance = chance;
        this.minVein = minVein;
        this.maxVein = maxVein;
    }

    public boolean isIn(int y) {
        return minY <= y && maxY >= y;
    }

    public int getVeinSize() {
        return RandomUtils.nextInt(minVein, maxVein);
    }
}
