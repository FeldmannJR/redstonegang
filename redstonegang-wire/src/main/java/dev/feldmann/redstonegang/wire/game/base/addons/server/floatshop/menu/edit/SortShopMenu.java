package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit;

import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SortShopMenu extends Menu {
    FloatShop shop;
    FloatShopAddon addon;

    public SortShopMenu(FloatShopAddon addon, FloatShop s) {
        super("Shop", s.getLinhas());
        this.addon = addon;
        this.shop = s;
        addClose(this::close);
        setMoveItens(true);
        addItens();
    }


    public void addItens() {
        for (FloatItem it : shop.getItens()) {
            addItem(it);
        }
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
                if (getShopItemId(ev.getCursor()) != null) {
                    ev.setCancelled(true);
                }

            }
        }
        return isButton;
    }

    public void close(Player p) {
        if (p.getItemOnCursor() != null) {
            if (getShopItemId(p.getItemOnCursor()) != null) {
                addItemStack(p.getItemOnCursor());
                p.setItemOnCursor(null);
            }
        }
        ItemStack[] contents = getContents();
        for (int i = 0; i < contents.length; i++) {
            Integer shopid = getShopItemId(contents[i]);
            if (shopid != null) {
                findItem(shopid).setSlot(i);
            }
        }
        if (shop.hasMenu()) {
            shop.getMenu(addon).updateFloatItems();
        }
    }

    public FloatItem findItem(Integer id) {
        for (FloatItem item : shop.getItens()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Integer getShopItemId(ItemStack item) {
        String lore = ItemUtils.findLore(item, "§0floatid:", false);
        return lore == null ? null : Integer.valueOf(lore.split(":")[1]);
    }


    public void addItem(FloatItem item) {
        ItemStack generate = item.getItem().clone();
        ItemUtils.addLore(generate, "§eCompra: " + item.getBuyPrice());
        ItemUtils.addLore(generate, "§eVenda: " + item.getSellPrice());
        ItemUtils.addLore(generate, "§0floatid:" + item.getId());
        if (item.getSlot() != null) {
            addItemStack(item.getSlot(), generate);
        } else {
            addItemStack(generate);
        }
    }
}
