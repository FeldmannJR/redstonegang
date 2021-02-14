package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class PlayerStatKill extends PlayerStat {


    public EntityType[] types;

    public PlayerStatKill(String uniqueId, ItemStack display, EntityType... types) {
        super(uniqueId, display);
        this.types = types;
    }

}
