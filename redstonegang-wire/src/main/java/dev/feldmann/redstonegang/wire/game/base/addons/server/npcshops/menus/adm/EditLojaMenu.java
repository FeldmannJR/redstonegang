package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.menus.adm;

import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EditLojaMenu extends Menu {

    LojaNPC loja;

    public EditLojaMenu(LojaNPC loja) {
        super("Editando Loja", loja.getSize());
        this.loja = loja;
        setMoveItens(true);
        addClose(this::close);
        loja.editing = true;
        for (LojaItem it : loja.getItens()) {
            addItemStack(it.getSlot(), it.generateWithPrice());
        }

    }

    public void close(Player p) {
        if (p.getItemOnCursor() != null) {
            if (isShopItem(p.getItemOnCursor())) {
                addItemStack(p.getItemOnCursor());
                p.setItemOnCursor(null);
            }
        }
        loja.updateFromItemstack(getContents());
        loja.editing = false;
        loja.save();
    }

    public boolean isShopItem(ItemStack it) {
        if (it == null) return false;

        if (ItemUtils.findLore(it, LojaNPC.LORE_COMPRA, false) != null) {
            return true;
        }

        return ItemUtils.findLore(it, LojaNPC.LORE_VENDA, false) != null;
    }

    @Override
    public boolean clickInventory(InventoryClickEvent ev) {
        boolean isButton = super.clickInventory(ev);
        if (!isButton) {
            if (ev.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                ev.setCancelled(true);
                return false;
            }
            if (!(ev.getClickedInventory() instanceof PlayerInventory)) {
                if (ev.getAction() == InventoryAction.DROP_ALL_CURSOR) {
                    ev.setCancelled(true);
                    ev.getWhoClicked().setItemOnCursor(null);
                }
                if (ev.getAction() == InventoryAction.DROP_ONE_CURSOR) {
                    ev.setCancelled(true);
                    ItemStack cursor = ev.getCursor();
                    if (cursor.getAmount() > 1) {
                        cursor.setAmount(cursor.getAmount() - 1);
                    } else {
                        cursor = null;
                    }
                    ev.getWhoClicked().setItemOnCursor(cursor);
                }
                if (ev.getAction() == InventoryAction.DROP_ONE_SLOT) {
                    ev.setCancelled(true);
                    ItemStack cursor = ev.getCurrentItem();
                    if (cursor.getAmount() > 1) {
                        cursor.setAmount(cursor.getAmount() - 1);
                    } else {
                        cursor = null;
                    }
                    ev.setCurrentItem(cursor);
                }
                if (ev.getAction() == InventoryAction.DROP_ALL_SLOT) {
                    ev.setCancelled(true);
                    ev.setCurrentItem(null);
                }
            } else {
                if (isShopItem(ev.getCursor())) {
                    ev.setCancelled(true);
                }

            }
        }
        return isButton;
    }


}
