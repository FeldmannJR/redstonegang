package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPage;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;

public class MenuListPacotes extends MenuViewPacotes {

    ShopAddon addon;

    public MenuListPacotes(ShopAddon addon) {
        super(addon);
        this.addon = addon;
    }

    @Override
    public void clickPacote(Player p, ShopClick click) {
        new MenuEditPacotes(click, addon).open(p);
    }

    @Override
    public void loadPage(int page) {
        super.loadPage(page);
        if (getButton(49) == null) {
            add(49, new Button(ItemBuilder.item(Material.GLASS).name("§e§lEditar Shop").build()) {
                @Override
                public void click(Player player, Menu menu, ClickType clickType) {
                    new MenuEditSlots(-1,addon).open(player);
                }
            });
        }
    }

    @Override
    public ItemStack trabalhaItem(ShopClick click, ItemStack it) {
        return it;
    }

    @Override
    public Comparator<ShopClick> getComparator() {
        return (o1, o2) -> {
            if (o1 instanceof ShopPage && o2 instanceof ShopPage) {
                return 0;
            }
            if (o1 instanceof ShopPage) {
                return -1;
            }
            if (o2 instanceof ShopPage) {
                return 1;
            }
            return o2.id - o1.id;
        };
    }

    @Override
    public boolean shouldSkip(ShopClick click) {
        return false;
    }
}
