package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu;

import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;

import java.util.Collections;
import java.util.List;

public class ShopMenu extends Menu {
    FloatShop shop;
    FloatShopAddon addon;

    public ShopMenu(FloatShopAddon addon, FloatShop s) {
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
        List<FloatItem> itens = shop.getItens();
        Collections.sort(itens, (o1, o2) -> {
            if (o1.getSlot() != null && o1.getSlot() != -1) return -1;
            return o1.getId() - o2.getId();
        });
        for (FloatItem it : itens) {
            addItem(it);
        }

    }


    public void addItem(FloatItem item) {
        ItemButton b = new ItemButton(addon, item);
        if (item.getSlot() != null && item.getSlot() != -1) {
            add(item.getSlot(), b);
            return;
        }
        addNext(b);
    }
}
