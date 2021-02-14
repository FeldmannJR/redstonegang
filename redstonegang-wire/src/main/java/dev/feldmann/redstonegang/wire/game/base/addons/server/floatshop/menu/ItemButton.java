package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.FloatShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.ButtonTick;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemButton extends Button implements ButtonTick {

    FloatItem item;

    double sell = -1;
    double available = -1;
    private long lastAvailableUpdate = -1;
    FloatShopAddon addon;

    public ItemButton(FloatShopAddon addon, FloatItem item) {
        super(generate(item));
        this.item = item;
        this.addon = addon;
        updateItem();
    }


    @Override
    public void click(Player p, Menu m, ClickType click) {
        int quantidade = 1;
        if (click.isShiftClick()) {
            quantidade = 10;
        }
        if (click.isRightClick()) {
            item.sell(p, quantidade);
        } else {
            item.buy(p, quantidade);
        }
    }

    public void updateItem() {
        double buy = item.calculateBuyPrice();
        double sell = item.calculateSellPrice();
        double available = item.getAvailable();
        boolean shouldUpdate = sell != this.sell;

        if (item.useStock() && available != this.available) {
            if (System.currentTimeMillis() > lastAvailableUpdate + 5000) {
                lastAvailableUpdate = System.currentTimeMillis();
                shouldUpdate = true;
            }
        }
        if (!shouldUpdate) return;
        List<String> it = getItem().getItemMeta().getLore();
        if (item.getBuyPrice() > 0) {
            it.set(1, C.buy("Preço Comprar: ##", buy));
        }
        if (item.getSellPrice() > 0) {
            it.set(2, C.sell("Preço Vender: ##", sell));
        }
        if (item.isItemFloat()) {
            it.set(4, C.itemDesc(item.getVarianceString()));
        } else {
            it.set(4, C.itemDesc("Preço normal!"));
        }
        if (item.useStock())
            it.set(6, C.itemDesc("Em Estoque: %%", NumberUtils.convertToString(available)));
        ItemUtils.setLore(getItem(), it);
        this.available = available;
        this.sell = sell;
        setItemStack(getItem());
    }

    private static ItemStack generate(FloatItem item) {
        ItemStack i = item.getItem().clone();
        ItemUtils.addLore(i, "");
        ItemUtils.addLore(i, "");
        ItemUtils.addLore(i, "");
        ItemUtils.addLore(i, "");
        ItemUtils.addLore(i, "4");
        ItemUtils.addLore(i, "");
        if (item.useStock()) {
            ItemUtils.addLore(i, "6");
            ItemUtils.addLore(i, "");
        }
        ItemUtils.addLore(i, C.itemDesc("Botão Esquerdo Compra !"), C.itemDesc("Botão Direito Vende!"), C.itemDesc("Shift + click faz 10 vezes!"));
        return i;

    }

    @Override
    public void onTick() {
        updateItem();
    }
}
