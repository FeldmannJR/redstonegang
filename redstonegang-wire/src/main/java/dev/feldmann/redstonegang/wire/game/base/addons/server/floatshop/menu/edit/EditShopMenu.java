package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit;

import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.ItemButton;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class EditShopMenu extends Menu {
    FloatShop shop;
    FloatShopAddon addon;

    public EditShopMenu(FloatShopAddon addon, FloatShop s) {
        super("Shop", s.getLinhas());
        this.addon = addon;
        this.shop = s;
        addItens();
    }

    public void updateFloatItems() {
        removeAllButtons();
        addItens();
    }

    public void addItens() {
        for (FloatItem it : shop.getItens()) {
            addItem(it);
        }
    }


    public void addItem(FloatItem item) {
        ItemButton b = new ItemButton(addon, item) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new EditItemMenu(addon, item).open(p);
            }
        };
        addNext(b);
    }
}
