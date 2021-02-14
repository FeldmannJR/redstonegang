package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCheck {

    public static boolean isInHand(Player p, ItemStack it) {

        if (p.getItemInHand() == null) {
            return false;
        }
        return p.getItemInHand().isSimilar(it);
    }
}
