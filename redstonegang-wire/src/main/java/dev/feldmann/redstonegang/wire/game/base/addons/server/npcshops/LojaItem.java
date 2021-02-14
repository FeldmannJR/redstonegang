package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops;

import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.inventory.ItemStack;


public class LojaItem {

    private ItemStack item;

    private Double precoVenda;
    private Double precoCompra;
    private int slot;

    public LojaItem(ItemStack item, Double precoVenda, Double precoCompra, int slot) {
        this.item = item;
        this.precoVenda = precoVenda;
        this.precoCompra = precoCompra;
        this.slot = slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ItemStack generateWithPrice() {
        ItemStack i = getItem().clone();
        if (getPrecoCompra() != null) {
            ItemUtils.addLore(i, LojaNPC.LORE_COMPRA + getPrecoCompra());
        }
        if (getPrecoVenda() != null) {
            ItemUtils.addLore(i, LojaNPC.LORE_VENDA + getPrecoVenda());
        }
        return i;
    }
}
