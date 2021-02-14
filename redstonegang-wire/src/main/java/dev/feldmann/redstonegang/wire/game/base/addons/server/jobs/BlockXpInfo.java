package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class BlockXpInfo {
    MaterialData data;
    boolean allowPlayerPlaced;
    private int xp;

    public BlockXpInfo(int xp, Material m) {
        this(xp, new MaterialData(m), false);
    }

    public BlockXpInfo(int xp, Material m, boolean allowPlayerPlaced) {
        this(xp, new MaterialData(m), allowPlayerPlaced);
    }

    public BlockXpInfo(int xp, Material m, byte data, boolean allowPlayerPlaced) {
        this(xp, new ExactMaterialData(m, data), allowPlayerPlaced);
    }

    public BlockXpInfo(int xp, Material m, byte data) {
        this(xp, new ExactMaterialData(m, data), false);
    }


    public BlockXpInfo(int xp, MaterialData data, boolean playerPlaced) {
        this.data = data;
        this.allowPlayerPlaced = playerPlaced;
        this.xp = xp;
    }

    public MaterialData getData() {
        return data;
    }

    public boolean isAllowPlayerPlaced() {
        return allowPlayerPlaced;
    }

    public int getXp() {
        return xp;
    }
}
