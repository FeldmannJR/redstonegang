package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MenuEditPacotes extends Menu {



    public MenuEditPacotes(ShopClick p, ShopAddon addon) {
        super("Editando " + p.id, 2);
        add(9, new Button(ItemBuilder.item(Material.DIODE).name("§e§lVoltar").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new MenuListPacotes(addon).open(player);
            }
        });
        p.editPacket(this);
    }
}
