package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectItemFromChest extends Menu {
    ChestShop shop;
    ChestShopAddon manager;

    public SelectItemFromChest(ChestShopAddon manager, ChestShop shop) {
        super("Selecionado Item", 6);
        this.shop = shop;
        this.manager = manager;
        Inventory chest = shop.findChest();
        if (chest == null) return;
        for (ItemStack it : chest.getContents()) {
            if (it != null) {
                ItemStack clone = it.clone();
                it.setAmount(1);
                addNext(new Button(clone) {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        if (!shop.deleted) {
                            shop.setItem(clone);
                            manager.save(shop);

                            C.info(p, "Salvo!");
                            p.closeInventory();
                        }
                    }
                });
            }
        }
    }

}
