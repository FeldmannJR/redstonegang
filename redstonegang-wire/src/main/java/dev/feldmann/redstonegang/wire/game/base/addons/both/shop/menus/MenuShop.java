package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPage;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuShop extends Menu implements ShopMenuBase {
    ShopAddon addon;

    public MenuShop(String titulo, int pageid, ShopAddon addon) {
        super(titulo, addon.getLines(pageid));
        this.addon = addon;
        int lines = addon.getLines(pageid);
        if (pageid != -1) {
            ShopPage shopPage = addon.getShopPage(pageid);
            if (shopPage != null) {
                add((lines - 1) * 9,
                        new Button(ItemBuilder.item(Material.DIODE).name("§e§lVoltar").lore("§fClique para voltar!").build()) {
                            @Override
                            public void click(Player player, Menu menu, ClickType clickType) {
                                addon.openPlayer(player, shopPage.pageid);
                            }
                        });
            }
        }
        for (ShopClick click : addon.getPage(pageid)) {
            final ItemStack i = click.buildItemStack();
            ItemMeta meta = i.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
            i.setItemMeta(meta);
            add(click.slot, new Button(i) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    click.click(player);
                }
            });
        }
    }

}
