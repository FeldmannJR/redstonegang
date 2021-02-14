package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class PlayerStatBreak extends PlayerStat {


    public MaterialData[] data;

    public PlayerStatBreak(String uniqueId, ItemStack display, MaterialData... data) {
        super(uniqueId, display);
        this.data = data;
    }

}
