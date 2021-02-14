package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopValuable;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;


public class MenuVerPacote extends Menu {

    ShopAddon addon;

    public MenuVerPacote(String nome, ShopClick click, int lines, ShopAddon addon) {
        super(nome, lines);
        add(0, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType cs) {
                addon.openPlayer(p, click.pageid);
            }
        });
        if (click instanceof ShopValuable) {
            add(8, ((ShopValuable) click).getComprarButton());
        }
        add(4, new DummyButton(click.buildItemStack()));
    }

}
