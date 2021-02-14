package dev.feldmann.redstonegang.wire.game.base.addons.server.kit;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {
    String name;
    /**
     * Cooldown em minutos
     **/
    int cd;
    /**
     * Itens do kit
     **/
    ItemStack[] items;

    public boolean isValid = true;
    public boolean editing = false;


    public Kit(String name, int cd, ItemStack[] items) {
        this.name = name;
        this.cd = cd;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getCooldown() {
        return cd;
    }

    public int countItens() {
        int x = 0;
        for (ItemStack t : items) {
            if (t != null) {
                x++;
            }
        }
        return x;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void give(Player p) {
        for (ItemStack item : items) {
            if (item != null) {
                p.getInventory().addItem(item);
            }
        }
        p.updateInventory();
    }

    public String getFormattedCooldown() {
        return TimeUtils.convertString(cd * 1000L * 60L, 2, null);
    }
}
