package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

import org.bukkit.inventory.ItemStack;

public class DisplayJobPerk extends JobPerk {
    ItemStack item;

    public DisplayJobPerk(int level, int chance, String nome, ItemStack item) {
        super(level, nome, chance);
        this.item = item;
    }


    @Override
    public ItemStack getItemStack() {
        return item;
    }
}
