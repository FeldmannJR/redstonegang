package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopTipo;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class MenuViewPacotes extends MultiplePageMenu<ShopClick> {

    ShopAddon addon;

    public MenuViewPacotes(ShopAddon addon) {
        super("Pacotes");
        this.addon = addon;
    }


    public abstract void clickPacote(Player p, ShopClick click);

    public abstract ItemStack trabalhaItem(ShopClick click, ItemStack it);

    public abstract boolean shouldSkip(ShopClick click);

    @Override
    public Button getButton(ShopClick click, int page) {
        ItemStack it = click.getIcone();
        ItemUtils.setItemName(it, "§7" + click.id + "   §a" + click.nome + " §7" + ShopTipo.getByObject(click).name());
        it = trabalhaItem(click, it);
        return new Button(it) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                clickPacote(player, click);
            }
        };
    }

    @Override
    public List<ShopClick> getAll() {
        List<ShopClick> ordered = new ArrayList<>();
        for (ShopClick s : addon.getClicks()) {
            if (!shouldSkip(s)) {
                ordered.add(s);
            }
        }
        ordered.sort(getComparator());
        return ordered;
    }


    public Comparator<ShopClick> getComparator() {
        return new Comparator<ShopClick>() {
            @Override
            public int compare(ShopClick o1, ShopClick o2) {
                return o2.id - o1.id;

            }
        };
    }

}
