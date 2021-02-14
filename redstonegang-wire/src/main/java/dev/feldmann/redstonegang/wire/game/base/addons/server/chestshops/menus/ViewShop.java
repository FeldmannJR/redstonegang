package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.menus;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShop;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.inventory.ItemStack;

public class ViewShop extends Menu {

    public ViewShop(ChestShop shop) {
        super("Shop de " + RedstoneGangSpigot.getPlayer(shop.getOwnerId()).getName(), 1);
        ItemStack it = shop.getItem().clone();
        ItemUtils.addLore(it, "");
        if (shop.getPrecoCompra() != null) {
            ItemUtils.addLore(it, C.sell("Preço para Vender: ##", NumberUtils.convertToString(shop.getPrecoCompra())));
        }
        if (shop.getPrecoVenda() != null) {
            ItemUtils.addLore(it, C.buy("Preço para Comprar: ##", NumberUtils.convertToString(shop.getPrecoVenda())));
        }
        add(4, new DummyButton(it));
    }
}
